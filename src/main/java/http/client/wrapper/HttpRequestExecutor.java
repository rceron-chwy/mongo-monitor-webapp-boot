package http.client.wrapper;

import http.client.wrapper.request.Request;
import http.client.wrapper.response.Response;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import com.mongodash.model.Server;

public class HttpRequestExecutor {

	private final HttpClient httpClient;

	private HttpRequestExecutor(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public static HttpRequestExecutor withSingleClientAndBasicCredentials(Server server) {
		return new HttpRequestExecutor(HttpClientFactory.createSingleClient(server));
	}

	public static HttpRequestExecutor withSingleClient() {
		return new HttpRequestExecutor(HttpClientFactory.createSingleClient(null));
	}

	public static HttpRequestExecutor withMultipleClientAndBasicCredentials(Server server) {
		return new HttpRequestExecutor(HttpClientFactory.createThreadedClient(server));
	}

	public static HttpRequestExecutor withMultipleClient() {
		return new HttpRequestExecutor(HttpClientFactory.createThreadedClient(null));
	}

	public static Response executeOnce(Request request) {
		return withSingleClient().execute(request);
	}

	public Response execute(Request request) {
		// TODO HttpGet httpGet = new
		// HttpGet(request.getHttpClientRequest().getURI());
		try {

			final HttpResponse httpResponse = httpClient.execute(request.getHttpClientRequest(), request.getHttpContext());

			return new Response(httpResponse, request.getHttpContext());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// TODO finally release httpGet.releaseConnection();
	}
}