drop table if exists "subscribers";

create table "subscribers" (
    id serial primary key,
    reference text unique default gen_random_uuid(),
    userId int references "user"(id)
);

insert into "subscribers" (userId)
values (2), (3), (4);