package ua.slupitsky.in_memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.in_memo.enums.CDBandOrder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDBandOrderForm {

    private Integer id;

    private String name;

    private CDBandOrder cdBandOrder;

}
