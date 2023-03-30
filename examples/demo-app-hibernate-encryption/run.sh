#!/bin/bash

# APP
export APP_PORT=${APP_PORT:-8090}
BASE_URL="http://localhost:${APP_PORT}/demo"

# MySQL
MYSQL_USER=springuser
MYSQL_ROOT_PASS=rootpass
MYSQL_PASS=userpass
MYSQL_DBNAME=app_db
export MYSQL_PORT=${MYSQL_PORT:-3306}

# Docker localhost
DOCKER_LOCALHOST=${DOCKER_LOCALHOST:-host.docker.internal} # or use 172.17.0.1

# Vault
DOCKER_TAG="1.2.2"
export PVAULT_PORT=${PVAULT_PORT:-8123}
PVAULT_CLI="docker run --rm -i -v $(pwd):/pwd -w /pwd -e PVAULT_ADDR=http://${DOCKER_LOCALHOST}:${PVAULT_PORT} piiano/pvault-cli:${DOCKER_TAG}"

# Run as root and also wait allow for time until mysql is up
function mysql_cmd_initial()
{
	# shellcheck disable=SC2034
	for i in {1..20}
	do  
		output=`docker run -it --rm mysql mysql -h${DOCKER_LOCALHOST} -uroot -p${MYSQL_ROOT_PASS} -P${MYSQL_PORT} -e "show databases;"`
		if [ $? != "0" ] ; then
			echo ${output} | grep -q "ERROR 20"
			if [ $? != "0" ] ; then
				echo "Failed running SQL command."
				return
			fi
			echo "MySQL not ready yet. Sleep 5"
			sleep 5
		else
			echo "MySQL is ready"
			echo "${output}"
			break
		fi
	done
}

function mysql_cmd()
{
	IS_ROOT=${1}
	CMD="${2}"

	if ${IS_ROOT} ; then
		U=""
		P="-p${MYSQL_ROOT_PASS}"
		DB=""
	else
		U="-u ${MYSQL_USER}"
		P="-p${MYSQL_PASS}"
		DB=${MYSQL_DBNAME}
	fi
	echo Running "${CMD}"
	docker run -it --rm mysql mysql -h${DOCKER_LOCALHOST} -P${MYSQL_PORT} ${DB} ${U} ${P} -e "${CMD}"
}

function add_customer()
{
  name=$1
  email=$2
  phone=$3
	ssn=$4
	dob=$5
	state=$6

	printf "Adding customer ${name} with email ${email} and with phone ${phone} ... "
	curl -s "${BASE_URL}/add-customer?name=${name}&email=${email}&phone=${phone}&ssn=${ssn}&dob=${dob}&state=${state}"
	if [ $? != 0 ] ; then
		echo "Failed adding customer"
	else
		echo
	fi
}

function usage_and_exit()
{
	echo "Usage: $0 -i : run in interactive mode"
	exit 1
}

function stop_all()
{
	echo "stop pvault"
	docker rm -f pvault-dev
	echo "stop mysql"
	docker rm -f mysql
	if [[ $(jobs -p) ]]; then
	  kill "$(jobs -p)"
	fi
	
}

function interrupted_callback()
{
	echo "Interrupted ..."
	stop_all
	exit 0
}

function debug()
{
	echo -e '\n--' $1
	if $INTERACTIVE_MODE ; then
		echo "<enter> to continue"
		read -r
	fi
}

# -------------
# 	 M A I N
# -------------


# trap ctrl-c and call the interrupted callback
trap interrupted_callback INT
INTERACTIVE_MODE=false
if [ $# -gt "1" ] ; then
    usage_and_exit
elif [ $# -eq "1" ] ; then
	if [ $1 = "-i" ] ; then
		INTERACTIVE_MODE=true
		echo "Enable interactive mode"
	else
		usage_and_exit
	fi
fi

# if JQ is missing, use just cat instead
JQ=jq
${JQ} --version > /dev/null 2>&1
if [ $? != 0 ] ; then
	JQ=cat
fi

# check for license key of Vault
if [ -z $PVAULT_SERVICE_LICENSE ] ; then
	echo "Please first set environment variable: PVAULT_SERVICE_LICENSE"
	echo "Obtain a free license here: https://piiano.com/docs/guides/get-started#install-piiano-vault"
	exit 0
else
	debug "license found in PVAULT_SERVICE_LICENSE"
fi

debug "stopping stale containers"
stop_all

debug "starting mysql"
docker run --rm --name mysql -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASS} -p ${MYSQL_PORT}:3306 -d mysql:8.0.30
mysql_cmd_initial
mysql_cmd true "create database app_db; create user '${MYSQL_USER}'@'%' identified by '${MYSQL_PASS}'; grant all on ${MYSQL_DBNAME}.* to '${MYSQL_USER}'@'%';"

# start vault
debug "starting vault"
docker run --rm --name pvault-dev -p ${PVAULT_PORT}:8123 \
	-e PVAULT_SERVICE_LICENSE=${PVAULT_SERVICE_LICENSE} -d piiano/pvault-dev:${DOCKER_TAG}

# check for Vault version to ensure it is up - TBD
until ${PVAULT_CLI} version > /dev/null 2>&1
do
    echo "Waiting for the vault to start ..."
    sleep 1
done

debug "Adding new collection 'customers' with name and phone property"
${PVAULT_CLI} collection add --collection-pvschema "
customers PERSONS (
  name  NAME,
  email EMAIL,
  phone PHONE_NUMBER,
  ssn   SSN,
  dob   STRING
)"

# run the app
debug "Running the spring app: java -jar ~/.m2/repository/com/piiano/example/demo-app-hibernate-encryption/0.0.1-SNAPSHOT/demo-app-hibernate-encryption-0.0.1-SNAPSHOT.jar"
java -jar ~/.m2/repository/com/piiano/example/demo-app-hibernate-encryption/0.0.1-SNAPSHOT/demo-app-hibernate-encryption-0.0.1-SNAPSHOT.jar \
	--server.port=${APP_PORT} --spring.datasource.url=jdbc:mysql://localhost:${MYSQL_PORT}/app_db &

until curl -s "${BASE_URL}/all"
do
    echo "Waiting for app at ${BASE_URL}/all"
    sleep 5
done

# Add some customers
debug "Adding customers..."
add_customer john   john@exmaple.com    123-11111   853-11-9898   1989-08-08  AZ
add_customer john   john2@exmaple.com   123-22222   454-21-4355   1975-02-10  NY
add_customer alice  alice@exmaple.com   123-33333   383-83-6464   1999-09-09  CA
add_customer bob	bob@exmaple.com     123-44444   978-35-2138   1982-06-10  FL

# Search customer by name=john
debug "Search customer by name=john --> expecting 2 results:"
curl -s "${BASE_URL}/find-customer-by-name?name=john" | ${JQ}
if [ $? != 0 ] ; then
	echo "Failed find customer by name"
fi

# Get all customers
debug "Get all customers --> Expecting 4 results:"
curl -s "${BASE_URL}/all" | ${JQ}
if [ $? != 0 ] ; then
	echo "Failed get all customers"
fi

# Show mysql encrypted data
debug "Showing encrypted data in mysql (note all columns are encrypted except the 'state' columns)"
mysql_cmd false 'select * from customers where id=1\G'

stop_all
