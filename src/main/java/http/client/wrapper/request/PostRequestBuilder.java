package http.client.wrapper.request;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;

public abstract class PostRequestBuilder<T extends PostRequestBuilder<?>> extends RequestBuilder<T> {

    public PostRequestBuilder(String baseURL) {
        super(URI.create(baseURL));
    }

    protected abstract HttpEntity getPostEntity();

    @Override
    protected HttpPost createHttpUriRequest() {
        final HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setEntity(getPostEntity());
        return httpPost;
    }
}
