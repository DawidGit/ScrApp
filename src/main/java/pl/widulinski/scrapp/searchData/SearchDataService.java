package pl.widulinski.scrapp.searchData;


import org.springframework.stereotype.Service;
import pl.widulinski.scrapp.enums.Categories;
import pl.widulinski.scrapp.urls.URLService;
import pl.widulinski.scrapp.webDataToScrap.DataToScrap;
import pl.widulinski.scrapp.webDataToScrap.DataToScrapRepository;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class SearchDataService {

    private final DataToScrapRepository dataToScrapRepository;

    private final URLService urlService;

    public SearchDataService(DataToScrapRepository dataToScrapRepository, URLService urlService) {
        this.dataToScrapRepository = dataToScrapRepository;
        this.urlService = urlService;
    }

    public Map<String, Set<Categories>> getShopsList() {

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

    public void searchData (PreparedDataToSrchDto preparedDataToSrchDto) {

        try {

            urlService.findElement(preparedDataToSrchDto.getShop(),preparedDataToSrchDto.getCategories());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}