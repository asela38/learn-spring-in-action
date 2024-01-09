create table if not exists Person (
    id identity,
    name varchar(1000) not null,
    createdAt timestamp not null
    );