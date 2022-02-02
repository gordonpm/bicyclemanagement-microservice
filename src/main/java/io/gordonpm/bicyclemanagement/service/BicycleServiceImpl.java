package io.gordonpm.bicyclemanagement.service;

import io.gordonpm.bicyclemanagement.dto.Bicycle;
import io.gordonpm.bicyclemanagement.repository.BicycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BicycleServiceImpl implements BicycleService{

    @Autowired
    private BicycleRepository bicycleRepository;

    @Override
    public List<Bicycle> getAllBicycles() {
        return bicycleRepository.getAllBicycles();
    }

    @Override
    public Bicycle getBicycle(String id) {
        return bicycleRepository.getBicycle(id);
    }

    @Override
    public void addBicycle(Bicycle bicycle) {
        bicycleRepository.addBicycle(bicycle);
    }
}
