package com.mongodash.http;

import http.client.wrapper.HttpRequestExecutor;
import http.client.wrapper.request.GetRequestBuilder;
import http.client.wrapper.request.RequestBuilder;
import http.client.wrapper.response.Response;

import org.apache.http.HttpStatus;
import org.apache.http.protocol.HttpContext;

import com.mongodash.Config;
import com.mongodash.model.Server;

public class HttpClient {

	private final HttpRequestExecutor executor;
	private Server server;
	private HttpContext httpContext;

	public HttpClient(Server server) {
		executor = HttpRequestExecutor.withSingleClientAndBasicCredentials(server);
		this.server = server;
	}

	public HttpClient(Server server, HttpContext context) {
		executor = HttpRequestExecutor.withMultipleClientAndBasicCredentials(server);
		this.server = server;
		this.httpContext = context;
	}

	public Response getServerStatus() {
		return get("/serverStatus");
	}

	public Response getBuildInfo() {
		return get("/buildInfo");
	}

	public Response getHostInfo() {
		return get("/hostInfo");
	}

	public Response getDatabaseInfo() {
		return get("/listDatabases");
	}
	
	public Response getIsMaster() {
		return get("/isMaster");
	}	
	
	private Response get(String info) {
		GetRequestBuilder get = RequestBuilder.get(getURL()).withPath(info).withPathParameter("text", "1");
		addExtras(get);
		return executor.execute(get.build());
	}

	private void addExtras(GetRequestBuilder get) {
		if (httpContext != null) {
			get = get.withContext(httpContext);
		}
	}

	private String getURL() {
		String scheme = (server.isSecure()) ? Config.HTTP_SCHEME.https.name() : Config.HTTP_SCHEME.http.name();
		return scheme + "://" + server.getHostname() + ":" + server.getPort();
	}

	public boolean isValidCredentials() {
		final Response response = executor.execute(RequestBuilder.get(getURL()).build());
		return response.getStatusCode() == HttpStatus.SC_OK;
	}
}
