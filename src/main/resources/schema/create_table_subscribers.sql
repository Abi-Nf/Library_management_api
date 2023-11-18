drop table if exists "subscribers";

create table "subscribers" (
    id serial primary key,
    reference text unique default gen_random_uuid(),
    name varchar(255)
);

insert into "subscribers" (name)
values ('John Doe'), ('Sarah'), ('Anonymous'), ('Thomas');