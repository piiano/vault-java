<p>
  <a href="https://piiano.com/pii-data-privacy-vault/">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://docs.piiano.com/img/logo-developers-dark.svg">
      <source media="(prefers-color-scheme: light)" srcset="https://docs.piiano.com/img/logo-developers.svg">
      <img alt="Piiano Vault" src="https://docs.piiano.com/img/logo-developers.svg" height="40" />
    </picture>
  </a>
</p>

# Piiano vault Java SDK


![Workflow status badge](https://github.com/piiano/vault-java/actions/workflows/test.yml/badge.svg?branch=main)
![Java version badge](https://img.shields.io/badge/java-8-blue)

This folder contains SDKs that connect to the Vault.

This package is compatible with Vault 1.11.2

## Compiling

Run: `make`

The Makefile will:

1. Auto generate the Openapi client
1. Create the Vault [Client](./client) using the auto generated client
1. Create the [Hibernate encryption](./hibernate-encryption/) package using the Client.

## Running tests

An instance of the Vault must be running before tests could start. The Makefile does that automatically.

Run: `make test`
