package ua.slupitsky.inMemo.services;

import ua.slupitsky.inMemo.models.mongo.DrumStick;

import java.util.List;
import java.util.Optional;

public interface DrumStickService {

    List<DrumStick> findAllDrumSticks();

    Optional <DrumStick> findDrumStickById(int id);

    void addDrumStick(DrumStick drumStick);

    void removeDrumStick(int id);

    void updateDrumStick(int id, DrumStick drumStick);

    void removeAllDrumSticks();

    void addCollectionDrumSticks(List<DrumStick> drumSticks);

}
