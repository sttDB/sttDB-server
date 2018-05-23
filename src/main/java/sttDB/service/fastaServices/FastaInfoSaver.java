package sttDB.service.fastaServices;

import sttDB.domain.Experiment;

interface FastaInfoSaver {
    void deleteOldSequences(Experiment experiment);
    void saveInfo(String[] sequenceLine, Experiment experiment);
}
