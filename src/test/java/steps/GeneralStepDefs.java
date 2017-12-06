package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;

public class GeneralStepDefs {
    @Then("^I get (\\d+) http response$")
    public void iGetHttpResponse(int responseNumber) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
