create table Room (
                         room_id uuid unique primary key not null,
                         size varchar(1000) not null,
                         address varchar(1000) not null
);