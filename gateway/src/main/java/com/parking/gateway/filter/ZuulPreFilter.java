package com.parking.gateway.filter;


import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;


public class ZuulPreFilter extends ZuulFilter {
	    @Override
	    public String filterType() {
	        return "pre";
	    }
	    @Override
	    public int filterOrder() {
	        return 1;
	    }
	    @Override
	    public boolean shouldFilter() {
	        return true;
	    }
	    @Override
	    public Object run() {
	        RequestContext ctx = RequestContext.getCurrentContext();
	        //ctx.addZuulRequestHeader(null, null);
	        HttpServletRequest request = ctx.getRequest();    
	         // Add a custom header in the request
	        ctx.addZuulRequestHeader("Authorization",
	                 request.getHeader("Authorization"));
	        System.out.println(request.getHeader("Authorization"));
	       // log.info(String.format("%s request to %s", request.getMethod(), 
	       //          request.getRequestURL().toString()));
	        return null;
	    }

}
