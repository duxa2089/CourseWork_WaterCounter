create table if not exists User (
                      user_id uuid unique primary key not null,
                      FirstName varchar(1000) not null,
                      MiddleName varchar(1000),
                      LastName varchar(1000) not null,
                      Email varchar(1000) not null,
                      Username varchar(1000) not null,
                      Password varchar(1000) not null,
                      role varchar(100) not null
);

create table if not exists Room (
                      room_id uuid unique primary key not null,
                      size varchar(1000) not null,
                      address varchar(1000) not null
);

create table if not exists Indicator (
                           indicator_id uuid unique primary key not null,
                           Firm varchar(1000) not null,
                           Model varchar(1000) not null,
                           TechParam varchar(1000),
                           Guarantee varchar(1000),
                           room_id uuid not null
                               CONSTRAINT fk_room
                                   REFERENCES Room
);

create table if not exists Indication (
                            indication_id uuid unique primary key not null,
                            value varchar(1000) not null,
                            activity_id uuid not null
                                CONSTRAINT fk_activity
                                    REFERENCES Activity,
                            indicator_id uuid not null
                                CONSTRAINT fk_indicator
                                    REFERENCES Indicator
);

create table if not exists History (
                         history_id uuid unique primary key not null,
                         isConfirmed boolean,
                         user_id uuid not null
                             CONSTRAINT fk_activity
                                 REFERENCES User

);

create table if not exists Activity (
                         activity_id uuid unique primary key not null,
                         DateFrom timestamp with time zone,
                         DateTo timestamp with time zone,
                         history_id uuid not null
                         CONSTRAINT fk_activity
                                 REFERENCES History
);