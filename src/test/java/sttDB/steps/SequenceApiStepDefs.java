package sttDB.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import sttDB.domain.Sequence;
import sttDB.repository.SequenceRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class SequenceApiStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Given("^I have two sequences in the DataBase$")
    public void iHaveTwoSequencesInTheDataBase() {
        Sequence sequence = new Sequence();
        sequence.setTrinityId("asd");
        sequence.setExperiment("test");
        sequence.setTranscript("BBC");
        sequenceRepository.save(sequence);
        Sequence sequence2 = new Sequence();
        sequence2.setTrinityId("asds");
        sequence2.setExperiment("test");
        sequence2.setTranscript("CCB");
        sequenceRepository.save(sequence2);
    }

    @When("^I use sequence route \"([^\"]*)\"$")
    public void iUseRoute(String url) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(url)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @And("^The sequences are correct$")
    public void theSequencesAreCorrect() throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content[0].trinityId", is("asd")))
                .andExpect(jsonPath("$.content[0].transcript", is("BBC")))
                .andExpect(jsonPath("$.content[0].experiment", is("test")))
                .andExpect(jsonPath("$.content[1].trinityId", is("asds")))
                .andExpect(jsonPath("$.content[1].transcript", is("CCB")))
                .andExpect(jsonPath("$.content[1].experiment", is("test")));
    }

    @And("^The sequence is correct$")
    public void theSequenceIsCorrect() throws Throwable {
        stepDefs.result.andExpect(jsonPath("$[0].trinityId", is("asd")))
                .andExpect(jsonPath("$[0].transcript", is("BBC")))
                .andExpect(jsonPath("$[0].experiment", is("test")));
    }
}
