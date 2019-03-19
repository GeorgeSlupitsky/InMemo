package ua.slupitsky.inMemo.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.slupitsky.inMemo.models.dto.DrumStickForm;
import ua.slupitsky.inMemo.models.mongo.DrumStick;
import ua.slupitsky.inMemo.repositories.DrumStickRepository;
import ua.slupitsky.inMemo.services.DrumStickService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
public class DrumStickServiceImpl implements DrumStickService {

    private final DrumStickRepository drumStickRepository;

    @Autowired
    public DrumStickServiceImpl(DrumStickRepository drumStickRepository) {
        this.drumStickRepository = drumStickRepository;
    }

    @Override
    public List<DrumStickForm> findAllDrumSticksWithBundle(ResourceBundle resourceBundle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<DrumStick> drumSticksFromDB = drumStickRepository.findAll();
        List<DrumStickForm> drumSticks = new ArrayList<>();
        for (DrumStick drumStick: drumSticksFromDB){
            DrumStickForm drumStickForm = new DrumStickForm();
            drumStickForm.setId(drumStick.getId());
            drumStickForm.setBand(drumStick.getBand());
            drumStickForm.setDrummerName(drumStick.getDrummerName());
            drumStickForm.setDate(formatter.format(drumStick.getDate()));
            drumStickForm.setCity(resourceBundle.getString(drumStick.getCity().getName()));
            drumStickForm.setDescription(resourceBundle.getString(drumStick.getDescription().getName()));
            drumSticks.add(drumStickForm);
        }
        return drumSticks;
    }

    @Override
    public List<DrumStick> findAllDrumSticks() {
        return drumStickRepository.findAll();
    }

    @Override
    public Optional<DrumStick> findDrumStickById(int id) {
        return drumStickRepository.findById(id);
    }

    @Override
    public void addDrumStick(DrumStick drumStick) {
        drumStickRepository.save(drumStick);
    }

    @Override
    public void removeDrumStick(int id) {
        DrumStick drumStick = drumStickRepository.findById(id).get();
        drumStickRepository.delete(drumStick);
    }

    @Override
    public void updateDrumStick(int id, DrumStick drumStick) {
        DrumStick storedDrumStick = drumStickRepository.findById(id).get();
        storedDrumStick.setBand(drumStick.getBand());
        storedDrumStick.setCity(drumStick.getCity());
        storedDrumStick.setDrummerName(drumStick.getDrummerName());
        storedDrumStick.setDate(drumStick.getDate());
        storedDrumStick.setDescription(drumStick.getDescription());
        drumStickRepository.save(storedDrumStick);
    }

    @Override
    public void removeAllDrumSticks() {
        drumStickRepository.deleteAll();
    }

    @Override
    public void addCollectionDrumSticks(List<DrumStick> drumSticks) {
        drumStickRepository.saveAll(drumSticks);
    }
}
