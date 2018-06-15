package sttDB.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import sttDB.domain.Kegg;
import sttDB.domain.Sequence;
import sttDB.repository.KeggRepository;
import sttDB.repository.SequenceRepository;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class UploadKeggStepDefs {

    @Autowired
    KeggRepository keggRepository;

    @Autowired
    SequenceRepository sequenceRepository;

    private Kegg keggA;

    @Then("^The kegg terms are stored$")
    public void keggTermsStored() {
        keggA = keggRepository.findByKeggId("K00002");

        assertTrue(keggA.getPath1().contains("Metabolism"));
        assertTrue(keggA.getPath2().contains("Global and overview maps"));
        assertTrue(keggA.getPath3().containsAll(Arrays.asList("01100 Metabolic pathways (746)",
                "01110 Biosynthesis of secondary metabolites (215)")));
    }

    @And("^The kegg terms are assigned to the sequences$")
    public void assignedkeggTerm() {
        Sequence sequenceA = sequenceRepository.findByTrinityId("asd").get(0);
        Sequence sequenceB = sequenceRepository.findByTrinityId("asds").get(0);

        assertThat(((Kegg) sequenceA.getDomainInfoValue("kegg").get(0)).getKeggId(), is(keggA.getKeggId()));
        assertThat(((Kegg) sequenceB.getDomainInfoValue("kegg").get(0)).getKeggId(), is(keggA.getKeggId()));
    }
}
