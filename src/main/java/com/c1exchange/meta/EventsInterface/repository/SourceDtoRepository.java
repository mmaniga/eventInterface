package com.c1exchange.meta.EventsInterface.repository;

import com.c1exchange.meta.EventsInterface.dto.SourceDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface SourceDtoRepository  extends CrudRepository<SourceDto,Long> {
}
