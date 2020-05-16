package pl.widulinski.scrapp.webDataToScrap;

import pl.widulinski.scrapp.enums.Categories;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

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

    public String getShop() {
        return shop;
    }

    public Categories getCategory() {
        return category;
    }

    public String getUrlToCategory() {
        return urlToCategory;
    }

    public String getXpathOfLastPage() {
        return xpathOfLastPage;
    }

    public String getXpathToArticleElement() {
        return xpathToArticleElement;
    }

    public String getXpathToArticleName() {
        return xpathToArticleName;
    }

    public String getXpathToArticleHref() {
        return xpathToArticleHref;
    }

    public String getXpathToArticlePrice() {
        return xpathToArticlePrice;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public void setUrlToCategory(String urlToCategory) {
        this.urlToCategory = urlToCategory;
    }

    public void setXpathOfLastPage(String xpathOfLastPage) {
        this.xpathOfLastPage = xpathOfLastPage;
    }

    public void setXpathToArticleElement(String xpathToArticleElement) {
        this.xpathToArticleElement = xpathToArticleElement;
    }

    public void setXpathToArticleName(String xpathToArticleName) {
        this.xpathToArticleName = xpathToArticleName;
    }

    public void setXpathToArticleHref(String xpathToArticleHref) {
        this.xpathToArticleHref = xpathToArticleHref;
    }

    public void setXpathToArticlePrice(String xpathToArticlePrice) {
        this.xpathToArticlePrice = xpathToArticlePrice;
    }


}
