package ee.stefanie.veebipood.controller;

import ee.stefanie.veebipood.models.OmnivaPM;
import ee.stefanie.veebipood.service.ParcelMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParcelMachineController {

    @Autowired
    ParcelMachineService parcelMachineService;

    @GetMapping("parcel-machines")
    public List<OmnivaPM> getParcelMachines(@RequestParam String country) {
        return parcelMachineService.getParcelMachines(country);
    }
}
