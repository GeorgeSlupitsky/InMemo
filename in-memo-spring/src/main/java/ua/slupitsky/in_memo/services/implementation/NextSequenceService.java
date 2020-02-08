package ua.slupitsky.in_memo.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ua.slupitsky.in_memo.entities.CustomSequences;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class NextSequenceService {

    private final MongoOperations mongo;

    @Autowired
    public NextSequenceService(MongoOperations mongo) {
        this.mongo = mongo;
    }

    public int getNextSequence(String seqName){
        CustomSequences counter = mongo.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq",1),
                options().returnNew(true).upsert(true),
                CustomSequences.class);
        return Objects.requireNonNull(counter).getSeq();
    }

}
