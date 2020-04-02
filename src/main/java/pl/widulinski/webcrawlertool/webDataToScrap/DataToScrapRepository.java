package pl.widulinski.webcrawlertool.webDataToScrap;

import io.micrometer.core.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.widulinski.webcrawlertool.enums.Categories;

import java.util.List;
import java.util.Set;


@Repository
public interface DataToScrapRepository extends JpaRepository<DataToScrap, Long> {

     DataToScrap findByShopAndCategory(String shop, Categories category);

     List<DataToScrap> findByShop(String shop);


}
