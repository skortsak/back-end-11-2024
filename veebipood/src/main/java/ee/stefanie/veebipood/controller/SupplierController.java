package ee.stefanie.veebipood.controller;

import ee.stefanie.veebipood.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    // ainult @...Mapping sisaldavad funktsioonid on siin
    // muud funktsioonid lähevad kas service (@Service) või util (@Component) kausta
    // Service --> selgelt selle kontrolleriga seotud funktsioon
    // Util --> üldiselt taaskasutatavad funktsioonid (arvutused/isikukoodi kontroll)

    @GetMapping("store-products")
    public String getStoreProducts() {
        return supplierService.getStoreProducts();
    }

    @GetMapping("escuela-products")
    public String getEscuelaProducts() {
        return supplierService.getEscuelaProducts();
    }

    @GetMapping("books")
    public String getBooks(@RequestParam String search) {
        return supplierService.getBooks(search);
    }
}
