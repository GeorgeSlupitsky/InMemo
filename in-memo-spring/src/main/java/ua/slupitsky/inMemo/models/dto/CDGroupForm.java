package ua.slupitsky.inMemo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.inMemo.models.enums.CDGroup;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDGroupForm {

    private Integer id;
    private String name;
    private CDGroup cdGroupEnum;

}
