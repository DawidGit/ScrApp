package pl.widulinski.scrapp.webDataToScrap;

import lombok.Data;
import pl.widulinski.scrapp.enums.Categories;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Data
public class DataToScrapDto {

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
