package com.c1exchange.meta.EventsInterface.scheduler;

import com.c1exchange.meta.EventsInterface.entity.ConnectedSource;
import com.c1exchange.meta.EventsInterface.repository.ConnectedSourceRepository;
//import com.c1exchange.meta.EventsInterface.repository.SourceDtoRepository;
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

   @Autowired
   private ConnectedSourceRepository connectedSourceRepository;

   @Autowired
   private RedisTemplate redisTemplate;


    @Scheduled(fixedDelayString = "PT10.000S")
    public void tenSecIntervalPrinting() {
        System.out.println("Printing in Scheduler...");
        System.out.println("Reading from database");
        connectedSourceRepository.findAll().forEach(x -> System.out.println(x));
        System.out.println("Checking Key Exists in Redis");
        Set<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().keys("*".getBytes());
        Iterator<byte[]> it = keys.iterator();
        StringBuffer sb = new StringBuffer();

        while(it.hasNext()){

            byte[] data = (byte[])it.next();
            sb.append(new String(data, 0, data.length));
        }
        System.out.println(sb.toString());



        System.out.println("Reading from database completed..");
    }
}
