package ua.slupitsky.in_memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.in_memo.entities.CDBand;

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

    private Boolean autograph;

    private String discogsLink;

}
