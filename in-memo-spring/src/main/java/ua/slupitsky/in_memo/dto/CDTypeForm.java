package ua.slupitsky.in_memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.in_memo.enums.CDType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDTypeForm {

    private Integer id;

    private String name;

    private CDType cdTypeEnum;

}
