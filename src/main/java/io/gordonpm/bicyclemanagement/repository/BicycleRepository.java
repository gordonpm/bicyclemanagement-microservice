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
            return null;
        }
    }

    public Bicycle addBicycle(Bicycle bicycle) {
        if (bicycleList.add(bicycle))
            return bicycle;
        else return null;
    }

    public Bicycle updateBicycle(String id, Bicycle bicycle) {
        for (Bicycle b : bicycleList) {
            if (b.getId().equals(id)) {
                b.setVendor(bicycle.getVendor());
                b.setName(bicycle.getName());
                b.setPrice(bicycle.getPrice());
                return b;
            }
        }
        return null;
    }

    public boolean deleteBicycle(String id) {
        Optional<Bicycle> first = bicycleList.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        if (first.isPresent()) {
            Bicycle bicycle = first.get();
            bicycleList.remove(bicycle);
            return true;
        } else {
            return false;
        }
    }
}
