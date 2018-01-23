package sttDB.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import sttDB.service.storageService.StorageService;

import java.io.File;
import java.io.FileInputStream;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DownloadExperimentfilesStepDefs {

    @Autowired
    private StorageService storageService;

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

    @And("^The file \"([^\"]*)\" is stored under experiment \"([^\"]*)\"$")
    public void theFileIsStoredUnder(String fileName, String experiment) throws Throwable {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("files/" + fileName).getFile());

        MockMultipartFile toStore = new MockMultipartFile("file", fileName,
                MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(file));

        storageService.storeFileInExperiment(toStore, experiment);
    }

    @And("^The received list of files contains the file \"([^\"]*)\"$")
    public void theReceivedListOfFilesContainsTheFile(String fileName) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$", contains(fileName)));
    }
}
