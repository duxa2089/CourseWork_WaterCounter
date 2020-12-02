create table User (
                         user_id uuid unique primary key not null,
                         FirstName varchar(1000) not null,
                         MiddleName varchar(1000),
                         LastName varchar(1000) not null,
                         Email varchar(1000) not null,
                         Username varchar(1000) not null,
                         Password varchar(1000) not null,
                         role varchar(100) not null
);