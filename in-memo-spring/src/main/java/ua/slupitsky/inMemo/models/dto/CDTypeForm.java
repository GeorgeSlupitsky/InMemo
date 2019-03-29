package ua.slupitsky.inMemo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.inMemo.models.enums.CDType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDTypeForm {

    private Integer id;
    private String name;
    private CDType cdTypeEnum;

}
