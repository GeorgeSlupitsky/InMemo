package ua.slupitsky.in_memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.in_memo.enums.CDCountry;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDCountryForm {

    private Integer id;

    private String name;

    private CDCountry cdCountryEnum;

}
