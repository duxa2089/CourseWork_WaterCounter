create table History (
                         history_id uuid unique primary key not null,
                         isConfirmed boolean,
                         user_id uuid not null
                             CONSTRAINT fk_activity
                             REFERENCES User

);