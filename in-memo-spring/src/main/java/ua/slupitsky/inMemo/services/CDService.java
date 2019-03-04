package ua.slupitsky.inMemo.services;

import ua.slupitsky.inMemo.models.enums.CDGroup;
import ua.slupitsky.inMemo.models.mongo.CD;

import java.util.List;
import java.util.Optional;

public interface CDService {

    List<CD> findAllCDs();

    Optional<CD> findCDById(int id);

    void addCD(CD cd);

    void removeCD(int id);

    void updateCD(int id, CD cd);

    List<CD> findByCDGroup(CDGroup cdGroup);

    void removeAllCDs();

    void addCollectionCD(List<CD> cds);
}
