package ua.slupitsky.inMemo.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CDBand {

    private String name;

    private Integer order;

    private List<CDBandMainMember> bandMembers;

}
