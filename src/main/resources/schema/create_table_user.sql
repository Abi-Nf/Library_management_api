drop table if exists "user";

create table if not exists "user" (
    id serial primary key,
    name varchar(255)
);

insert into "user" (name)
values ('John Doe'), ('Sarah'), ('Soa'),
       ('Paul'), ('Cheng'), ('Patrick'), ('Bob');