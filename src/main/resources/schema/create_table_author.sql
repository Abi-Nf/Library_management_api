drop table if exists "author";

create table if not exists "author" (
    id serial primary key,
    name text not null,
    gender char check ( gender = 'M' or gender = 'F' )
);

insert into "author" (name, gender)
values ('Marry Benedito', 'F'),
       ('Moliere', 'M');