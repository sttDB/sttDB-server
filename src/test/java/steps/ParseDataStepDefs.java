package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class ParseDataStepDefs {

    @Autowired
    private StepDefs stepDefs;

    private File fileWanted;

    @Given("^I have a file named \"([^\"]*)\"$")
    public void iHaveAFileNamed(String fileName) throws Throwable {
        fileWanted = new File("./src/test/resources/files/"+fileName);
    }

    @When("^I send the file$")
    public void aUserSendsAFile() throws Throwable {
        String message = stepDefs.mapper.writeValueAsString(fileWanted);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/uploadFasta")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(message)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Then("^The database has information about DNA$")
    public void theDatabaseHasInformationAboutDNA() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
