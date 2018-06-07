package sttDB.service.goService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Go;
import sttDB.exception.GoParsingException;
import sttDB.repository.GoRepository;

@Service
public class GoSaver {

    @Autowired
    private GoRepository goRepository;

    void save(String[] parsedGoElements){
        parsedGoElements[0] = decideGoType(parsedGoElements[0]);
        Go go = new Go(parsedGoElements[0], parsedGoElements[1], parsedGoElements[2], parsedGoElements[3], parsedGoElements[4], parsedGoElements[5]);
        goRepository.save(go);
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
}
