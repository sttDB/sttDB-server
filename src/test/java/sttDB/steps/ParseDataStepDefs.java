package sttDB.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import sttDB.domain.Sequence;
import sttDB.repository.SequenceRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class ParseDataStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private SequenceRepository sequenceRepository;

    private String fileName;
    private File testFile;

    @Given("^I have a file named \"([^\"]*)\"$")
    public void iHaveAFileNamed(String fileName) throws Throwable {
        this.fileName = fileName;
        ClassLoader classLoader = getClass().getClassLoader();
        testFile = new File(classLoader.getResource("files/" + fileName).getFile());
    }

    @When("^I send the file as Multipart file to \"([^\"]*)\"$")
    public void iSendTheFileAsMultipartFileTo(String url) throws Throwable {
        MockMultipartFile file = createMockMultiPartFile();
        stepDefs.result = stepDefs.mockMvc.perform(
                fileUpload(url)
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
        Page<Sequence> result = sequenceRepository.findByExperiment("tests",
                new PageRequest(0, 20));
        Iterator<Sequence> it = result.iterator();
        Sequence sequence = it.next();

        assertThat(sequence.getTrinityId(), is("comp6_c0_seq1"));
        assertThat(sequence.getTranscript(), is("GGTT"));
        assertThat(sequence.getLength(), is(4));
        assertThat(sequence.getDynamicFastaInfo(), is("len=4 path=[1:0-144 306:145-298]"));
    }

    @When("^I upload the family file to experiment \"([^\"]*)\"$")
    public void iUploadTheFileToExperiment(String experiment) throws Throwable {
        MockMultipartFile file = createMockMultiPartFile();
        stepDefs.result = stepDefs.mockMvc.perform(
                fileUpload("/upload/interpro")
                        .file(file)
                        .header("experiment", experiment)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

    }
}
