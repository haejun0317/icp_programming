package com.sds.icagile.cafe.beverage;

import com.sds.icagile.cafe.beverage.model.Beverage;
import com.sds.icagile.cafe.beverage.model.BeverageSize;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BeverageRepositoryTest {
    @Autowired
    private BeverageRepository beverageRepository;

    private Beverage americano;
    private Beverage latte;
    private Beverage coldBrew;

    @BeforeEach
    public void setUp() {
       // createBeverages();
    }

    @Test
    public void whenFindByCostGreaterThan_thenReturnBeveragesGT100() {
        //TODO - implement
    }

    @Test
    public void whenSaveBeverages_thenReturnCreatedBeverages() {
        //TODO - implement
    }

    private List<Beverage> createBeverages() {
        //TODO - implement
        return null;
    }
}