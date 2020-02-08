package ua.slupitsky.in_memo.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.slupitsky.in_memo.constants.DefaultAppConstants;
import ua.slupitsky.in_memo.dto.DrumStickForm;
import ua.slupitsky.in_memo.entities.DrumStick;
import ua.slupitsky.in_memo.enums.DrumStickCity;
import ua.slupitsky.in_memo.enums.DrumStickType;
import ua.slupitsky.in_memo.repositories.DrumStickRepository;
import ua.slupitsky.in_memo.services.DrumStickService;
import ua.slupitsky.in_memo.sorting.DrumStickComparator;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrumStickServiceImpl implements DrumStickService {

    //TODO: LOGGING

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
    public void addDrumStick(DrumStickForm drumStickForm) {
        DrumStick drumStick = new DrumStick();
        setDrumStickEntityFromDrumStickForm(drumStick, drumStickForm);
        drumStickRepository.save(drumStick);
    }

    @Override
    public void removeDrumStick(int id) {
        Optional <DrumStick> optionalDrumStick = drumStickRepository.findById(id);
        if (optionalDrumStick.isPresent()){
            DrumStick drumStick = optionalDrumStick.get();
            drumStickRepository.delete(drumStick);
        }
    }

    @Override
    public void updateDrumStick(DrumStickForm drumStickForm) {
        Optional<DrumStick> drumStickOptional = drumStickRepository.findById(drumStickForm.getId());
        if (drumStickOptional.isPresent()){
            DrumStick storedDrumStick = drumStickOptional.get();
            setDrumStickEntityFromDrumStickForm(storedDrumStick, drumStickForm);
            drumStickRepository.save(storedDrumStick);
        }
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
        Optional<DrumStick> drumStickOptional = drumStickRepository.findById(drumstickId);
        if (drumStickOptional.isPresent()){
            DrumStick drumStick = drumStickOptional.get();
            if (photo != null){
                int index = Objects.requireNonNull(photo.getOriginalFilename()).lastIndexOf('.');
                String extension = photo.getOriginalFilename().substring(index);
                String path = System.getProperty("catalina.home") + "/images/drumsticks/" + drumStick.getBand() + DefaultAppConstants.PATH_DELIMITER;
                File file = new File(path);
                if(!file.exists()) {
                    file.mkdirs();
                }
                String date = dateTimeFormatter.format(drumStick.getDate());
                file = new File(file, drumStick.getDrummerName() + DefaultAppConstants.UNDERSCORE + date + extension);
                photo.transferTo(file);
                drumStick.setLinkToPhoto("/images/drumsticks/" + drumStick.getBand() + DefaultAppConstants.PATH_DELIMITER +
                        drumStick.getDrummerName() + DefaultAppConstants.UNDERSCORE + date + extension);
                drumStickRepository.save(drumStick);
            }
        }
    }

    private List<DrumStickForm> getDrumStickFormFromEntity(List<DrumStick> drumSticksFromDB, ResourceBundle resourceBundle){
        List<DrumStickForm> drumSticks = new ArrayList<>();
        for (DrumStick drumStick: drumSticksFromDB){
            DrumStickForm drumStickForm = new DrumStickForm();
            drumStickForm.setId(drumStick.getId());
            drumStickForm.setBand(drumStick.getBand());
            drumStickForm.setDrummerName(drumStick.getDrummerName());
            drumStickForm.setDate(drumStick.getDate());
            drumStickForm.setCity(resourceBundle.getString(drumStick.getCity().getName()));
            drumStickForm.setDescription(resourceBundle.getString(drumStick.getDescription().getName()));
            drumStickForm.setLinkToPhoto(drumStick.getLinkToPhoto());
            drumSticks.add(drumStickForm);
        }
        return drumSticks;
    }

    private void setDrumStickEntityFromDrumStickForm(DrumStick drumStick, DrumStickForm drumStickForm){
        drumStick.setId(drumStickForm.getId());
        drumStick.setBand(drumStickForm.getBand());
        drumStick.setDrummerName(drumStickForm.getDrummerName());
        drumStick.setLinkToPhoto(drumStickForm.getLinkToPhoto());
        drumStick.setCity(Enum.valueOf(DrumStickCity.class, drumStickForm.getCity()));
        drumStick.setDescription(Enum.valueOf(DrumStickType.class, drumStickForm.getDescription()));
        drumStick.setDate(drumStickForm.getDate());
    }

}
