create table if not exists _roles
(
    id         uuid         not null primary key default gen_random_uuid(),
    name       varchar(255) not null unique,
    created_at timestamp                         default now(),
    updated_at timestamp                         default now()
);

create table if not exists _groups
(
    id         uuid         not null primary key default gen_random_uuid(),
    name       varchar(255) not null unique,
    created_at timestamp                         default now(),
    updated_at timestamp                         default now()
);

create table if not exists _users
(
    id              uuid         not null primary key default gen_random_uuid(),
    username        varchar(255) not null unique,
    email           varchar(255) not null unique,
    hashed_password text         not null,
    profile_id      uuid,
    created_at      timestamp                         default now(),
    updated_at      timestamp                         default now()
);

create type gender as enum ('Мужской','Женский');

create table if not exists _profiles
(
    id           uuid         not null primary key default gen_random_uuid(),
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    patronymic   varchar(255) not null,
    avatar_url   text,
    phone_number varchar(255) unique,
    gender       gender,
    birthday     date,
    created_at   timestamp                         default now(),
    updated_at   timestamp                         default now()
);

create table if not exists _users_roles
(
    id      uuid not null primary key default gen_random_uuid(),
    user_id uuid not null,
    role_id uuid not null
);

create table if not exists _users_groups
(
    id       uuid not null primary key default gen_random_uuid(),
    user_id  uuid not null,
    group_id uuid not null
);

alter table _users_roles
    add foreign key (user_id) references _users (id),
    add foreign key (role_id) references _roles (id);

alter table _users_groups
    add foreign key (user_id) references _users (id),
    add foreign key (group_id) references _groups (id);

alter table _users
    add foreign key (profile_id) references _profiles (id);