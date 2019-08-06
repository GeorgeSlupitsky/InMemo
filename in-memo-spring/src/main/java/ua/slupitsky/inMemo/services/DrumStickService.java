package ua.slupitsky.inMemo.services;

import org.springframework.web.multipart.MultipartFile;
import ua.slupitsky.inMemo.models.dto.DrumStickForm;
import ua.slupitsky.inMemo.models.mongo.DrumStick;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public interface DrumStickService {

    List<DrumStick> findAllDrumSticks();

    List<DrumStickForm> findAllDrumSticksWithResourceBundle(ResourceBundle resourceBundle);

    Optional <DrumStick> findDrumStickById(int id);

    void addDrumStick(DrumStick drumStick);

    void removeDrumStick(int id);

    void updateDrumStick(int id, DrumStick drumStick);

    void removeAllDrumSticks();

    void addCollectionDrumSticks(List<DrumStick> drumSticks);

    void uploadPhoto(MultipartFile photo, int drumstickId) throws IOException;

}
