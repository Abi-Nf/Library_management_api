drop table if exists "admin";

create table if not exists "admin" (
    id serial primary key,
    email varchar(255) not null,
    name varchar(255)
);

insert into "admin" (email, name)
values ('john@gmail.com', 'John Doe'),
       ('sarah@gmail.com', 'Sarah Mayer');