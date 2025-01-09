package ee.stefanie.veebipood.repository;

import ee.stefanie.veebipood.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    // Saab tagastada ainult Orderitega seotud väärtusi, näiteks:
    // List<Order>
    // Order
    // boolean - exists
    // int - count
    List<Order> findByPerson_Id(Long id); // SELECT * FROM person WHERE person_id = 5

    List<Order> findByTotalSumGreaterThan(double totalSum); // SELECT * FROM person WHERE totalSum > 1000

    // KONTROLLIDA: @Query("SELECT * FROM person WHERE totalSum > ?")
    //List<Order> findByTotalSumLargerThan(double totalSum);


}
