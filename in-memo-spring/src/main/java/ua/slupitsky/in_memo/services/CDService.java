package ua.slupitsky.in_memo.services;

import ua.slupitsky.in_memo.dto.CDForm;
import ua.slupitsky.in_memo.enums.CDGroup;
import ua.slupitsky.in_memo.entities.CD;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public interface CDService {

    List<CDForm> findAllCDs(ResourceBundle resourceBundle);

    List<CDForm> findByCDGroupWithResourceBundle(CDGroup cdGroup, ResourceBundle resourceBundle);

    List<CD> findByCDGroup(CDGroup cdGroup);

    Optional<CD> findCDById(int id);

    void addCD(CDForm cdForm);

    void removeCD(int id);

    void updateCD(CDForm cdForm);

    void removeAllCDs();

    void addCollectionCD(List<CD> cds);

}
