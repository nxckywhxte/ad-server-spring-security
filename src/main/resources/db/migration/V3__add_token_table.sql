create table if not exists _tokens
(
    id      uuid not null primary key default gen_random_uuid(),
    token   text not null unique,
    token_type varchar(255),
    revoked bool,
    expired bool,
    user_id uuid not null
);

alter table _tokens
add foreign key (user_id) references _users(id)