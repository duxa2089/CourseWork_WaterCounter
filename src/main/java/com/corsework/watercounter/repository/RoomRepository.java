package com.corsework.watercounter.repository;

import com.corsework.watercounter.entities.Activity;
import com.corsework.watercounter.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomRepository extends CrudRepository<Room, UUID> {

    @Query(value = "select r.* from users\n" +
            "    join history h on users.user_id = h.user_id\n" +
            "    join activity a on h.history_id = a.history_id\n" +
            "    join indication i on a.activity_id = i.activity_id\n" +
            "    join indicator i2 on i.indicator_id = i2.indicator_id\n" +
            "    join room r on i2.room_id = r.room_id\n" +
            "where username = '?1'", nativeQuery = true)
    Room findByUsername(String username);
}
