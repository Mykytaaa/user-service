package com.mykytaaa.user.profile.service.e2e.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StepDefinitions {

    @Given("I run a simple test")
    public void runSimpleTest() {}

    @When("The test is running")
    public void testRunning() {}

    @Then("Always assert true")
    public void alwaysAssertTrue() {
        assertTrue(true);
    }
}
