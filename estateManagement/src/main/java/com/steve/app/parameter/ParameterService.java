package com.steve.app.parameter;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class ParameterService {

	@Autowired
	private ParameterRepository parameterRepo;
	
	
	@PostConstruct
	private void defaults() {
		if(this.parameterRepo.count() == 0 ) {
			Parameter default_shares = new Parameter();
			default_shares.setParameterName("default_shares");
			default_shares.setParameterValue(5);
			default_shares.setCreatedAt(LocalDate.now().toString());
			
			
			Parameter price_ratio = new Parameter();
			price_ratio.setParameterName("price_ratio");
			price_ratio.setParameterValue(10);
			price_ratio.setCreatedAt(LocalDate.now().toString());
			
			
			this.parameterRepo.saveAll(List.of(default_shares, price_ratio));
			
		}
	}
	
	public List<Parameter> getAllParameters(){
		return this.parameterRepo.findAll();
	}
	
	public Parameter createParameter(Parameter parameter) {
		// TODO Auto-generated method stub
		Parameter check = parameterRepo.findByParameterName(parameter.getParameterName());
		if (check != null) {
			throw new RuntimeException("parameter already exists!");

		}
		parameter.setCreatedAt(LocalDate.now().toString());
		parameter.setAddedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return parameterRepo.save(parameter);
	}

	
	public Parameter updateParameter(Parameter parameter, int parameterId) {
		// TODO Auto-generated method stub
		Parameter updated = parameterRepo.findById(parameterId).get();
		if (updated == null) {
			throw new RuntimeException("parameter doesn't exist");
		}

		parameter.setCreatedAt(LocalDate.now().toString());
		parameter.setAddedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return parameterRepo.save(parameter);
	}

	
	public void deleteParameter(int parameterId) {
		Parameter parameter = parameterRepo.findById(parameterId).get();

		parameterRepo.delete(parameter);

	}

	
	public int getParameterValue(String pName) {
		// TODO Auto-generated method stub
		return parameterRepo.findByParameterName(pName).getParameterValue();
	}
	
}
