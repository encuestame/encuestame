package org.encuestame.core.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

public class MapFilter extends OncePerRequestFilter {

    @Override
    @SuppressWarnings("unchecked")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            synchronized (session) {
                Map<String, ?> flash = (Map<String, ?>) session.getAttribute(RequestSessionMap.FLASH_MAP_SESSION_ATTRIBUTE);
                if (flash != null) {
                    for (Map.Entry<String, ?> entry : flash.entrySet()) {
                        Object currentValue = request.getAttribute(entry.getKey());
                        if (currentValue == null) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                    }
                    session.removeAttribute(RequestSessionMap.FLASH_MAP_SESSION_ATTRIBUTE);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}