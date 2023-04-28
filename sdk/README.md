<p>
  <a href="https://piiano.com/pii-data-privacy-vault/">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://piiano.com/docs/img/logo-developers-dark.svg">
      <source media="(prefers-color-scheme: light)" srcset="https://piiano.com/wp-content/uploads/piiano-logo-developers.png">
      <img alt="Piiano Vault" src="https://piiano.com/wp-content/uploads/piiano-logo-developers.png" height="40" />
    </picture>
  </a>
</p>

# Piiano vault Java SDK

This folder contains SDKs that connect to the Vault.

This package is compatible with Vault 1.4.0

## Compiling

Run: `make`

The Makefile will:

1. Auto generate the Openapi client
1. Create the Vault [Client](./client) using the auto generated client
1. Create the [Hibernate encryption](./hibernate-encryption/) package using the Client.

## Running tests

An instance of the Vault must be running before tests could start. The Makefile does that automatically.

Run: `make test`
