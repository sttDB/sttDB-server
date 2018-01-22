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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class SequenceApiStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private SequenceRepository sequenceRepository;
    private Sequence sequence;

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

    @Given("^I create a sequence with trinirtyId \"([^\"]*)\" and transcript \"([^\"]*)\" in experiment \"([^\"]*)\"$")
    public void iCreateASequenceWithTrinirtyIdAndTranscriptInExperiment(String trinityId,
                                                                        String transcript,
                                                                        String experiment) throws Throwable {
        sequence = new Sequence();
        sequence.setTrinityId(trinityId);
        sequence.setTranscript(transcript);
        sequence.setExperiment(experiment);
    }

    @When("^I POST the sequence$")
    public void iPOSTTheSequence() throws Throwable {
        String sequenceJson = stepDefs.mapper.writeValueAsString(sequence);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/sequences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sequenceJson)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    @When("^I DELETE a sequence with trinityId \"([^\"]*)\" and experiment \"([^\"]*)\"$")
    public void iDELETEASequenceWithTrinityIdAndExperiment(String trinityId, String experiment) throws Throwable {
        Sequence toDelete = sequenceRepository.findByTrinityIdAndExperiment(trinityId, experiment).get(0);
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/sequences/" + toDelete.getId())
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    @When("^I modify the sequence with new trinityId \"([^\"]*)\"$")
    public void iModifyTheSequenceWithNewTrinityId(String newTrinityId) throws Throwable {
        Sequence toUpdate = sequenceRepository.findByTrinityIdAndExperiment("asd", "test").get(0);
        toUpdate.setTrinityId(newTrinityId);
        stepDefs.result = stepDefs.mockMvc.perform(
                put("/sequences/" + toUpdate.getId())
                        .accept(MediaType.APPLICATION_JSON)
        );
    }
}
