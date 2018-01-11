package com.funflowers.integration;

import com.flexionmobile.codingchallenge.integration.Integration;
import com.flexionmobile.codingchallenge.integration.IntegrationTestRunner;
import com.funflowers.FunFlowerFlexionMobileIntegrator;
import com.funflowers.HttpRequestFactory;
import org.junit.Test;

public class FunFlowerFlexionMobileIntegratorIT {

    @Test
    public void defaultIntegrationTest() {
        //given
        Integration integration = new FunFlowerFlexionMobileIntegrator(new HttpRequestFactory());
        IntegrationTestRunner runner = new IntegrationTestRunner();

        //when + then
        runner.runTests(integration);
    }

}