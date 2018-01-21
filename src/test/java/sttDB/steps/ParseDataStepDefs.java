package sttDB.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ParseDataStepDefs {

    @Autowired
    private StepDefs stepDefs;

    private String fileName;
    private File testFile;

    @Given("^I have a file named \"([^\"]*)\"$")
    public void iHaveAFileNamed(String fileName) throws Throwable {
        this.fileName = fileName;
        ClassLoader classLoader = getClass().getClassLoader();
        testFile = new File(classLoader.getResource("files/" + fileName).getFile());
    }

    @When("^I send the file as Multipart file$")
    public void aUserSendsAFile() throws Throwable {
        MockMultipartFile file = createMockMultiPartFile();
        stepDefs.result = stepDefs.mockMvc.perform(
                fileUpload("/upload/fasta")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    private MockMultipartFile createMockMultiPartFile() throws IOException {
        return new MockMultipartFile("file", fileName,
                MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(testFile));
    }

    @Then("^The database has information about DNA$")
    public void theDatabaseHasInformationAboutDNA() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/sequences?trinityId={comp6_c0_seq1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.length", is(4)))
                .andExpect(jsonPath("$.trinityId", is("comp6_c0_seq1")))
                .andExpect(jsonPath("$.transcript", is("GGTT")))
                .andExpect(jsonPath("$.experiment.name", is("tests.fasta")));
    }
}
