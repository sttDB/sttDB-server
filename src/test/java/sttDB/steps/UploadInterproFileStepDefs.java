package sttDB.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import sttDB.domain.Experiment;
import sttDB.domain.Family;
import sttDB.domain.Sequence;
import sttDB.repository.ExperimentRepository;
import sttDB.repository.FamilyRepository;
import sttDB.repository.SequenceRepository;

import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class UploadInterproFileStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private ExperimentRepository experimentRepository;

    private Family familyA;
    private Family familyB;

    @Then("^The families are stored$")
    public void theFamiliesAreStored() throws Throwable {
        familyA = familyRepository.findByInterproId("PF1234");
        familyB = familyRepository.findByInterproId("PF2345");

        assertThat(familyA.getInterproId(), is("PF1234"));
        assertThat(familyA.getDescription(), is("MH1 domain"));
        assertThat(familyB.getInterproId(), is("PF2345"));
        assertThat(familyB.getDescription(), is("CTF/NF-I"));
    }

    @Then("^The families are assigned to the sequences$")
    public void theFamiliesAreAssignedToTheSequences() throws Throwable {
        Sequence sequenceA = sequenceRepository.findByTrinityId("asd").get(0);
        Sequence sequenceB = sequenceRepository.findByTrinityId("asds").get(0);

        assertThat(sequenceA.getFamilies().get(0).getInterproId(), is(familyA.getInterproId()));
        assertThat(sequenceB.getFamilies().get(0).getInterproId(), is(familyB.getInterproId()));
    }

    @And("^There is an experiment named \"([^\"]*)\"$")
    public void thereIsAnExperimentNamed(String experimentName) throws Throwable {
        experimentRepository.save(new Experiment(experimentName));
    }
}
