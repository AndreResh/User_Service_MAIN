create table if not exists "role"
(
    id INT NOT NULL  ,
    name varchar(255) NOT NULL ,
    primary key (id)
);
create table if not exists "user"
(
    id       INT NOT NULL  ,
    email    varchar(255) NOT NULL ,
    password varchar(255) NOT NULL ,
    primary key (id)
);
create table if not exists "user_roles"
(
    "user_id"  INT,
    "roles_id" INT,
    foreign key ("roles_id") references "role",
    foreign key ("user_id") references "user"
)
