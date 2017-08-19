package com.group1.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
* Servlet Filter implementation class HitCountServletFilter
*/
@WebFilter("/*")
public class MyFilter implements Filter {
  private int hitCount;

  public void destroy() {
  }

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    // increase counter by one
    hitCount++;

    // Print the counter.
    System.out.println("Site visits count :" + hitCount);
    request.setAttribute("counter", hitCount);
    // pass the request along the filter chain
    chain.doFilter(request, response);
  }

  public void init(FilterConfig fConfig) throws ServletException {
    // Reset hit counter.
    hitCount = 0;
  }
}