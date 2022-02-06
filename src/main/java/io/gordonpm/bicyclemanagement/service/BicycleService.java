package io.gordonpm.bicyclemanagement.service;

import io.gordonpm.bicyclemanagement.dto.Bicycle;

import java.util.List;

public interface BicycleService {
    List<Bicycle> getAllBicycles();

    Bicycle getBicycle(String id);

    Bicycle addBicycle(Bicycle bicycle);

    Bicycle updateBicycle(String id, Bicycle bicycle);

    boolean deleteBicycle(String id);
}
