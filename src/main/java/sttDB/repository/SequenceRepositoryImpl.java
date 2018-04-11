package sttDB.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import sttDB.domain.Experiment;
import sttDB.domain.Family;
import sttDB.domain.Sequence;

import java.util.List;

class SequenceRepositoryImpl implements CustomSequenceRepository {

    @Autowired
    MongoOperations mongoOperation;


    @Override
    public void sequenceFamiliesUpload(String trinityId, Experiment experiment, List<Family> family) {

        Query query = new Query();
        query.addCriteria(Criteria.where("trinityId").is(trinityId).and("experiment").is(experiment.getName()));


        Update update = new Update();
        update.pushAll("domainInfo.families", family.toArray());

        mongoOperation.updateMulti(query, update, Sequence.class);
    }
}
