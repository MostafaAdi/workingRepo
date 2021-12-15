package com.steve.app.parameter;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;




@Service
@CacheConfig(cacheNames = "addresses")
public class ParameterService {

	@Autowired
	private ParameterRepository parameterRepo;
	
	
	@PostConstruct
	private void defaults() {
		if(this.parameterRepo.count() == 0 ) {
			Parameter default_shares = new Parameter();
			default_shares.setParameterName("default_shares");
			default_shares.setParameterValue(5);
			
			
			
			Parameter price_ratio = new Parameter();
			price_ratio.setParameterName("price_ratio");
			price_ratio.setParameterValue(10);
			
			
			
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
		
		return parameterRepo.save(parameter);
	}

	@CacheEvict(value = "addresses", allEntries=true) 
	public Parameter updateParameter(Parameter parameter) {
		// TODO Auto-generated method stub
		Optional<Parameter> updated = parameterRepo.findById(parameter.getId());
		
		if (updated.isEmpty()) {
			throw new RuntimeException("parameter doesn't exist");
		}
		
		if (parameter.getVersion() != updated.get().getVersion()) {
//			throw new ConflictException();
		}
		
		Parameter dbPrameter = updated.get();

		
		dbPrameter.setParameterName(parameter.getParameterName());
		dbPrameter.setParameterValue(parameter.getParameterValue());
		return parameterRepo.save(dbPrameter);
	}

	
	public void deleteParameter(int parameterId) {
		Parameter parameter = parameterRepo.findById(parameterId).get();

		parameterRepo.delete(parameter);

	}

	@CacheEvict(value = "addresses", allEntries=true) 
	public int getParameterValue(String pName) {
		// TODO Auto-generated method stub
		return parameterRepo.findByParameterName(pName).getParameterValue();
	}

	public Parameter getById(int parameterId) {
		Optional<Parameter> parameter = this.parameterRepo.findById(parameterId);
		if(parameter.isPresent()) {
			return parameter.get();
		}else {
			return null ; 
		}
	}
	
}
