package ua.slupitsky.inMemo.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.slupitsky.inMemo.models.dto.DrumStickForm;
import ua.slupitsky.inMemo.models.mongo.DrumStick;
import ua.slupitsky.inMemo.repositories.DrumStickRepository;
import ua.slupitsky.inMemo.services.DrumStickService;
import ua.slupitsky.inMemo.sorting.DrumStickComparator;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Service
public class DrumStickServiceImpl implements DrumStickService {

    private final DrumStickRepository drumStickRepository;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    public DrumStickServiceImpl(DrumStickRepository drumStickRepository) {
        this.drumStickRepository = drumStickRepository;
    }

    @Override
    public List<DrumStick> findAllDrumSticks() {
        return drumStickRepository.findAll().stream().sorted(new DrumStickComparator()).collect(Collectors.toList());
    }

    @Override
    public List<DrumStickForm> findAllDrumSticksWithResourceBundle(ResourceBundle resourceBundle) {
        List<DrumStick> drumSticksFromDB = drumStickRepository.findAll();
        drumSticksFromDB.sort(new DrumStickComparator());
        return getDrumStickFormFromEntity(drumSticksFromDB, resourceBundle);
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

    @Override
    public void uploadPhoto(MultipartFile photo, int drumstickId) throws IOException {
        DrumStick drumStick = drumStickRepository.findById(drumstickId).get();
        if (photo != null){
            int index = Objects.requireNonNull(photo.getOriginalFilename()).lastIndexOf(".");
            String extension = photo.getOriginalFilename().substring(index);
            String path = System.getProperty("catalina.home") + "/images/drumsticks/" + drumStick.getBand() + "/";
            File file = new File(path);
            if(!file.exists()) {
                file.mkdirs();
            }
            String date = dateTimeFormatter.format(drumStick.getDate());
            file = new File(file, drumStick.getDrummerName() + "_" + date + extension);
            photo.transferTo(file);
            drumStick.setLinkToPhoto("/images/drumsticks/" + drumStick.getBand() + "/" + drumStick.getDrummerName() + "_" + date + extension);
            drumStickRepository.save(drumStick);
        }
    }

    private List<DrumStickForm> getDrumStickFormFromEntity(List<DrumStick> drumSticksFromDB, ResourceBundle resourceBundle){
        List<DrumStickForm> drumSticks = new ArrayList<>();
        for (DrumStick drumStick: drumSticksFromDB){
            DrumStickForm drumStickForm = new DrumStickForm();
            drumStickForm.setId(drumStick.getId());
            drumStickForm.setBand(drumStick.getBand());
            drumStickForm.setDrummerName(drumStick.getDrummerName());
            drumStickForm.setDate(dateTimeFormatter.format(drumStick.getDate()));
            drumStickForm.setCity(resourceBundle.getString(drumStick.getCity().getName()));
            drumStickForm.setDescription(resourceBundle.getString(drumStick.getDescription().getName()));
            drumStickForm.setLinkToPhoto(drumStick.getLinkToPhoto());
            drumSticks.add(drumStickForm);
        }
        return drumSticks;
    }
}
