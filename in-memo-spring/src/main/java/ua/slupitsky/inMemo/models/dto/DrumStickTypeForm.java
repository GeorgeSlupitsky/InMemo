package ua.slupitsky.inMemo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.inMemo.models.enums.DrumStickType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrumStickTypeForm {

    private Integer id;
    private String name;
    private DrumStickType drumStickTypeEnum;

}
