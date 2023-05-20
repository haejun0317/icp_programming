package com.sds.icagile.cafe.beverage;

import com.sds.icagile.cafe.beverage.model.Beverage;
import com.sds.icagile.cafe.beverage.model.BeverageSize;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BeverageRepositoryTest {
    @Autowired
    private BeverageRepository beverageRepository;

    @Test
    public void whenFindByCostGreaterThan_thenReturnBeveragesGT100() {
        List<Beverage> onlyColdBrew = beverageRepository.findByCostGreaterThan(100);

        assertEquals(onlyColdBrew.size(), 3);
        assertEquals(onlyColdBrew.get(0).getName(), "americano");
    }

    @Test
    public void whenSaveBeverages_thenReturnCreatedBeverages() {
        List<Beverage> newBeverages = beverageRepository.findAll();

        assertThat(newBeverages.size()).isEqualTo(3);
        assertThat(newBeverages.get(0).getName()).isEqualTo("americano");
        assertThat(newBeverages.get(1).getName()).isEqualTo("coldbrew");
        assertThat(newBeverages.get(2).getName()).isEqualTo("latte");
    }
}