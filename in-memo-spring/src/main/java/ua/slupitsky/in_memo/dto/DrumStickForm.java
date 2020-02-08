package ua.slupitsky.in_memo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.slupitsky.in_memo.utils.LocalDateDeserializer;
import ua.slupitsky.in_memo.utils.LocalDateSerializer;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrumStickForm {

    private Integer id;

    private String band;

    private String drummerName;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    private String city;

    private String description;

    private String linkToPhoto;

}
