package sttDB.service.goService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.domain.Go;
import sttDB.exception.GoParsingException;
import sttDB.repository.GoRepository;
import sttDB.repository.SequenceRepository;

@Service
public class GoSaver {

    @Autowired
    private GoRepository goRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    void save(String[] parsedGoElements, Experiment experiment){
        parsedGoElements[0] = decideGoType(parsedGoElements[0]);
        saveGoTerm(parsedGoElements, experiment);
    }

    private String decideGoType(String go){
        switch (go) {
            case "C":
                return "Cellular component";
            case "F":
                return "Mollecular function";
            case "P":
                return "Biological process";
            default: throw new GoParsingException("Go type undefined");
        }
    }

    private void saveGoTerm(String[] parsedGoElements, Experiment experiment) {
        Go go = goRepository.findGoByGoId(parsedGoElements[4]);
        if(go != null){
            addNewTermsToGo(parsedGoElements, go);
        }else{
            go = new Go(parsedGoElements[0], parsedGoElements[1], parsedGoElements[2], parsedGoElements[3], parsedGoElements[4], parsedGoElements[5]);
        }
        goRepository.save(go);
        sequenceRepository.sequenceGoTermUpload(go.getInputAccession(), experiment,go);
    }

    private void addNewTermsToGo(String[] parsedGoElements, Go go) {
        if(!go.getSlimId().contains(parsedGoElements[1])){
            go.addSlimId(parsedGoElements[1]);
        }
        if(go.getGoName().contains(parsedGoElements[2])){
            go.addGoName(parsedGoElements[3]);
        }
    }
}
