package io.gordonpm.bicyclemanagement.controller;

import io.gordonpm.bicyclemanagement.dto.Bicycle;
import io.gordonpm.bicyclemanagement.service.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BicycleController {

    @Autowired
    private BicycleService bicycleService;

    @GetMapping("/bicycles")
    public List<Bicycle> getAllBicycles() {
        return bicycleService.getAllBicycles();
    }

    @GetMapping("/bicycles/{id}")
    public Bicycle getBicycle(@PathVariable String id) {
        return bicycleService.getBicycle(id);
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
}
