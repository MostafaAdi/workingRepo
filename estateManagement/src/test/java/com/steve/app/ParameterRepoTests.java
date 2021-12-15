package com.steve.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.steve.app.parameter.Parameter;
import com.steve.app.parameter.ParameterRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ParameterRepoTests {

	@Autowired
	private ParameterRepository parameterRepo;

	@Test
	public void showParametersTest() {
		List<Parameter> parameters = parameterRepo.findAll();
		assertThat(parameters).size().isGreaterThan(0);
	}
	
	@Test
	public void findParameterByNameExistsTest() {
		String parameterName = "default_shares";
		Parameter parameter = parameterRepo.findByParameterName(parameterName);
		assertThat(parameter).isNotNull();
	}
	
	@Test
	public void findParameterByNameNotExistsTest() {
		String parameterName = "random name";
		Parameter parameter = parameterRepo.findByParameterName(parameterName);
		assertNull(parameter);
	}
	
	@Test
	public void createNewParameterTes() {
		Parameter parameter = new Parameter("testParameter", 10);
		Parameter newParameter = parameterRepo.save(parameter);
		assertNotNull(newParameter);
	}
	
	@Test
	public void updateExistingParameterTest() {
		int value = 2500;
		Parameter existingParameter = parameterRepo.findByParameterName("default_shares");
		existingParameter.setParameterValue(value);
		Parameter updatedParameter = parameterRepo.save(existingParameter);	
		assertThat(updatedParameter.getParameterValue()).isEqualTo(value);
	}
	
	@Test
	public void deleteExistingParameterTest() {
		int parameterId = 1;
		
		
		boolean isPresentBeforeDelete = parameterRepo.findById(parameterId).isPresent();
		parameterRepo.deleteById(parameterId);	
		
		boolean notPresentAfterDelete = parameterRepo.findById(parameterId).isPresent();
		
		assertTrue(isPresentBeforeDelete);
		assertFalse(notPresentAfterDelete);
	}
	
}
