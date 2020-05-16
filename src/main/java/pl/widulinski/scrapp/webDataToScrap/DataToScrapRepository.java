package pl.widulinski.scrapp.webDataToScrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.widulinski.scrapp.enums.Categories;

import java.util.List;


@Repository
public interface DataToScrapRepository extends JpaRepository<DataToScrap, Long> {

     DataToScrap findByShopAndCategory(String shop, Categories category);

     List<DataToScrap> findByShop(String shop);


}
