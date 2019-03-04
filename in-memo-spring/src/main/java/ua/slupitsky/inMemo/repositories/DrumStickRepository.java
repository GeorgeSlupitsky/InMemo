package ua.slupitsky.inMemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.slupitsky.inMemo.models.mongo.DrumStick;

@Repository
public interface DrumStickRepository extends MongoRepository<DrumStick, Integer> {
}
