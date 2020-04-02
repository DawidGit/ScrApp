package pl.widulinski.webcrawlertool.FoundWebElements;


import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
@Data
public class FoundWebElement {

    private String name;
    private String price;
    private String link;

}
