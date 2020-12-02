package com.corsework.watercounter.repository;

import com.corsework.watercounter.entities.Activity;
import com.corsework.watercounter.entities.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoryRepository extends CrudRepository<History, UUID> {
}
