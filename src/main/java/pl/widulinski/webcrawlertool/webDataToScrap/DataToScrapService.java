package pl.widulinski.webcrawlertool.webDataToScrap;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DataToScrapService {



private DataToScrapRepository dataToScrapRepository;

    public DataToScrapService(DataToScrapRepository dataToScrapRepository) {
        this.dataToScrapRepository = dataToScrapRepository;
    }

    public void addNewShopParameters(DataToScrapDto dataToScrapDto) {

        DataToScrap dataToScrap = new DataToScrap();

        dataToScrap.setCategory(dataToScrapDto.getCategory());
        dataToScrap.setShop(dataToScrapDto.getShop());
        dataToScrap.setUrlToCategory(dataToScrapDto.getUrlToCategory());
        dataToScrap.setXpathOfLastPage(dataToScrapDto.getXpathOfLastPage());
        dataToScrap.setXpathToArticleElement(dataToScrapDto.getXpathToArticleElement());
        dataToScrap.setXpathToArticleName(dataToScrapDto.getXpathToArticleName());
        dataToScrap.setXpathToArticleHref(dataToScrapDto.getXpathToArticleHref());
        dataToScrap.setXpathToArticlePrice(dataToScrapDto.getXpathToArticlePrice());

        dataToScrapRepository.save(dataToScrap);
    }


}
