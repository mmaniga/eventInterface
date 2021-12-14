package com.c1exchange.meta.EventsInterface.repository;

//import com.c1exchange.meta.EventsInterface.dto.SourceDto;

import com.c1exchange.meta.EventsInterface.entity.ConnectedSource;
import com.c1exchange.meta.EventsInterface.entity.ConnectedSourceRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ConnectedSourceRepository extends CrudRepository<ConnectedSource, Long> {

}
