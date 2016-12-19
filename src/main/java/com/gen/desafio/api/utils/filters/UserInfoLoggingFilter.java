package com.gen.desafio.api.utils.filters;


import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;


public class UserInfoLoggingFilter implements Filter {

	private static Logger log = Logger.getLogger(UserInfoLoggingFilter.class);
	
	private ServletContext context = null;

	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		context = fConfig.getServletContext();
	}
	
	@Override
	public void destroy() {
		NDC.remove();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hReq = (HttpServletRequest) request;
		HttpServletResponse hResp = (HttpServletResponse) response;
		String url = hReq.getServletPath();
		String originalUrl = this.getUrl(hReq);

		// Looking for remote client IP
		String ipAddress = hReq.getHeader("x-forwarded-for");
		if (ipAddress == null) {
			ipAddress = hReq.getHeader("X_FORWARDED_FOR");
			if (ipAddress == null) {
				ipAddress = hReq.getRemoteAddr();
				if(ipAddress == null)
					ipAddress = "null";
			}
		}
		
		// Setting up NDC context
        NDC.push(ipAddress);

		// Logging request data
		log.debug("IP " + ipAddress + " requesting URL: " + originalUrl);

		// Logging browser info
		log.debug("IP " + ipAddress + ": Extra browser Info");
		Enumeration headerNames = hReq.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			if ("user-agent".equals(headerName.toLowerCase()) || "cookie".equals(headerName.toLowerCase()))
				log.debug(headerName.toUpperCase() + ": " + hReq.getHeader(headerName));
		}

		// pass the request along the filter chain
		try {
			chain.doFilter(request, response);
		} catch (IOException e) {
			log.debug(e, e);
			throw e;
		} catch (ServletException e) {
			log.debug(e, e);
			throw e;
		} finally {
			NDC.pop();
		}
		
	}
	
	private String getUrl(HttpServletRequest req) {
		String reqUrl = req.getRequestURL().toString();
		String queryString = req.getQueryString();
		if (queryString != null) {
			reqUrl += "?" + queryString;
		}
		return reqUrl;
	}

}
