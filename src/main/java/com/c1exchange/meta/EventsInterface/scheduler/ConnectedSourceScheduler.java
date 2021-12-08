package com.c1exchange.meta.EventsInterface.scheduler;

import com.c1exchange.meta.EventsInterface.repository.SourceDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ConnectedSourceScheduler {

    @Autowired
    private SourceDtoRepository sourceDtoRepository;


    @Scheduled(fixedDelayString = "PT10.000S")
    public void tenSecIntervalPrinting() {
        System.out.println("Printing in Scheduler...");
        System.out.println("Reading from database");
        sourceDtoRepository.findAll().forEach(x -> System.out.println(x));
        System.out.println("Reading from database completed..");
    }
}
