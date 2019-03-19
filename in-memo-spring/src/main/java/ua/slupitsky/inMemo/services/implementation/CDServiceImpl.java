package ua.slupitsky.inMemo.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.slupitsky.inMemo.models.dto.CDForm;
import ua.slupitsky.inMemo.models.enums.CDGroup;
import ua.slupitsky.inMemo.models.mongo.CD;
import ua.slupitsky.inMemo.repositories.CDRepository;
import ua.slupitsky.inMemo.services.CDService;
import ua.slupitsky.inMemo.sorting.CDComparator;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CDServiceImpl implements CDService {

    private final CDRepository cdRepository;

    @Autowired
    public CDServiceImpl(CDRepository cdRepository) {
        this.cdRepository = cdRepository;
    }

    @Override
    public List<CDForm> findAllCDs(ResourceBundle resourceBundle) {
        List <CD> cdsFromDB = cdRepository.findAll().stream().sorted(new CDComparator()).collect(Collectors.toList());
        List<CDForm> cds = new ArrayList<>();
        for (CD cd: cdsFromDB){
            CDForm cdForm = new CDForm();
            cdForm.setId(cd.getId());
            cdForm.setBand(cd.getBand());
            cdForm.setAlbum(cd.getAlbum());
            cdForm.setYear(cd.getYear());
            if (cd.getBooklet().getQuantityOfPages() != 0){
                cdForm.setBooklet(cd.getBooklet().getQuantityOfPages() + " " + resourceBundle.getString(cd.getBooklet().getName()));
            } else {
                cdForm.setBooklet(resourceBundle.getString(cd.getBooklet().getName()));
            }
            cdForm.setCountryEdition(resourceBundle.getString(cd.getCountryEdition().getName()));
            cdForm.setCdType(resourceBundle.getString(cd.getCdType().getName()));
            cds.add(cdForm);
        }
        return cds;
    }

    @Override
    public Optional<CD> findCDById(int id) {
        return cdRepository.findById(id);
    }

    @Override
    public void addCD(CD cd) {
        cdRepository.save(cd);
    }

    @Override
    public void removeCD(int id) {
        CD cd = cdRepository.findById(id).get();
        cdRepository.delete(cd);
    }

    @Override
    public void updateCD(int id, CD cd) {
        CD storedCD = cdRepository.findById(id).get();
        storedCD.setAlbum(cd.getAlbum());
        storedCD.setBand(cd.getBand());
        storedCD.setBooklet(cd.getBooklet());
        storedCD.setCountryEdition(cd.getCountryEdition());
        storedCD.setYear(cd.getYear());
        storedCD.setCdGroup(cd.getCdGroup());
        storedCD.setCdType(cd.getCdType());
        cdRepository.save(storedCD);
    }

    @Override
    public List<CD> findByCDGroup(CDGroup cdGroup) {
        return cdRepository.findCDByCdGroup(cdGroup);
    }

    @Override
    public void removeAllCDs() {
        cdRepository.deleteAll();
    }

    @Override
    public void addCollectionCD(List<CD> cds) {
        cdRepository.saveAll(cds);
    }
}
