package sttDB.service.fastaServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import sttDB.domain.Experiment;
import sttDB.domain.Sequence;
import sttDB.repository.ExperimentRepository;
import sttDB.repository.SequenceRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

@Component
public class FastaDownloader {

    private SequenceRepository sequenceRepository;

    private ExperimentRepository experimentRepository;

    @Autowired
    public FastaDownloader(SequenceRepository sequenceRepository, ExperimentRepository experimentRepository) {
        this.sequenceRepository = sequenceRepository;
        this.experimentRepository = experimentRepository;
    }

    public File createFasta(String sequenceId, String experiment) throws IOException {
        return experiment.equals("") ? createLargeFastaFile(sequenceId) : createSimpleFastaFile(sequenceId, experiment);
    }

    private File createSimpleFastaFile(String sequenceId, String experimentName) throws IOException {
        PrintWriter writer = new PrintWriter("searchedQuery.fasta", "UTF-8");
        Experiment experiment = experimentRepository.findOne(experimentName);
        insertSequences(writer, sequenceRepository.findByTrinityIdAndExperiment(sequenceId, experiment).iterator());
        writer.close();
        return new File("./searchedQuery.fasta");
    }

    private void insertSequences(PrintWriter writer, Iterator<Sequence> sequenceIterator) {
        while (sequenceIterator.hasNext()) {
            Sequence resultSequence = sequenceIterator.next();
            writer.println(">" + resultSequence.getTrinityId()
                    + " " + resultSequence.getDynamicFastaInfo());
            writer.println(resultSequence.getTranscript());
        }
    }

    private File createLargeFastaFile(String sequenceId) throws IOException {
        PrintWriter writer = new PrintWriter("searchedQuery.fasta", "UTF-8");
        Page<Sequence> sequencePage = sequenceRepository.findByTrinityIdLike(sequenceId, new PageRequest(0,1000));
        insertSequences(writer, sequencePage.iterator());
        while(sequencePage.hasNext()){
            sequencePage = sequenceRepository.findByTrinityIdLike(sequenceId, sequencePage.nextPageable());
            insertSequences(writer, sequencePage.iterator());
        }
        writer.close();
        return new File("./searchedQuery.fasta");
    }
}
