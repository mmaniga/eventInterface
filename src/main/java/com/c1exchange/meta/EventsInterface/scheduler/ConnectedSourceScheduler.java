package com.c1exchange.meta.EventsInterface.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConnectedSourceScheduler {

    @Scheduled(fixedDelayString = "PT10.000S")
    public void tenSecIntervalPrinting() {
        System.out.println("Printing in Scheduler...");
    }
}
