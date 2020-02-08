package ua.slupitsky.in_memo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.slupitsky.in_memo.enums.CDGroup;
import ua.slupitsky.in_memo.entities.CD;

import java.util.List;

@Repository
public interface CDRepository extends MongoRepository<CD, Integer> {

    List<CD> findCDByCdGroup(CDGroup cdGroup);

}
