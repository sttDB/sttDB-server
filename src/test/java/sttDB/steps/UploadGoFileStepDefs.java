package sttDB.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import sttDB.domain.Go;
import sttDB.domain.Sequence;
import sttDB.repository.GoRepository;
import sttDB.repository.SequenceRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UploadGoFileStepDefs {

    @Autowired
    private GoRepository goRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    private Go goA;
    private Go goB;

    @Then("^The go terms are stored$")
    public void goTermsStored(){
        goA = goRepository.findGoByGoId("GO:0008622");
        goB = goRepository.findGoByGoId("GO:0016887");

        assertThat(goA.getGoType(), is("Cellular component"));
        assertThat(goA.getSlimId(), is("GO:0000228"));
        assertThat(goA.getGoName(), is("nuclear chromosome"));
        assertThat(goA.getInputAccession(), is("asd"));
        assertThat(goA.getDescription(), is("epsilon DNA polymerase complex"));

        assertThat(goB.getGoType(), is("Mollecular function"));
        assertThat(goB.getSlimId(), is("GO:0016887"));
        assertThat(goB.getGoName(), is("ATPase activity"));
        assertThat(goB.getInputAccession(), is("asds"));
        assertThat(goB.getDescription(), is("ATPase activity"));
    }

    @And("^The go terms are assigned to the sequences$")
    public void assignedGoTerm(){
        Sequence sequenceA = sequenceRepository.findByTrinityId("asd").get(0);
        Sequence sequenceB = sequenceRepository.findByTrinityId("asds").get(0);

        assertThat(((Go)sequenceA.getDomainInfoValue("go").get(0)).getGoId(), is(goA.getGoId()));
        assertThat(((Go)sequenceB.getDomainInfoValue("go").get(0)).getGoId(), is(goB.getGoId()));
    }

}
