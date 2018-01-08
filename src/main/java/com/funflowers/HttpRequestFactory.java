package com.funflowers;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

import java.util.Map;

public class HttpRequestFactory {

    public enum RequestType implements HttpRequestCreator {
        GET_REQUEST {
            @Override
            public HttpRequest create(String url) {
                return Unirest.get(url);
            }
        }, POST_REQUEST {
            @Override
            public HttpRequest create(String url) {
                return Unirest.post(url);
            }
        }
    }

    private interface HttpRequestCreator {
        HttpRequest create(String url);
    }

    public HttpRequest create(RequestType requestType, String url, Map<String, String> routeParams) {
        HttpRequest request = requestType.create(url);
        routeParams.forEach(request::routeParam);
        return request;
    }
}
