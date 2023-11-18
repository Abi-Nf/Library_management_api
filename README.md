# Library Management API

A project that aims to making an OAS specification to API step by step.

## Database connection

In order to ensure the connection to postgres.
you have to check these environment variables:

`PSQL_URL`: it follows this kind of value ``jdbc:postgresql://your_host:the_port/the_database``

`PSQL_USER`: your postgresql user

`PSQL_PASS`: your postgresql password

the way to do in linux and maybe macOS:

```shell
export PSQL_URL=#The value here
export PSQL_USER=#The value here
export PSQL_PASS=#The value here
```

The way to do in Windows:

```shell
setx PSQL_URL "The value here"
setx PSQL_USER "The value here"
setx PSQL_PASS "The value here"
```

## Database Schemas

All scripts for schemas are in [`This directory`](src/main/resources/schema)