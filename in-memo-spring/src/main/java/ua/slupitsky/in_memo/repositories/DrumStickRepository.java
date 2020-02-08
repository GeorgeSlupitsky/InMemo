package ua.slupitsky.in_memo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.slupitsky.in_memo.entities.DrumStick;

@Repository
public interface DrumStickRepository extends MongoRepository<DrumStick, Integer> {
}
