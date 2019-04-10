package ua.slupitsky.inMemo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.inMemo.models.enums.DrumStickCity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrumStickCityForm {

    private Integer id;
    private String name;
    private DrumStickCity drumStickCityEnum;

}
