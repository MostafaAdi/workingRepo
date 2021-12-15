package com.steve.app.parameter;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer>{
	
	@Cacheable("addresses")
	Parameter findByParameterName(String pName);
}
