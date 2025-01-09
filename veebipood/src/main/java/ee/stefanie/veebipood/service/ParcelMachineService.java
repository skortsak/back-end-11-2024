package ee.stefanie.veebipood.service;

import ee.stefanie.veebipood.models.OmnivaPM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParcelMachineService {

    @Autowired
    RestTemplate restTemplate;

    public List<OmnivaPM> getParcelMachines(String country) {
        //RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.omniva.ee/locations.json";
        ResponseEntity<OmnivaPM[]> response = restTemplate.exchange(url, HttpMethod.GET, null, OmnivaPM[].class);
        List<OmnivaPM> omnivaPMs = new ArrayList<>();
        for (OmnivaPM pm: response.getBody()) {
            if (pm.getA0_NAME().equals(country.toUpperCase())) {
                omnivaPMs.add(pm);
            }
        }
        return omnivaPMs;
    }
}
