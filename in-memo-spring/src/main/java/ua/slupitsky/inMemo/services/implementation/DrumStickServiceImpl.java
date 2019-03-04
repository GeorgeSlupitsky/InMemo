package ua.slupitsky.inMemo.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.slupitsky.inMemo.models.mongo.DrumStick;
import ua.slupitsky.inMemo.repositories.DrumStickRepository;
import ua.slupitsky.inMemo.services.DrumStickService;

import java.util.List;
import java.util.Optional;

@Service
public class DrumStickServiceImpl implements DrumStickService {

    private final DrumStickRepository drumStickRepository;

    @Autowired
    public DrumStickServiceImpl(DrumStickRepository drumStickRepository) {
        this.drumStickRepository = drumStickRepository;
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
