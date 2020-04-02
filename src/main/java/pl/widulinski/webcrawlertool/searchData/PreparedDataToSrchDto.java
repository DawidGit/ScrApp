package pl.widulinski.webcrawlertool.searchData;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.widulinski.webcrawlertool.enums.Categories;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Component
@Data
public class PreparedDataToSrchDto {

    @NotEmpty
    private String shop;

    @Enumerated(EnumType.STRING)
    private Categories categories;

}
