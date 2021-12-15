package com.steve.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.steve.app.estate.Estate;
import com.steve.app.estate.EstateRepository;
import com.steve.app.estate.EstateService;
import com.steve.app.exceptions.NoEstateFoundException;

@SpringBootTest
public class EstateServiceTests {


	@Autowired
	private EstateService estateService;
	
	@MockBean
    private EstateRepository estateRepo;
	
	
	private Estate existingEstate;
	
	
	@TestConfiguration
    static class EstateServiceContextConfig {

        @Bean
        public EstateService getEstateService () {
            return new EstateService();
        }
    }
	
	@BeforeEach
    public void setUp() {
									//estateName, price. sharesCount, clientName, isSold
        existingEstate = new Estate(2, "estate", 100000, 2400, "gov", false);
    }
	
	@Test
    void deleteNotExistingEstateTest () {
        int id = 100;
        Mockito.when(estateRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoEstateFoundException.class, () ->{
            estateService.deleteEstate(id);
        });
    }

	@Test
    void deleteExistingEstateTest() {

        int id = 2;
        Mockito.when(estateRepo.findById(id)).thenReturn(Optional.of(existingEstate));
        assertDoesNotThrow(()-> estateService.deleteEstate(id));
    }
	
	@Test
    void findByNameExistingEstateTest() {

        Mockito.when(estateRepo.findByEstateName(existingEstate.getEstateName())).thenReturn(existingEstate);
        Estate found = estateService.getByName(existingEstate.getEstateName());
        
        assertThat(found.getEstateName())
                .isEqualTo(existingEstate.getEstateName());

    }

    @Test
    void findByIdNotExistingEstate () {

        int id = 100;
        Mockito.when(estateRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoEstateFoundException.class, () -> estateService.getById(id)
                );
    }
}
