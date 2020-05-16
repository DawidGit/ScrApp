package pl.widulinski.scrapp.searchData;

import lombok.Data;
import pl.widulinski.scrapp.enums.Categories;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;


@Data
public class PreparedDataToSrchDto {

    @NotEmpty
    private String shop;

    @Enumerated(EnumType.STRING)
    private Categories categories;

}
