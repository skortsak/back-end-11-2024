package ee.stefanie.veebipood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SupplierService {

    @Autowired
    RestTemplate restTemplate;

    public String getStoreProducts() {
        //RestTemplate restTemplate = new RestTemplate(); // resttemplate võimaldab rakendusest välja päringuid teha

        String url = "https://fakestoreapi.com/products";
                     // kui saan brauserist kätte, on tegemist alati GET päringuga
                                                  // null --> RequestEntity. Selle sees on Headerid ja Body.
                                                  // kui juba brauser sai kätte, siis saan ka API päringuna "null" saates kätte
                                                                  // String.class --> mida kätte saan (ajutine)
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        // ResponseEntity sisse tuleb kõik: Headerid, Staatuskood, Body jnejne


        return response.getBody();
    }

    public String getEscuelaProducts() {
        //RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.escuelajs.co/api/v1/products";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return response.getBody();
    }

    public String getBooks(String search) {
        //RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.itbook.store/1.0/search/" + search;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
