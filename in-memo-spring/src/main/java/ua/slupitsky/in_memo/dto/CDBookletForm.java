package ua.slupitsky.in_memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.in_memo.enums.CDBooklet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDBookletForm {

    private Integer id;

    private String name;

    private CDBooklet cdBookletEnum;

}
