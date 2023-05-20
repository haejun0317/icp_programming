package com.sds.icagile.cafe.beverage;

import com.sds.icagile.cafe.beverage.model.Beverage;
import com.sds.icagile.cafe.beverage.model.BeverageSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeverageControllerTest {
    @InjectMocks
    private BeverageController beverageController;

    @Mock
    private BeverageService mockBeverageService;

    @Test
    public void getBeverages를_호출하면_음료목록을_리턴한다() {
        //given
        Beverage americano = new Beverage(1, "americano", 10, BeverageSize.SMALL);
        Beverage latte = new Beverage(2, "latte", 20, BeverageSize.REGULAR);

        when(mockBeverageService.getBeverages()).thenReturn(Arrays.asList(americano, latte));

        //when
        List<Beverage> result = beverageController.getBeverages();

        //then
        assertThat(result, contains(americano, latte));
    }

    @Test
    public void 음료목록으로_createBeverages를_호출하면_생성한_음료목록을_리턴한다() {
        Beverage americano = new Beverage(1, "americano", 10, BeverageSize.SMALL);
        Beverage latte = new Beverage(2, "latte", 20, BeverageSize.REGULAR);
        List<Beverage> beverages = Arrays.asList(americano, latte);

        when(mockBeverageService.createBeverages(any())).thenReturn(beverages);

        List<Beverage> result = beverageController.createBeverages(beverages);

        assertThat(result, contains(americano, latte));
    }
}