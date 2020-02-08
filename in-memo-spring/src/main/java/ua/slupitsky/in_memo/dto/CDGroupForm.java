package ua.slupitsky.in_memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.in_memo.enums.CDGroup;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDGroupForm {

    private Integer id;

    private String name;

    private CDGroup cdGroupEnum;

}
