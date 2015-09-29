package http.client.wrapper;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.util.StringUtils;

import com.mongodash.model.Server;

public class HttpClientFactory {

	private static RequestConfig config;

	static {
		config = RequestConfig.custom()
				.setConnectionRequestTimeout((int) TimeUnit.SECONDS.toMillis(2 * 60 * 1000))
				.setRedirectsEnabled(false)
				.setCookieSpec(CookieSpecs.BEST_MATCH)
				.setExpectContinueEnabled(true)
				.setStaleConnectionCheckEnabled(true)
				.setAuthenticationEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
	}

	private HttpClientFactory() {
		super();
	}

	public static HttpClient createThreadedClient(Server server) {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(20);
		cm.setDefaultMaxPerRoute(10);
		HttpClientBuilder client = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(config);
		
		addBasicCredentials(client, server);
		return client.build();
	}

	public static HttpClient createSingleClient(Server server) {
		HttpClientBuilder client = HttpClients.custom().setDefaultRequestConfig(config);
		
		addBasicCredentials(client, server);
		return client.build();
	}

	private static void addBasicCredentials(HttpClientBuilder client, Server server) {
		if (StringUtils.hasText(server.getUsername()) && StringUtils.hasText(server.getPassword())) {
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(new AuthScope(server.getHostname(), server.getPort()), new UsernamePasswordCredentials(server.getUsername(),
					server.getPassword()));
			client.setDefaultCredentialsProvider(credentialsProvider);
		}
	}
}