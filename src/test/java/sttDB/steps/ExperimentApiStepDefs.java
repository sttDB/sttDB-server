package sttDB.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import sttDB.domain.Experiment;
import sttDB.repository.ExperimentRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ExperimentApiStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private ExperimentRepository experimentRepository;

    private Experiment experiment;

    @Given("^There are (\\d+) experiments in the Database$")
    public void thereAreExperimentsInTheDatabase(int nExperiments) throws Throwable {
        for (int i = 1; i <= nExperiments; i++) {
            Experiment experiment = new Experiment("Experiment-" + i);
            experimentRepository.save(experiment);
        }
    }

    @When("^I get all experiments$")
    public void iGetAllExperiments() throws Throwable {
        String url = "/experiments";
        stepDefs.result = stepDefs.mockMvc.perform(
                get(url).accept(MediaType.APPLICATION_JSON));
    }

    @Then("^I see the experiments \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iSeeTheExperimentsAnd(String expName1, String expName2) throws Throwable {
        stepDefs.result
                .andExpect(jsonPath("$.content[0].name", is(expName1)))
                .andExpect(jsonPath("$.content[1].name", is(expName2)));
    }

    @Given("^I create a experiment with name \"([^\"]*)\"$")
    public void iCreateAExperimentWithName(String expName) throws Throwable {
        this.experiment = new Experiment(expName);
    }

    @When("^I POST the experiment$")
    public void iPOSTTheExperiment() throws Throwable {
        String experimentJson = stepDefs.mapper.writeValueAsString(experiment);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/experiments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(experimentJson)
                        .accept(MediaType.APPLICATION_JSON));

    }

    @When("^I DELETE the experiment with ID \"([^\"]*)\"$")
    public void iDELETETheExperimentWithID(String expID) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/experiments/" + expID)
                        .accept(MediaType.APPLICATION_JSON));
    }

    @When("^I modify the experiment with new name \"([^\"]*)\"$")
    public void iModifyTheExperimentWithNewName(String newExpName) throws Throwable {
        Experiment modifiedExperiment = new Experiment(newExpName);
        String experiment = stepDefs.mapper.writeValueAsString(modifiedExperiment);
        stepDefs.result = stepDefs.mockMvc.perform(
                put("/experiments/Experiment-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(experiment)
                        .accept(MediaType.APPLICATION_JSON));

    }
}