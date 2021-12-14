package com.c1exchange.meta.EventsInterface.repository;

import com.c1exchange.meta.EventsInterface.entity.ConnectedSourceRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectedSourceRedisRepository extends CrudRepository<ConnectedSourceRedis, String> {
    @Override
    Optional<ConnectedSourceRedis> findById(String s);
}
