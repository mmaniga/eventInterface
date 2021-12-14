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
            System.out.println(x.getAccountId());
            System.out.println(x.getId());
            System.out.println(x.getKey());
            System.out.println(x.getName());
            System.out.println(x.getStatus());
        });

    }

    @Scheduled(fixedDelayString = "PT600.000S")
    public void tenSecIntervalPrinting() {
        logger.info("Getting into tenSecIntervalPrinting");
        System.out.println("Reading from database");
        connectedSourceRepository.findAll().forEach(x -> {
            System.out.println(x.getAccountId());
        });
        System.out.println("Checking Key Exists in Redis");
        Set<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().keys("*".getBytes());
        Iterator<byte[]> it = keys.iterator();
        StringBuffer sb = new StringBuffer();

        while (it.hasNext()) {
            byte[] data = (byte[]) it.next();
            sb.append(new String(data, 0, data.length));
        }
        System.out.println(sb.toString());

        System.out.println("USing Redis Hash");

        StringRedisTemplate redisTemplate1 = new StringRedisTemplate();
        redisTemplate1.setConnectionFactory(redisTemplate.getConnectionFactory());
        redisTemplate1.setKeySerializer(new StringRedisSerializer());
        redisTemplate1.setValueSerializer(new StringRedisSerializer());
        redisTemplate1.afterPropertiesSet();

        RedisHashCommands hashOperations = redisTemplate1.getConnectionFactory().getConnection().hashCommands();
        HashOperations hashOperations1 = redisTemplate1.opsForHash();
        //Map<String,String>  connectedSource =  (Map)hashOperations1.get("ConnectedSource","01926aae-d4f9-5dc2-8391-fe2f90d64776");
        //System.out.println("connected Source " + connectedSource);


        Map<String,Map> ent = hashOperations1.entries("ConnectedSource");
        ent.entrySet().forEach((e -> {
            System.out.println(e.getKey().toString() + " : " +e.getValue() );
        }));

        System.out.println("Getting from other method");
        ConnectedSourceRedis c = new ConnectedSourceRedis();
        c.setAccountId("QQQQL");
        c.setId("1111");
       // c.setKey("abc");
      //  c.setStatus("active");
        sourceKeyRepository.save(c);
        System.out.println("save complete");
        System.out.println("read");
        Optional<ConnectedSourceRedis> cc = sourceKeyRepository.findById("1111");
        System.out.println("find got");
        System.out.println(cc.get().getId());
        //Map<String,String> connectedSource = sourceKeyRepository.getSourceKey("ConnectedSource");

        /*Map<String, String> entr = hashOperations.hGetAll("ConnectedSource".getBytes(StandardCharsets.UTF_8));

        entr.entrySet().forEach((e -> {
            System.out.println(e.getKey().toString());
            System.out.println(e.getValue());
        }));


        System.out.println(entr.size());

         */
        //Map<String,String> connectedSource = sourceKeyRepository.getSourceKey("ConnectedSource");
        //System.out.println(event);
        //event.entrySet().forEach(entry -> {
       //     System.out.println(entry.getKey() + " " + entry.getValue());
        //});


        System.out.println("Reading from database completed..");
    }
}
