package ua.slupitsky.in_memo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.in_memo.enums.CDBandOrder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDBand {

    private String name;

    private CDBandOrder order;

    private List<CDBandMainMember> bandMembers;

}
