package ua.slupitsky.inMemo.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.inMemo.models.enums.CDBandOrder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDBand {

    private String name;

    private CDBandOrder order;

    private List<CDBandMainMember> bandMembers;

}
