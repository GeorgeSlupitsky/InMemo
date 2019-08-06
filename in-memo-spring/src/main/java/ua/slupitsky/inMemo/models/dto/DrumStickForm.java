package ua.slupitsky.inMemo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrumStickForm {

    private Integer id;
    private String band;
    private String drummerName;
    private String date;
    private String city;
    private String description;
    private String linkToPhoto;

}
