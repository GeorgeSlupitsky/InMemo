package ua.slupitsky.in_memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.in_memo.enums.DrumStickType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrumStickTypeForm {

    private Integer id;

    private String name;

    private DrumStickType drumStickTypeEnum;

}
