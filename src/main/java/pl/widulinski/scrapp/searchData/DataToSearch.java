package pl.widulinski.scrapp.searchData;


import lombok.Data;
import org.springframework.stereotype.Component;
import pl.widulinski.scrapp.enums.Categories;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;


@Component
@Data
public class DataToSearch {

    private String shop;

    @Enumerated(EnumType.STRING)
    private Set<Categories> categories;
    private String cat;


}
