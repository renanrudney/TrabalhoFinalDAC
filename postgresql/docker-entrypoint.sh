#!/bin/bash

set -e
# Executa o arquivo init.sql usando psql
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -c "CREATE DATABASE $DBPOSTGRES"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$DBPOSTGRES" -f "/tmp/init.sql"

# Executa o comando original do entrypoint do PostgreSQL
exec "$@"