package io.gordonpm.bicyclemanagement.repository;

import io.gordonpm.bicyclemanagement.dto.Bicycle;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BicycleRepository {

    List<Bicycle> bicycleList = new ArrayList<>();
    public List<Bicycle> getAllBicycles() {
        return bicycleList;
    }

    public Bicycle getBicycle(String id) {
        Optional<Bicycle> first = bicycleList.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            return new Bicycle();
        }
    }

    public void addBicycle(Bicycle bicycle) {
        bicycleList.add(bicycle);
    }
}
