package ua.slupitsky.in_memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.in_memo.enums.DrumStickCity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrumStickCityForm {

    private Integer id;

    private String name;

    private DrumStickCity drumStickCityEnum;

}
