drop table member;

create table member(
 id         varchar2(15),
 password   varchar2(10),
 name       varchar2(15),
 age        Number,
 gender     varchar2(5),
 email      varchar2(30),
 PRIMARY KEY(id)
);

select * from member;

insert into member values ('admin','1234','관리자',100,'여', 'hahaha');

insert into member values ('renjun','1234','런전',20,'남', 'angel');
insert into member values ('jisung','1234','햄쮜송',18,'남', 'popwar');
insert into member values ('haechan','1234','풀썬',20,'남', 'gomdori');