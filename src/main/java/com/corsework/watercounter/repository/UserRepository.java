package com.corsework.watercounter.repository;

import com.corsework.watercounter.entities.Activity;
import com.corsework.watercounter.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    User findByUsername(String Username);
}
