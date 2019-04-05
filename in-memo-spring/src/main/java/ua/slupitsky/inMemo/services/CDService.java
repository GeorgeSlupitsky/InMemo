package ua.slupitsky.inMemo.services;

import ua.slupitsky.inMemo.models.dto.CDForm;
import ua.slupitsky.inMemo.models.enums.CDGroup;
import ua.slupitsky.inMemo.models.mongo.CD;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public interface CDService {

    List<CDForm> findAllCDs(ResourceBundle resourceBundle);

    List<CDForm> findByCDGroupWithResourceBundle(CDGroup cdGroup, ResourceBundle resourceBundle);
    
    List<CD> findCDsByBand_Name(String bandName);

    List<CD> findByCDGroup(CDGroup cdGroup);

    Optional<CD> findCDById(int id);

    void addCD(CD cd);

    void removeCD(int id);

    void updateCD(int id, CD cd);

    void removeAllCDs();

    void addCollectionCD(List<CD> cds);
}
