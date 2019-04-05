package ua.slupitsky.inMemo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.inMemo.models.enums.CDBandOrder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDBandOrderForm {

    private Integer id;
    private String name;
    private CDBandOrder cdBandOrder;

}
