package ee.stefanie.veebipood.repository;

import ee.stefanie.veebipood.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository; // --> kõik asjad sees (kõige rohkem funktsioone tuleb interface-i)

import java.util.Collection;
import java.util.List;
//import org.springframework.data.repository.CrudRepository; --> ainult tähtsad asjad
//import org.springframework.data.repository.PagingAndSortingRepository; --> lisaks sorteerimine ja lehekülgede näitamine

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByCategory_Id(Long id);

    List<Product> findByNutrients_FatLessThan(int fat);

    List<Product> findByCharacteristic_IdIn(Collection<Long> ids);


}
