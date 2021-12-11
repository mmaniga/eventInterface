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
