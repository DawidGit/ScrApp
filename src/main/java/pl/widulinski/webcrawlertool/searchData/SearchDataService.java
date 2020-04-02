package pl.widulinski.webcrawlertool.searchData;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import pl.widulinski.webcrawlertool.enums.Categories;
import pl.widulinski.webcrawlertool.urls.URLService;
import pl.widulinski.webcrawlertool.webDataToScrap.DataToScrap;
import pl.widulinski.webcrawlertool.webDataToScrap.DataToScrapRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class SearchDataService {



    private DataToScrapRepository dataToScrapRepository;


    private URLService urlService;

    public SearchDataService(DataToScrapRepository dataToScrapRepository, URLService urlService) {
        this.dataToScrapRepository = dataToScrapRepository;
        this.urlService = urlService;
    }


    public Map<String, Set<Categories>> getDataRepository() {

        Set<DataToSearch> shopNames = new HashSet<>();
        Map<String,Set<Categories>> shopAndCategories = new HashMap<>();

        for (DataToScrap shopName : dataToScrapRepository.findAll()
        ) {
            DataToSearch newData = new DataToSearch();
            newData.setShop(shopName.getShop());
            shopNames.add(newData);
        }

        for (DataToSearch eachName : shopNames) {

            Set<Categories> newCat = new HashSet<>();

            for (DataToScrap eachCategory : dataToScrapRepository.findByShop(eachName.getShop())
            ) {
                newCat.add(eachCategory.getCategory());

            }
            eachName.setCategories(newCat);
            shopAndCategories.put(eachName.getShop(),newCat);

        }
        return shopAndCategories;

    }

    public void searchData (PreparedDataToSrchDto preparedDataToSrchDto) throws InterruptedException, InvalidFormatException {

        try {

            urlService.findElement(preparedDataToSrchDto.getShop(),preparedDataToSrchDto.getCategories());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}