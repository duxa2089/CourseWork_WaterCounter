create table Indication (
                         indication_id uuid unique primary key not null,
                         value varchar(1000) not null,
                         activity_id uuid not null
                         CONSTRAINT fk_activity
                             REFERENCES Activity,
                         indicator_id uuid not null
                         CONSTRAINT fk_indicator
                             REFERENCES Indicator
);