package http.client.wrapper.request;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpContextBuilder {

    private final String domain;
    private final List<ClientCookie> cookies;
    private HttpContext overrideHttpContext;

    public HttpContextBuilder(String domain) {
        this.domain = domain;
        this.cookies = new ArrayList<ClientCookie>();
    }

    public HttpContextBuilder withCookies(List<Cookie> cookies) {
        this.cookies.addAll(Cookies.toClientCookie(domain, cookies));
        return this;
    }

    public HttpContextBuilder useContext(HttpContext httpContext) {
        this.overrideHttpContext = httpContext;
        return this;
    }

    public HttpContext build() {
        return hasOverriddenHttpContext() ? overrideHttpContext : buildNewCookiesAwareHttpContext();
    }

    private boolean hasOverriddenHttpContext() {
        return null != overrideHttpContext;
    }

    private HttpContext buildNewCookiesAwareHttpContext() {
        final HttpContext httpContext = new BasicHttpContext();
        if (hasCookies()) {
            httpContext.setAttribute(HttpClientContext.COOKIE_STORE, Cookies.toClientCookieStore(cookies));
        }
        return httpContext;
    }

    private boolean hasCookies() {
        return !this.cookies.isEmpty();
    }
}
