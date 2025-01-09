package ee.stefanie.veebipood.service;

import ee.stefanie.veebipood.models.NordPoolResponse;
import ee.stefanie.veebipood.models.TimestampPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class NordPoolService {

    @Autowired
    RestTemplate restTemplate;

    public List<TimestampPrice> getElectricityPrices(String country) {
        //RestTemplate restTemplate = new RestTemplate();
        String url = "https://dashboard.elering.ee/api/nps/price";
        ResponseEntity<NordPoolResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, NordPoolResponse.class);
        return switch (country) {
            case "ee" -> response.getBody().data.ee;
            case "fi" -> response.getBody().data.fi;
            case "lv" -> response.getBody().data.lv;
            case "lt" -> response.getBody().data.lt;
            case null, default -> new ArrayList<>();
        };
    }
}
