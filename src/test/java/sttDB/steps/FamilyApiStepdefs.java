package sttDB.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import sttDB.domain.Family;
import sttDB.domain.PartialSequence;
import sttDB.domain.Sequence;
import sttDB.repository.FamilyRepository;
import sttDB.repository.SequenceRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class FamilyApiStepdefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    private Family family;

    @Given("^I have one families in the DataBase$")
    public void iHaveTwoFamiliesInTheDataBase() {
        Family families = new Family();
        families.setInterproId("asd");
        families.setDescription("protein");
        familyRepository.save(families);
    }

    @And("^A sequence with that family$")
    public void aSequenceWithThatFamily() throws Throwable {
        Sequence sequence = new Sequence();
        sequence.setTrinityId("asd");
        sequence.setExperiment("test");
        sequence.addFamily(familyRepository.findOne("asd"));
        sequenceRepository.save(sequence);
    }

    @When("^I use family route \"([^\"]*)\"$")
    public void iUseFamilyRoute(String url) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(url)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @And("^The family is correct$")
    public void theFamilyIsCorrect() throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.content[0].interproId", is("asd")))
                .andExpect(jsonPath("$.content[0].description", is("protein")))
                .andExpect(jsonPath("$.totalElements", is(1)));
    }

    @And("^The proteins are correct$")
    public void thePartialProteinsAreCorrect() throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.content[0].trinityId", is("asd")))
                .andExpect(jsonPath("$.content[0].experiment", is("test")));
    }

    @Given("^I create a family with interpro ID \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void iCreateAFamilyWithNameAndDescription(String interproId, String description) throws Throwable {
        family = new Family();
        family.setInterproId(interproId);
        family.setDescription(description);
    }

    @When("^I POST the family$")
    public void iPOSTTheFamily() throws Throwable {
        String familyJson = stepDefs.mapper.writeValueAsString(family);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/families")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(familyJson)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    @When("^I DELETE the family with interproID \"([^\"]*)\"$")
    public void iDELETETheFamilyWithInterproID(String interproId) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/families/" + interproId)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
    }

    @When("^I modify the family with new interproId \"([^\"]*)\"$")
    public void iModifyTheFamilyWithNewInterproId(String newInterproId) throws Throwable {
        Family family = familyRepository.findOne("asd");
        family.setInterproId(newInterproId);
        String familyJson = stepDefs.mapper.writeValueAsString(family);
        stepDefs.result = stepDefs.mockMvc.perform(
                put("/families/asd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(familyJson)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }
}
