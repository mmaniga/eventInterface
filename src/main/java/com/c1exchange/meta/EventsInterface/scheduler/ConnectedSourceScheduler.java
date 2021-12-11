package com.c1exchange.meta.EventsInterface.scheduler;

import com.c1exchange.meta.EventsInterface.entity.ConnectedSource;
import com.c1exchange.meta.EventsInterface.repository.ConnectedSourceRepository;
//import com.c1exchange.meta.EventsInterface.repository.SourceDtoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.Set;

@Component
public class ConnectedSourceScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConnectedSourceRepository connectedSourceRepository;

    @Autowired
    private RedisTemplate redisTemplate;


    /*
    https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html?is-external=true#parse-java.lang.CharSequence-

    "PT20.345S" -- parses as "20.345 seconds"
    "PT15M"     -- parses as "15 minutes" (where a minute is 60 seconds)
    "PT10H"     -- parses as "10 hours" (where an hour is 3600 seconds)
    "P2D"       -- parses as "2 days" (where a day is 24 hours or 86400 seconds)
    "P2DT3H4M"  -- parses as "2 days, 3 hours and 4 minutes"
    "P-6H3M"    -- parses as "-6 hours and +3 minutes"
    "-P6H3M"    -- parses as "-6 hours and -3 minutes"
    "-P-6H+3M"  -- parses as "+6 hours and -3 minutes"
     */
    @Scheduled(fixedDelayString = "PT10M")
    public void scheduleLoadConnectedSource() {
        logger.info("Getting into scheduleLoadConnectedSource");
    }

    @Scheduled(fixedDelayString = "PT60.000S")
    public void tenSecIntervalPrinting() {
        logger.info("Getting into tenSecIntervalPrinting");
        System.out.println("Printing in Scheduler...");
        System.out.println("Reading from database");
        connectedSourceRepository.findAll().forEach(x -> System.out.println(x.getAccountId()));
        System.out.println("Checking Key Exists in Redis");
        Set<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().keys("*".getBytes());
        Iterator<byte[]> it = keys.iterator();
        StringBuffer sb = new StringBuffer();

        while (it.hasNext()) {

            byte[] data = (byte[]) it.next();
            sb.append(new String(data, 0, data.length));
        }
        System.out.println(sb.toString());


        System.out.println("Reading from database completed..");
    }
}
