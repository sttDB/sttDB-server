package sttDB.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import sttDB.domain.Family;
import sttDB.repository.FamilyRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class FamilyApiStepdefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private FamilyRepository familyRepository;

    @Given("^I have one families in the DataBase$")
    public void iHaveTwoFamiliesInTheDataBase() {
        Family families = new Family();
        families.setInterproId("asd");
        families.setDescription("protein");
        familyRepository.save(families);
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
}
