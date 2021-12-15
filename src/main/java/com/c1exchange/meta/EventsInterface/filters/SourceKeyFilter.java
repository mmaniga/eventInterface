package com.c1exchange.meta.EventsInterface.filters;

import com.c1exchange.meta.EventsInterface.Constants;
import com.c1exchange.meta.EventsInterface.entity.ConnectedSourceRedis;
import com.c1exchange.meta.EventsInterface.repository.ConnectedSourceRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Order(1)
public class SourceKeyFilter implements Filter {

    @Autowired
    private ConnectedSourceRedisRepository connectedSourceRedisRepository;

    //Note: Refactor this @Mani
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String accessKey = ((HttpServletRequest) request).getHeader(Constants.ACCESS_KEY);

        if (accessKey != null && !accessKey.isBlank() && !accessKey.isEmpty()) {
            try {
                ConnectedSourceRedis cs =  connectedSourceRedisRepository.findById(accessKey).get();
                if(!cs.getStatus().equalsIgnoreCase("active")) {
                    System.out.println("Key no more active...");
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }catch (NoSuchElementException e) {
                System.out.println("Invalid Key");
                HttpServletResponse res = (HttpServletResponse) response;
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } else {
            System.out.println("Access key not found, this request would be dropped");
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        chain.doFilter(request, response);
    }
}
