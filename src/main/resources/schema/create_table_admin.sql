drop table if exists "admin";

create table if not exists "admin" (
    id serial primary key,
    email varchar(255) not null,
    userId int references "user"(id)
);

insert into "admin" (email, userId)
values ('john@gmail.com', 1),
       ('sarah@gmail.com', 2);