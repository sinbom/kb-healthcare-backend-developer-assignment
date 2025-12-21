create table if not exists users
(
    id         binary(16)   not null primary key,
    name       varchar(64)  not null comment '이름',
    nickname   varchar(64)  not null unique comment '닉네임',
    email      varchar(320) not null unique comment '이메일',
    password   varchar(255) not null comment '패스워드',
    created_at datetime  not null comment '생성일시',
    updated_at datetime  not null comment '수정일시'
);