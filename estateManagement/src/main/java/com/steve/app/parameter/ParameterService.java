package com.steve.app.parameter;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class ParameterService {

	@Autowired
	private ParameterRepository parameterRepo;
	
	
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
