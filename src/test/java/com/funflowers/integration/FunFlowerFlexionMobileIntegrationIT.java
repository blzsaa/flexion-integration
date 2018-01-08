package com.funflowers.integration;

import com.flexionmobile.codingchallenge.integration.Integration;
import com.flexionmobile.codingchallenge.integration.IntegrationTestRunner;
import com.funflowers.FunFlowerFlexionMobileIntegration;
import com.funflowers.HttpRequestFactory;
import com.google.gson.Gson;
import org.junit.Test;

public class FunFlowerFlexionMobileIntegrationIT {

    @Test
    public void integration() {
        //given
        Integration integration = new FunFlowerFlexionMobileIntegration( new HttpRequestFactory());
        IntegrationTestRunner runner = new IntegrationTestRunner();

        //when + then
        runner.runTests(integration);
    }
}