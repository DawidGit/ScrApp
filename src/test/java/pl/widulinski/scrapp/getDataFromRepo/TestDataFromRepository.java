package pl.widulinski.scrapp.getDataFromRepo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.widulinski.scrapp.enums.Categories;
import pl.widulinski.scrapp.webDataToScrap.DataToScrap;
import pl.widulinski.scrapp.webDataToScrap.DataToScrapDto;
import pl.widulinski.scrapp.webDataToScrap.DataToScrapRepository;
import pl.widulinski.scrapp.webDataToScrap.DataToScrapService;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
public class TestDataFromRepository {

    @Autowired
    private DataToScrapRepository dataToScrapRepository;

    @Autowired
    private DataToScrapService dataToScrapService;

    @Test
    public void insertDataToRepositoryTest() {

        //given
        int count = 0;
        DataToScrapDto dataToScrapDtoTest = new DataToScrapDto();
        dataToScrapDtoTest.setCategory(Categories.ELECTRONICS);
        dataToScrapDtoTest.setShop("AllegroTest");
        dataToScrapDtoTest.setUrlToCategory("https://allegro.pl/kategoria/komputery?3=&bmatch=baseline-cl-dict43-ele-1-2-1127&p=");
        dataToScrapDtoTest.setXpathOfLastPage("//div[@class=|_1bo4a _np6in _ku8d6 _3db39_14wVQ _ymwpx _nt6qd|]/div/span");
        dataToScrapDtoTest.setXpathToArticleElement("//article/div/div");
        dataToScrapDtoTest.setXpathToArticleName("//h2/a");
        dataToScrapDtoTest.setXpathToArticleHref("//h2/a");
        dataToScrapDtoTest.setXpathToArticlePrice("//div/div/div/div/span");


        //when
        dataToScrapService.addNewShopParameters(dataToScrapDtoTest);
        Iterable<DataToScrap>  shopFromRepoTest = dataToScrapRepository.findByShop(dataToScrapDtoTest.getShop());

        //then
        for (DataToScrap element:shopFromRepoTest) {

            Assertions.assertEquals(dataToScrapDtoTest.getUrlToCategory(), element.getUrlToCategory());
            Assertions.assertEquals(dataToScrapDtoTest.getXpathOfLastPage(), element.getXpathOfLastPage());
            Assertions.assertEquals(dataToScrapDtoTest.getXpathToArticleElement(), element.getXpathToArticleElement());
            Assertions.assertEquals(dataToScrapDtoTest.getXpathToArticleName(), element.getXpathToArticleName());
            Assertions.assertEquals(dataToScrapDtoTest.getXpathToArticleHref(), element.getXpathToArticleHref());
            Assertions.assertEquals(dataToScrapDtoTest.getXpathToArticlePrice(), element.getXpathToArticlePrice());

          count++;
        }

        Assertions.assertEquals(count,1);
    }
}
