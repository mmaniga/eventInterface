package com.c1exchange.meta.EventsInterface.filters;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class SourceKeyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("Getting into Filter ");
        String accessKey = ((HttpServletRequest) request).getHeader("access-key");
        if (accessKey != null && !accessKey.isBlank() && !accessKey.isEmpty()) {
            System.out.println("Access Key Found " + accessKey);
        } else {
            System.out.println("Access key not found, this request would be dropped");
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        chain.doFilter(request, response);
        System.out.println("Getting out of Filter");
    }
}
