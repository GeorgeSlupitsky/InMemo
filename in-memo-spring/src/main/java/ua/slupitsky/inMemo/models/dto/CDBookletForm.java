package ua.slupitsky.inMemo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.inMemo.models.enums.CDBooklet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDBookletForm {

    private Integer id;
    private String name;
    private CDBooklet cdBookletEnum;

}