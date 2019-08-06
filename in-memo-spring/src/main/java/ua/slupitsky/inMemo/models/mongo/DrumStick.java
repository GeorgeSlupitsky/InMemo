package ua.slupitsky.inMemo.models.mongo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;
import ua.slupitsky.inMemo.models.enums.DrumStickCity;
import ua.slupitsky.inMemo.models.enums.DrumStickType;
import ua.slupitsky.inMemo.utils.LocalDateDeserializer;
import ua.slupitsky.inMemo.utils.LocalDateSerializer;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "drum_sticks")
public class DrumStick {

    @Id
    @ApiModelProperty(notes = "The database generated Drum Stick ID")
    private Integer id;

    @ApiModelProperty(notes = "Name of band")
    private String band;

    @ApiModelProperty(notes = "Drummer's name")
    private String drummerName;

    @ApiModelProperty(notes = "Date of receiving Drum Stick")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    @ApiModelProperty(notes = "Name of city, where the concert was")
    private DrumStickCity city;

    @ApiModelProperty(notes = "Description of Drum Stick")
    private DrumStickType description;

    @ApiModelProperty(notes = "Link to drummer's photo")
    private String linkToPhoto;

}
