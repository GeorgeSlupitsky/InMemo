package ua.slupitsky.inMemo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.inMemo.models.enums.CDCountry;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDCountryForm {

    private Integer id;
    private String name;
    private CDCountry cdCountryEnum;

}
