package pl.widulinski.webcrawlertool;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.widulinski.webcrawlertool.searchData.PreparedDataToSrchDto;
import pl.widulinski.webcrawlertool.searchData.SearchDataService;
import pl.widulinski.webcrawlertool.webDataToScrap.DataToScrapRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class CommandRunner implements CommandLineRunner {


    private SearchDataService searchDataService;


    private DataToScrapRepository dataToScrapRepository;


    private PreparedDataToSrchDto preparedDataToSrchDto;

    public CommandRunner(SearchDataService searchDataService, DataToScrapRepository dataToScrapRepository, PreparedDataToSrchDto preparedDataToSrchDto) {
        this.searchDataService = searchDataService;
        this.dataToScrapRepository = dataToScrapRepository;
        this.preparedDataToSrchDto = preparedDataToSrchDto;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("##########################" + dataToScrapRepository.findAll() + "########################################################");

//        Set<DataToSearch> list = searchDataService.getDataRepository();
//
//        for (DataToSearch elem : list
//        ) {
//            System.out.println(elem.getShop());
//        }

        System.out.println("##########################" + searchDataService.getDataRepository() + "########################################################");
        System.out.println("##########################" + preparedDataToSrchDto.toString() + "########################################################");


    }
}
