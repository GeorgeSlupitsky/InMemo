package ua.slupitsky.inMemo.models.mongo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ua.slupitsky.inMemo.models.enums.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cds")
public class CD {

    @Id
    @ApiModelProperty(notes = "The database generated CD ID")
    private Integer id;

    @ApiModelProperty(notes = "Band")
    private CDBand band;

    @ApiModelProperty(notes = "Name of album")
    private String album;

    @ApiModelProperty(notes = "Year of album")
    private String year;

    @ApiModelProperty(notes = "Type of booklet")
    private CDBooklet booklet;

    @ApiModelProperty(notes = "Country Edition")
    private CDCountry countryEdition;

    @ApiModelProperty(notes = "Description of CD")
    private CDType cdType;

    @ApiModelProperty(notes = "Group of Collection")
    private CDGroup cdGroup;

    @ApiModelProperty(notes = "Index for comparing")
    private Integer indexWeight;

    @ApiModelProperty(notes = "Autograph availability")
    private Boolean autograph;

    @ApiModelProperty(notes = "Link to Discogs")
    private String discogsLink;

}
