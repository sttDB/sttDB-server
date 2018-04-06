package sttDB.steps;

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

public class FamilyApiComplexStepDefs {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private StepDefs stepDefs;

    /*X = asd Y = bnm Z = xcv*/
    @Given("^I have some families in the DataBase$")
    public void iHaveTwoFamiliesInTheDataBase() {
        Family families = new Family();
        families.setInterproId("1");
        families.setDescription("asd");
        familyRepository.save(families);
        Family families2 = new Family();
        families2.setInterproId("2");
        families2.setDescription("bnm");
        familyRepository.save(families2);
        Family families3 = new Family();
        families3.setInterproId("3");
        families3.setDescription("xcv");
        familyRepository.save(families3);
        Family families4 = new Family();
        families4.setInterproId("4");
        families4.setDescription("asd bnm");
        familyRepository.save(families4);
        Family families5 = new Family();
        families5.setInterproId("5");
        families5.setDescription("asd xcv");
        familyRepository.save(families5);
    }

    @When("^I use the complex family route \"([^\"]*)\"$")
    public void iUseFamilyRoute(String url) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(url)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @And("^The family has the correct description \"([^\"]*)\"$")
    public void theFamilyIsCorrect(String resultDescription) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.content[0].description", is(resultDescription)));
    }
}
