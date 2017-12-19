package sttDB.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .content(message)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Then("^The database has information about DNA$")
    public void theDatabaseHasInformationAboutDNA() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/sequences/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.length", is(4)))
                .andExpect(jsonPath("$.trinityId", is("comp6_c0_seq1")))
                .andExpect(jsonPath("$.transcript", is("GGTT")))
                .andExpect(jsonPath("$.experiment", is("tests.fasta")));
    }
}
