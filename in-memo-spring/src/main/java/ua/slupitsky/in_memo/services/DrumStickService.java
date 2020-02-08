package ua.slupitsky.in_memo.services;

import org.springframework.web.multipart.MultipartFile;
import ua.slupitsky.in_memo.dto.DrumStickForm;
import ua.slupitsky.in_memo.entities.DrumStick;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public interface DrumStickService {

    List<DrumStick> findAllDrumSticks();

    List<DrumStickForm> findAllDrumSticksWithResourceBundle(ResourceBundle resourceBundle);

    Optional <DrumStick> findDrumStickById(int id);

    void addDrumStick(DrumStickForm drumStickForm);

    void removeDrumStick(int id);

    void updateDrumStick(DrumStickForm drumStickForm);

    void removeAllDrumSticks();

    void addCollectionDrumSticks(List<DrumStick> drumSticks);

    void uploadPhoto(MultipartFile photo, int drumstickId) throws IOException;

}
