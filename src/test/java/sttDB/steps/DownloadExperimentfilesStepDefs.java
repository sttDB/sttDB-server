package sttDB.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class DownloadExperimentfilesStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Given("^There are no experiments$")
    public void thereAreNoExperiments() throws Throwable {
        // No experiments to add
    }

    @When("^I perform a GET to the url \"([^\"]*)\"$")
    public void iPerformAGETToTheUrl(String url) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(url)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }
}
