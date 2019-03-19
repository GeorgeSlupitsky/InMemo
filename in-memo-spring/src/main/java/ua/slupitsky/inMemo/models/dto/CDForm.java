package ua.slupitsky.inMemo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.inMemo.models.mongo.CDBand;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDForm {

    private Integer id;
    private CDBand band;
    private String album;
    private String year;
    private String booklet;
    private String countryEdition;
    private String cdType;
    private String cdGroup;

}
