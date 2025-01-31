package com.mykytaaa.user.profile.service.e2e.steps;

import com.mykytaaa.user.profile.service.e2e.assertions.FactoryDtoAssertHandler;
import com.mykytaaa.user.profile.service.e2e.util.TestContext;
import com.mykytaaa.user.profile.service.e2e.util.TestContextStorage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class HttpResponseStep {

    /**
     * Factory for generating DTO assert handlers.
     */
    private final FactoryDtoAssertHandler factoryDtoAssertHandler;

    /**
     * Verifies that the actual HTTP response code matches the expected status code.
     *
     * @param expectedStatusCode The expected HTTP status code.
     */
    @Then("response code is {int}")
    public void responseCodeIs(int expectedStatusCode) {
        final int actualStatusCode = TestContextStorage.getTestContext().getStatusCode();
        assertThat(actualStatusCode).isEqualTo(expectedStatusCode);
    }

    /**
     * Asserts that the response body contains the expected data as specified in the DataTable.
     *
     * @param dataTable The DataTable containing expected data to be validated against the response body.
     */
    @And("response body contains:")
    public void responseBodyContains(DataTable dataTable) {
        final TestContext responseContext = TestContextStorage.getTestContext();
        final Object responseBody = responseContext.response().getBody();

        factoryDtoAssertHandler.getInstance(responseContext.operation())
                .assertResponseBody(dataTable, responseBody);

        TestContextStorage.clearContext();
    }
}
