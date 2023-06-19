package com.ttm.crm.server.config;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlForwardFilter implements Filter {

	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //  API is considered a REST call in the authenticated area of the application
        if (isApi(req)) {
            String path = req.getRequestURI();
        	
        	req = new HttpServletRequestWrapper((HttpServletRequest) req) {
              
        		@Override
        		public String getRequestURI() {
        			return path.substring(5);
        		}        
          };
        } else if (!isStatic(req) && !isPublicApi(req)) {
            req.getRequestDispatcher("/index.html").forward(req, res);
        }
       
        chain.doFilter(req, res);
    }
	
	
    private boolean isApi(HttpServletRequest request) {
    	return request.getRequestURI().startsWith("/rest");
    }

    private boolean isStatic(HttpServletRequest request) {
        return Pattern.matches(".+\\.((html)|(css)|(js)|(png)|(ttf)|(ico)|(svg))$", request.getRequestURI());
    }
    
    //  Any API call that is outside the authenticated area of the application
    private boolean isPublicApi(HttpServletRequest request) {
    	return request.getRequestURI().equals("/referralform");
    }
     
}
