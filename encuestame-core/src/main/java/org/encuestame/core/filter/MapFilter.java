/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * MapFilter.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
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