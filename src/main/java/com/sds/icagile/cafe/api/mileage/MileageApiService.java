package com.sds.icagile.cafe.api.mileage;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="mileage", url="http://localhost:9000/api/v1")
public interface MileageApiService {

    @GetMapping("/mileages/{customerId}")
    int getMileages(@PathVariable("customerId") int customerId);

    @PutMapping("/mileages/{customerId}")
    double minusMileages(@PathVariable("customerId") int customerId, Mileage mileage);

    @PostMapping("/mileages/{customerId}")
    double saveMileages(@PathVariable("customerId") int customerId, Mileage mileage);
}
