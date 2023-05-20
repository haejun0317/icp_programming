package com.sds.icagile.cafe.beverage;

import com.sds.icagile.cafe.beverage.model.Beverage;
import com.sds.icagile.cafe.beverage.model.BeverageSize;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BeverageServiceTest {
    @InjectMocks
    private BeverageService beverageService;

    @Mock
    private BeverageRepository mockBeverageRepository;

    @Test
    public void whenGetBeverages_thenReturnAllBeverages() {
        //TODO - implement
    }
}