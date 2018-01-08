package com.funflowers;

import com.flexionmobile.codingchallenge.integration.Integration;
import com.flexionmobile.codingchallenge.integration.Purchase;
import com.funflowers.exception.IntegratorException;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.request.HttpRequest;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.funflowers.HttpRequestFactory.RequestType.GET_REQUEST;
import static com.funflowers.HttpRequestFactory.RequestType.POST_REQUEST;

public class FunFlowerFlexionMobileIntegration implements Integration {
    private static final String DEVELOPER_ID = "blzsaa";

    private final Gson gson;
    private final HttpRequestFactory httpRequestFactory;

    public FunFlowerFlexionMobileIntegration(HttpRequestFactory httpRequestFactory) {
        this.gson = new Gson();
        this.httpRequestFactory = httpRequestFactory;
    }

    public Purchase buy(String itemId) {
        HttpResponse<JsonNode> json = doPost(
                "http://sandbox.flexionmobile.com/javachallenge/rest/developer/{developerId}/buy/{itemId}",
                ImmutableMap.of("developerId", DEVELOPER_ID, "itemId", itemId));
        return fromJson(json.getBody().toString(), MyPurchase.class);
    }


    public List<Purchase> getPurchases() {
        HttpResponse<JsonNode> json = doGet(
                "http://sandbox.flexionmobile.com/javachallenge/rest/developer/​{developerId}​/all",
                ImmutableMap.of("developerId", DEVELOPER_ID));
        return Arrays.asList(fromJson(json.getBody().getArray().toString(), MyPurchase[].class));
    }

    public void consume(Purchase purchase) {
        doPost("http://sandbox.flexionmobile.com/javachallenge/rest/developer/{developerId}/consume/{purchaseId}",
                ImmutableMap.of("developerId", DEVELOPER_ID, "purchaseId", purchase.getId()));
    }

    private <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    private HttpResponse<JsonNode> doPost(String url, Map<String, String> routeParams) {
        HttpRequest request = httpRequestFactory.create(POST_REQUEST, url, routeParams);
        return doRequest(request);
    }

    private HttpResponse<JsonNode> doGet(String url, Map<String, String> routeParams) {
        HttpRequest request = httpRequestFactory.create(GET_REQUEST, url, routeParams);
        return doRequest(request);
    }

    private HttpResponse<JsonNode> doRequest(HttpRequest request) {
        HttpResponse<JsonNode> response;
        try {
            response = request.asJson();
        } catch (Exception e) {
            throw new IntegratorException(e);
        }
        checkResponse(response);
        return response;
    }

    private void checkResponse(HttpResponse<JsonNode> response) {
        if (response.getStatus() != HttpStatus.SC_OK) {
            throw new IntegratorException(String.format("unexpected status %d, %s", response.getStatus(), response.getBody()));
        }
    }
}
