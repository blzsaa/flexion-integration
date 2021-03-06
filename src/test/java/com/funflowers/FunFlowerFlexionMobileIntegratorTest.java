package com.funflowers;

import com.flexionmobile.codingchallenge.integration.Purchase;
import com.funflowers.HttpRequestFactory.RequestType;
import com.funflowers.exception.IntegratorException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class FunFlowerFlexionMobileIntegratorTest {

    @Mock
    private HttpRequestFactory httpRequestFactory;
    @Mock
    private HttpRequest request;
    @Mock
    private HttpResponse<JsonNode> mockResponse;

    @InjectMocks
    private FunFlowerFlexionMobileIntegrator integrator;

    @Before
    public void setUp() {
        doReturn(request).when(httpRequestFactory).create(Mockito.any(RequestType.class), anyString(), anyMap());
    }

    private void mockResponse(int statusCode, String body) {
        try {
            JsonNode json = new JsonNode(body);
            doReturn(statusCode).when(mockResponse).getStatus();
            doReturn(json).when(mockResponse).getBody();
            doReturn(mockResponse).when(request).asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldThrowExceptionWhenErrorOccur() {
        //given
        mockResponse(HttpStatus.SC_NOT_FOUND, "{}");

        //when
        Throwable thrown = catchThrowable(() -> integrator.buy("alma"));

        //then
        assertThat(thrown).isInstanceOf(IntegratorException.class).hasMessageContaining("unexpected status 404, {}");
    }

    @Test
    public void shouldBuyUnconsumedItems() {
        //given
        mockResponse(HttpStatus.SC_OK, "{\"consumed\":false,\"id\":\"asd\",\"itemId\":\"alma\"}");

        //when
        Purchase actual = integrator.buy("alma");

        //then
        MyPurchase expected = new MyPurchase();
        expected.setConsumed(false);
        expected.setId("asd");
        expected.setItemId("alma");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeAbleToConsumeBoughtItemWithoutException() {
        //given
        mockResponse(HttpStatus.SC_OK, "{}");
        MyPurchase boughtItem = new MyPurchase();
        boughtItem.setConsumed(false);
        boughtItem.setId("1");

        //when
        integrator.consume(boughtItem);
    }

    @Test
    public void shouldGetAllPurchases() {
        //given
        mockResponse(HttpStatus.SC_OK, "{\"purchases\":[" +
                "{\"consumed\":true,\"id\":\"asd\",\"itemId\":\"alma\"}," +
                "{\"consumed\":false,\"id\":\"asd2\",\"itemId\":\"alma2\"}" +
                "]}");

        //when
        List<Purchase> actual = integrator.getPurchases();

        //then
        MyPurchase expected1 = new MyPurchase();
        expected1.setConsumed(true);
        expected1.setId("asd");
        expected1.setItemId("alma");

        MyPurchase expected2 = new MyPurchase();
        expected2.setConsumed(false);
        expected2.setId("asd2");
        expected2.setItemId("alma2");

        assertThat(actual).containsOnly(expected1, expected2);
    }

}