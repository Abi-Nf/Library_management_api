drop table if exists "book";
drop type if exists Topic;

create type Topic AS ENUM ( 'ROMANCE', 'COMEDY', 'OTHER' );

create table if not exists "book" (
    id serial primary key,
    bookName text,
    pageNumbers int,
    topic Topic,
    releaseDate date,
    author int references author(id)
);

insert into "book" (bookName, pageNumbers, topic, releaseDate, author)
values ('Through rivers', 324, 'OTHER', '2016-06-12'::date, 1),
       ('Lost in New York', 256, 'COMEDY', '2018-03-22'::date, 1),
       ('My favorite crime', 500, 'ROMANCE', '2021-11-01'::date, 2);