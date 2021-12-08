package com.c1exchange.meta.EventsInterface.repository;

import com.c1exchange.meta.EventsInterface.dto.SourceDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceDtoRepository  extends CrudRepository<SourceDto,Long> {
}
