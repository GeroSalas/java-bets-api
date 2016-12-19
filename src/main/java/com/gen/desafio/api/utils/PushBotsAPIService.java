/**
 * 
 */
package com.gen.desafio.api.utils;


import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class PushBotsAPIService {
	
	private static final Logger log = LoggerFactory.getLogger(PushBotsAPIService.class);
	
    //private static HttpClient httpClient = new DefaultHttpClient();
    
    @Async 
	public static boolean sendNotificationsToAll(String message){
    	boolean sent = false;
    	String endpoint = APIConstants.PUSHBOTS_REST_API_URL + "/push/all";

    	HttpPost httpPostRequest = new HttpPost();
    	 httpPostRequest.setURI(URI.create(endpoint));
    	 httpPostRequest.setHeader("X-PUSHBOTS-APPID", APIConstants.X_PUSHBOTS_APPID);
    	 httpPostRequest.setHeader("X-PUSHBOTS-SECRET", APIConstants.X_PUSHBOTS_SECRET);
    	 httpPostRequest.setHeader("Content-Type", APIConstants.CONTENT_TYPE_JSON);
		 
		 JSONObject req = new JSONObject();
		  req.put("msg", message);
		 JSONArray platforms = new JSONArray();
		  platforms.put(0);
		  platforms.put(1);
		  req.put("platform", platforms);
		  
		try {
			StringEntity jsonBody = new StringEntity(req.toString());
			httpPostRequest.setEntity(jsonBody);
			HttpResponse response = getThreadSafeClient().execute(httpPostRequest);
			// responseBody = EntityUtils.toString(response.getEntity());  2OO OK EMPTY
			sent = true;
			log.debug("Pushbots API Response ("+response.getStatusLine().getStatusCode()+")");
		
		} catch (Exception ex) {
			log.error("Pushbots API Exception: " + ex.getMessage());
		}
		
		return sent;
    }
	
	
    @Async 
	public static boolean sendNotificationsToOne(String token, String message, int platform){
    	boolean sent= false;
    	String endpoint = APIConstants.PUSHBOTS_REST_API_URL + "/push/one";

    	HttpPost httpPostRequest = new HttpPost();
    	 httpPostRequest.setURI(URI.create(endpoint));
    	 httpPostRequest.setHeader("X-PUSHBOTS-APPID", APIConstants.X_PUSHBOTS_APPID);
    	 httpPostRequest.setHeader("X-PUSHBOTS-SECRET", APIConstants.X_PUSHBOTS_SECRET);
    	 httpPostRequest.setHeader("Content-Type", APIConstants.CONTENT_TYPE_JSON);
		 
		 JSONObject req = new JSONObject();
		  req.put("token", token);
		  req.put("msg", message);
		  req.put("platform", platform);
		  
		try {
			StringEntity jsonBody = new StringEntity(req.toString());
			httpPostRequest.setEntity(jsonBody);
			HttpResponse response = getThreadSafeClient().execute(httpPostRequest);
			// responseBody = EntityUtils.toString(response.getEntity());  2OO OK EMPTY
			sent = true;
			log.debug("Pushbots API Response ("+response.getStatusLine().getStatusCode()+")");
		
		} catch (Exception ex) {
			log.error("Pushbots API Exception: " + ex.getMessage());
		}
		
	   return sent;
    }
    
    
    private static DefaultHttpClient getThreadSafeClient()  {

        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);
        
        return client;
    }
	
	
}
