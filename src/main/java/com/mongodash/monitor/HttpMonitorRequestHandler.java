package com.mongodash.monitor;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mongodash.exception.HttpRequestHandlerException;

@Component
public class HttpMonitorRequestHandler {

	private static final Logger logger = LoggerFactory.getLogger(HttpMonitorRequestHandler.class);

	private static HttpClient httpClient;

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(5);
		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static String getResponse(URI uri, HttpContext context) throws HttpRequestHandlerException {
		String response = null;

		HttpGet httpGet = new HttpGet(uri);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet, context);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				try {
					response = EntityUtils.toString(entity);
				} catch (ParseException pe) {
					//ignore and response will be null
					logger.warn("Unable to parse response. Will be ignored. Error is: {}", pe.getMessage());
				} catch (IOException io) {
					//ignore and response will be null
					logger.warn("Unable to parse response. Will be ignored. Error is: {}", io.getMessage());
				}
			}
		} catch (ClientProtocolException e) {
			throw new HttpRequestHandlerException("Invalid protocol for URI [" + uri.toString() + "]", e);
		} catch (IOException e) {
			throw new HttpRequestHandlerException("Connection failed. Make sure the URI [" + uri.toString() + "] is accessible. ", e);
		} finally {
			httpGet.releaseConnection();
		}
		return response;
	}
}
