package com.c1exchange.meta.EventsInterface.scheduler;

import com.c1exchange.meta.EventsInterface.dto.Event;
import com.c1exchange.meta.EventsInterface.entity.ConnectedSource;
import com.c1exchange.meta.EventsInterface.entity.ConnectedSourceRedis;
import com.c1exchange.meta.EventsInterface.filters.SourceKeyFilter;
import com.c1exchange.meta.EventsInterface.repository.ConnectedSourceRepository;
import com.c1exchange.meta.EventsInterface.repository.SourceKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisHashCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class ConnectedSourceScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConnectedSourceRepository connectedSourceRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SourceKeyRepository sourceKeyRepository;

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
    @Scheduled(fixedDelayString = "PT1M")
    public void scheduleLoadConnectedSource() {
        logger.info("Getting into scheduleLoadConnectedSource");

        // Read from mysql and load into redis
        // Every interval do this periodically
        // if data not in cache read from database.

        // They are saved as hash to to get from cli use hget key field or with field or hgetall key
        // use type key function to know the type of key in redis..\

        connectedSourceRepository.findAll().forEach(x -> {
            System.out.println("Processing account " + x.getAccountId() +" into Redis");
            ConnectedSourceRedis connectedSourceRedis = new ConnectedSourceRedis();
            connectedSourceRedis.setId(x.getId().toString());
            connectedSourceRedis.setAccountId(x.getAccountId().toString());
            connectedSourceRedis.setKey(x.getKey());
            connectedSourceRedis.setName(x.getName());
            connectedSourceRedis.setStatus(x.getStatus());
            sourceKeyRepository.save(connectedSourceRedis);
        });

        System.out.println("For Testing - Getting back from Redis");
        sourceKeyRepository.findAll().forEach(x -> {
            System.out.println("------------------------------");
            System.out.println(String.format(" AccountId : %s, Id : %s, Key : %s, Name : %s, Status : %s ",
                    x.getAccountId(),x.getId(), x.getKey(), x.getName(), x.getStatus()));
        });

        System.out.println("getting by key - Direct call");
        Optional<ConnectedSource> cs =  connectedSourceRepository.findById(17L);
        ConnectedSource ccs = cs.get();
        System.out.println(String.format(" AccountId : %s, Id : %s, Key : %s, Name : %s, Status : %s ",
                ccs.getAccountId(),ccs.getId(), ccs.getKey(), ccs.getName(), ccs.getStatus()));

    }

}
