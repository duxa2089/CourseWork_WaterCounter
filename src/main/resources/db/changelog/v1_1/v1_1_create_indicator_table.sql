create table Indicator (
                         indicator_id uuid unique primary key not null,
                         Firm varchar(1000) not null,
                         Model varchar(1000) not null,
                         TechParam varchar(1000),
                         Guarantee varchar(1000),
                         room_id uuid not null
                         CONSTRAINT fk_room
                                 REFERENCES Room
);