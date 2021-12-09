package com.c1exchange.meta.EventsInterface.repository;

//import com.c1exchange.meta.EventsInterface.dto.SourceDto;
import com.c1exchange.meta.EventsInterface.entity.ConnectedSource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectedSourceRepository extends CrudRepository<ConnectedSource,Long> {
    List<ConnectedSource> findAll();
}
