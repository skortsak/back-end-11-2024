package ee.stefanie.veebipood.controller;

import ee.stefanie.veebipood.entity.CartRow;
import ee.stefanie.veebipood.entity.Order;
import ee.stefanie.veebipood.entity.Person;
import ee.stefanie.veebipood.repository.OrderRepository;
import ee.stefanie.veebipood.repository.PersonRepository;
import ee.stefanie.veebipood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//@CrossOrigin("http://localhost:4200")
@RestController
public class OrderController {

    // OrderRepository orderRepository = new OrderRepository();
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;
    @Autowired
    private PersonRepository personRepository;

//    @Autowired
//    ProductRepository productRepository;

    // seob ühe faili teisega: Dependency Injection
    // 1 mälukoht üle terve  projekti -> ei pea tegema kunagi = new ProductRepository();

    @PostMapping("orders")
    public String addOrder(@RequestBody List<CartRow> cartRows) {

        Order order = new Order();

        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personRepository.findById(id).orElseThrow();
        order.setPerson(person);

        order.setCartRows(cartRows);

        order.setCreation(new Date());
        // ctrl + alt + m -> tekitab funktsiooni või meetodeid
        double sum = orderService.calculateSum(order);
        order.setTotalSum(sum);
        Order dbOrder = orderRepository.save(order);
        return orderService.getPaymentLink(dbOrder);
    }// payment_reference omab enda URL parameetri infot, kas makse õnnestus või ebaõnnestus
    //SETTLED: ?order_reference=36765904&payment_reference=fcd0d7e73ae32dd65447db207109395850b0811b83bbcdf6e2d76eae0e369aaa
    //SETTLED: ?order_reference=36765905&payment_reference=36e42ce049f8501cadc003b4fd782d5a42a16d344ddc2a3d5bd258e594aefb36
    //FAILED: ?order_reference=36765906&payment_reference=9e9774f63cd31c3b812c7005478ea7b151ffe81bd76d993227d8feedca9221be

    @GetMapping("check-payment")
    public boolean checkPaymentStatus(@RequestParam String paymentRef) {
        return orderService.checkPaymentStatus(paymentRef);
    }

    // Controlleris ei tohiks ühtegi funktsiooni olla, millel pole
    // @Mapping

    // Controlleri ülesanne võiks olla ainult päringute vastu võtmine ja
    // front-endile andmete tagastamine

    // localhost:8080/person-orders/5
    @GetMapping("person-orders/{id}")
    public List<Order> getPersonOrders(@PathVariable Long id) {
        return orderRepository.findByPerson_Id(id);
    }

    // localhost:8080/orders-larger-than?minSum=40
    @GetMapping("orders-larger-than")
    public List<Order> ordersLargerThan(@RequestParam double minSum) {
        return orderRepository.findByTotalSumGreaterThan(minSum);
    }

    @GetMapping("order-proteins/{id}")
    public int proteinsInOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();

        return orderService.calculateProteins(order);
    }
}
// Kui paneme koodi käima "run" -->
// Luuakse classid + constructorid + autowired
// Siis jääb seisma

// CRON --> võib ka käivitada. puhastus. e-maili meeldetuletused.
// Controller: Eesmärgiga päringuid vastu võtta
// front-endiga suhtlemine

//Entity: andmebaasimudel
// Eesmärk on andmemudel tekitada, mis on ka andmebaasis tabelina

// Repository: andmebaasiga suhtlemiseks -> andmete võtmiseks, lisamiseks, muutmiseks jne

// Service:
