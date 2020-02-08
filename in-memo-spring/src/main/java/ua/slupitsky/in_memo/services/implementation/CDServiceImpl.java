package ua.slupitsky.in_memo.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.slupitsky.in_memo.dto.CDForm;
import ua.slupitsky.in_memo.enums.CDBooklet;
import ua.slupitsky.in_memo.enums.CDCountry;
import ua.slupitsky.in_memo.enums.CDGroup;
import ua.slupitsky.in_memo.entities.CD;
import ua.slupitsky.in_memo.enums.CDType;
import ua.slupitsky.in_memo.repositories.CDRepository;
import ua.slupitsky.in_memo.services.CDService;
import ua.slupitsky.in_memo.sorting.CDComparator;
import ua.slupitsky.in_memo.sorting.SortingUtils;

import java.util.*;
import java.util.List;

@Service
public class CDServiceImpl implements CDService {

    //TODO: LOGGING

    private final CDRepository cdRepository;

    @Autowired
    public CDServiceImpl(CDRepository cdRepository) {
        this.cdRepository = cdRepository;
    }

    @Override
    public List<CDForm> findAllCDs(ResourceBundle resourceBundle) {
        List<CD> cdsFromDB = cdRepository.findAll();
        SortingUtils.clearWeight(cdsFromDB);
        SortingUtils.setWeightForSorting(cdsFromDB);
        cdsFromDB.sort(new CDComparator());
        return getCDFormFromEntity(cdsFromDB, resourceBundle);
    }

    @Override
    public List<CD> findByCDGroup(CDGroup cdGroup) {
        return cdRepository.findCDByCdGroup(cdGroup);
    }

    @Override
    public List<CDForm> findByCDGroupWithResourceBundle(CDGroup cdGroup, ResourceBundle resourceBundle) {
        List<CD> cdsFromDB = cdRepository.findCDByCdGroup(cdGroup);
        SortingUtils.clearWeight(cdsFromDB);
        SortingUtils.setWeightForSorting(cdsFromDB);
        cdsFromDB.sort(new CDComparator());
        return getCDFormFromEntity(cdsFromDB, resourceBundle);
    }

    @Override
    public Optional<CD> findCDById(int id) {
        return cdRepository.findById(id);
    }

    @Override
    public void addCD(CDForm cdForm) {
        CD cd = new CD();
        setCDEntityFromCDForm(cd, cdForm);
        cdRepository.save(cd);
    }

    @Override
    public void removeCD(int id) {
        Optional<CD> optionalCD = cdRepository.findById(id);
        if (optionalCD.isPresent()){
            CD cd = optionalCD.get();
            cdRepository.delete(cd);
        }
    }

    @Override
    public void updateCD(CDForm cdForm) {
        Optional<CD> optionalCD = cdRepository.findById(cdForm.getId());
        if (optionalCD.isPresent()){
            CD storedCD = optionalCD.get();
            setCDEntityFromCDForm(storedCD, cdForm);
            cdRepository.save(storedCD);
        }
    }

    @Override
    public void removeAllCDs() {
        cdRepository.deleteAll();
    }

    @Override
    public void addCollectionCD(List<CD> cds) {
        cdRepository.saveAll(cds);
    }

    private List<CDForm> getCDFormFromEntity(List<CD> cdsFromDB, ResourceBundle resourceBundle){
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
            cdForm.setAutograph(cd.getAutograph());
            cdForm.setDiscogsLink(cd.getDiscogsLink());
            cds.add(cdForm);
        }
        return cds;
    }

    private void setCDEntityFromCDForm(CD cd, CDForm cdForm){
        cd.setId(cdForm.getId());
        cd.setBand(cdForm.getBand());
        cd.setAlbum(cdForm.getAlbum());
        cd.setYear(cdForm.getYear());
        cd.setAutograph(cdForm.getAutograph());
        cd.setDiscogsLink(cdForm.getDiscogsLink());
        cd.setBooklet(Enum.valueOf(CDBooklet.class, cdForm.getBooklet()));
        cd.setCdGroup(Enum.valueOf(CDGroup.class, cdForm.getCdGroup()));
        cd.setCdType(Enum.valueOf(CDType.class, cdForm.getCdType()));
        cd.setCountryEdition(Enum.valueOf(CDCountry.class, cdForm.getCountryEdition()));
    }

}
