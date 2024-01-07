1031  docker network create cassandra-net
1032  docker run --name my-cassandra \\n--network cassandra-net \\n-p 9042:9042 \\n-d cassandra:latest
1033  docker run -it --network cassandra-net --rm cassandra cqlsh my-cassandra
1034  docker run -it --network cassandra-net --rm cassandra cqlsh my-cassandra