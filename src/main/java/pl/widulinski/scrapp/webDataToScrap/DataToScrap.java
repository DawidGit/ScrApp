package pl.widulinski.scrapp.webDataToScrap;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import pl.widulinski.scrapp.enums.Categories;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Data
@Table(name = "data_to_scrap")
public class DataToScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private String shop;

    @Enumerated(EnumType.STRING)
    private Categories category;

    @NotEmpty
    private String urlToCategory;

    @NotEmpty
    private String xpathOfLastPage;

    @NotEmpty
    private String xpathToArticleElement;

    @NotEmpty
    private String xpathToArticleName;

    @NotEmpty
    private String xpathToArticleHref;

    @NotEmpty
    private String xpathToArticlePrice;


}
