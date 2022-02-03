package io.gordonpm.bicyclemanagement.controller;

import io.gordonpm.bicyclemanagement.dto.Bicycle;
import io.gordonpm.bicyclemanagement.service.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;

@RestController
public class BicycleController {

    @Autowired
    private BicycleService bicycleService;

    @PostConstruct
    public void initBicycles() {
        Bicycle trekBicycle = new Bicycle("100", "Trek", "Emonda SLR6", 6699.99);
        Bicycle bianchiBicycle = new Bicycle("200", "Bianchi", "Infinito XE 11SP", 3675.00);
        Bicycle canyonBicycle = new Bicycle("300", "Canyon", "Endurace CF SL 8 Disc", 3499.00);

        bicycleService.addBicycle(trekBicycle);
        bicycleService.addBicycle(bianchiBicycle);
        bicycleService.addBicycle(canyonBicycle);
    }

    @GetMapping("/bicycles")
    public List<Bicycle> getAllBicycles() {
        return bicycleService.getAllBicycles();
    }

    @GetMapping("/bicycles/{id}")
    public ResponseEntity<?> getBicycle(@PathVariable String id) {
        Bicycle bicycle = bicycleService.getBicycle(id);
        if (bicycle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bicycle, HttpStatus.OK);
    }

    @PostMapping("/bicycles")
    public ResponseEntity<Object> addBicycle(@RequestBody Bicycle bicycle) {
        bicycleService.addBicycle(bicycle);
        URI path = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bicycle.getId())
                .toUri();
        return ResponseEntity.created(path).build();
    }

    @PutMapping("/bicycles/{id}")
    public ResponseEntity<?> updateBicycle(@PathVariable String id, @RequestBody Bicycle bicycle) {
        boolean isFound = bicycleService.updateBicycle(id, bicycle);
        if (!isFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bicycle, HttpStatus.OK);
    }

    @DeleteMapping("/bicycles/{id}")
    public ResponseEntity<?> deleteBicycle(@PathVariable String id) {
        boolean isFound = bicycleService.deleteBicycle(id);
        if (!isFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
