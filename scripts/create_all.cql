create keyspace homeewatcher with replication = {'class' : 'SimpleStrategy', 'replication_factor':2};
use homeewatcher;

source 'create_tables.cql';
source 'insert_data.cql';