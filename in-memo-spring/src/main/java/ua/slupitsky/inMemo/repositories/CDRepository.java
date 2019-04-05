package ua.slupitsky.inMemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.slupitsky.inMemo.models.enums.CDGroup;
import ua.slupitsky.inMemo.models.mongo.CD;

import java.util.List;

@Repository
public interface CDRepository extends MongoRepository<CD, Integer> {

    List<CD> findCDByCdGroup(CDGroup cdGroup);

    List<CD> findCDByBand_Name (String bandName);
}
