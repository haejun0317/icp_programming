package com.sds.icagile.cafe.beverage;

import com.sds.icagile.cafe.beverage.model.Beverage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeverageService {
    public final BeverageRepository beverageRepository;

    public BeverageService(BeverageRepository beverageRepository) {
        this.beverageRepository = beverageRepository;
    }

    public List<Beverage> getBeverages() {
        return beverageRepository.findAll();
    }

    public List<Beverage> createBeverages(List<Beverage> beverages) {
        return beverageRepository.saveAll(beverages);
    }
}
