create database collectivite;

CREATE USER federation_agricole_manager WITH PASSWORD 'bandymilay';

GRANT ALL PRIVILEGES ON DATABASE collectivite TO federation_agricole_manager;