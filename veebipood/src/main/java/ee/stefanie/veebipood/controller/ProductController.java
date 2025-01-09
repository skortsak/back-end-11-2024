package ee.stefanie.veebipood.controller;

import ee.stefanie.veebipood.repository.ProductRepository;
import ee.stefanie.veebipood.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

//@CrossOrigin("http://localhost:4200")
@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;


//    private final ProductRepository productRepository;
//
//    public ProductController(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

//    List<Product> products = new ArrayList<>(Arrays.asList(
//            new Product("Coca", 5, true, ""),
//            new Product("Fanta", 5, true, ""),
//            new Product("Sprite", 5, true, "")
//    ));

    //localhost:8080/products
    @GetMapping("products")
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable); // SELECT * FROM products;
    }

    @GetMapping("all-products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("product/{name}")
    public Product getProduct(@PathVariable String name) {
        return productRepository.findById(name).orElseThrow(); // SELECT * FROM products;
    }

    // kui ma teen brauseris päringuid, siis ma ei saa @PostMapping, @PutMapping, @DeleteMapping
    //localhost:8080/add-product?name=Pepsi&price=2


    // localhost:8080/add-product
    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return productRepository.findAll();
    }

    @PutMapping("products")
    public Product editProduct(@RequestBody Product product) {
        if (productRepository.existsById(product.getName())) {
            return productRepository.save(product);
        }
        throw new RuntimeException("Sellise IDga toodet ei ole!");
    }

    // Variandid, mida tagastada pärast lisamist:
    // List<String> --> uuenenud list
    // String --> Lisatud toode
    // String --> Sõnum, et ilusti lisatud
    // void --> kui errorit ei ole, järelikult õnnestus

    @DeleteMapping("products/{name}")
    public List<Product> deleteProduct(@PathVariable String name) {
//        Product product = products.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
//        products.remove(product);
        productRepository.deleteById(name); //DELETE FROM products WHERE id = "Coca"
        return productRepository.findAll();
    }
    @Autowired
    Random random;

    @GetMapping("random-product")
    public Product randomProduct() {
        List<Product> products = productRepository.findAll();
//        Random random = new Random();

        System.out.println(random);
        int index = random.nextInt(products.size());
        return products.get(index);
    }

    @GetMapping("category-products")
    public List<Product> getCategoryProducts(@RequestParam Long categoryId) {
        return productRepository.findByCategory_Id(categoryId); // SELECT * FROM products WHERE category_id = ?;
    }

    @GetMapping("characteristics-products")
    public List<Product> getProductsByCharacteristics(@RequestParam List<Long> ids) {
        return productRepository.findByCharacteristic_IdIn(ids); // SELECT * FROM products WHERE category_id = ?;
    }
}
