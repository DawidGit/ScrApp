package pl.widulinski.scrapp.getDataFromRepo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.widulinski.scrapp.enums.Categories;
import pl.widulinski.scrapp.webDataToScrap.DataToScrap;
import pl.widulinski.scrapp.webDataToScrap.DataToScrapRepository;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@SpringBootTest
public class TestDataFromRepository {

    @Autowired
    private DataToScrapRepository dataToScrapRepository;

    @Test
    public void insertDataToRepositoryTest() {

        //given
        DataToScrap dataToScrapTest1 = new DataToScrap();
        dataToScrapTest1.setCategory(Categories.ELECTRONICS);
        dataToScrapTest1.setShop("AllegroTest");
        dataToScrapTest1.setUrlToCategory("https://allegro.pl/kategoria/komputery?3=&bmatch=baseline-cl-dict43-ele-1-2-1127&p=");
        dataToScrapTest1.setXpathOfLastPage("//div[@class=|_1bo4a _np6in _ku8d6 _3db39_14wVQ _ymwpx _nt6qd|]/div/span");
        dataToScrapTest1.setXpathToArticleElement("//article/div/div");
        dataToScrapTest1.setXpathToArticleName("//h2/a");
        dataToScrapTest1.setXpathToArticleHref("//h2/a");
        dataToScrapTest1.setXpathToArticlePrice("//div/div/div/div/span");


        //when
        dataToScrapRepository.save(dataToScrapTest1);
        Iterable<DataToScrap>  shopFromRepoTest = dataToScrapRepository.findByShop(dataToScrapTest1.getShop());

        //then
        for (DataToScrap element:shopFromRepoTest) {

            Assertions.assertEquals(dataToScrapTest1.getUrlToCategory(), element.getUrlToCategory());
            Assertions.assertEquals(dataToScrapTest1.getXpathOfLastPage(), element.getXpathOfLastPage());
            Assertions.assertEquals(dataToScrapTest1.getXpathToArticleElement(), element.getXpathToArticleElement());
            Assertions.assertEquals(dataToScrapTest1.getXpathToArticleName(), element.getXpathToArticleName());
            Assertions.assertEquals(dataToScrapTest1.getXpathToArticleHref(), element.getXpathToArticleHref());
            Assertions.assertEquals(dataToScrapTest1.getXpathToArticlePrice(), element.getXpathToArticlePrice());
        }
    }
}
