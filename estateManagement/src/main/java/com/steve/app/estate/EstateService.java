package com.steve.app.estate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steve.app.exceptions.NoEstateFoundException;
import com.steve.app.parameter.ParameterService;

@Service
public class EstateService {

	@Autowired
	private EstateRepository estateRepo;
	
	@Autowired
	private ParameterService parameterService;
	
	
	public Estate getById(int estateId) {
		Optional<Estate> optional = estateRepo.findById(estateId);
		if (optional.isEmpty()) {
			 throw new NoEstateFoundException("No estate with this ID");
		}
      
        return optional.get();
	}
	
	public Estate getByName(String estateName) {
		Estate estate = this.estateRepo.findByEstateName(estateName);
		if(estate != null) {
			return estate;
		}else {
			return null ; 
		}
	}
	
	
	
	@Transactional
	public Estate addEstate(Estate estate) {
		Estate check = estateRepo.findByEstateName(estate.getEstateName());
		if (check != null) {
			throw new RuntimeException("estate already exists!");

		}
		
		return estateRepo.save(estate);
	}
	
	
	public List<Estate> getAll(){
		return this.estateRepo.findAll(); 
	}
	
	public List<Estate> getNonSelled(){
		List<Estate> result = new ArrayList<>();
		for(Estate estate : this.estateRepo.findAll()) {
			if(!estate.isSelled()) {
				estate.setSellPrice(estate.getPrice() * this.parameterService.getParameterValue("price_ratio"));
				result.add(estate);
			}
		}
		return result ; 
	}
	
	public List<Estate> getSelled(){
		List<Estate> result = new ArrayList<>();
		for(Estate estate : this.estateRepo.findAll()) {
			if(estate.isSelled()) {
				result.add(estate);
			}
		}
		return result ; 
	}

	
	@Transactional
	public int updateEstate(Estate estate) {
		Optional<Estate> optional = this.estateRepo.findById(estate.getId());
		if(optional.isPresent()) {
//			if(estate.getVersion() != optional.get().getVersion()) {
////				throw new ConflictException();
//			}
			Estate estateFromDb = optional.get();
		 
		
			estateFromDb.setEstateName(estate.getEstateName());
			estateFromDb.setClientName(estate.getClientName());
			estateFromDb.setSellDate(estate.getSellDate());
			estateFromDb.setPrice(estate.getPrice());
			estateFromDb.setSharesCount(estate.getSharesCount());
			estateFromDb.setSellPrice(estate.getSellPrice());
			
			this.estateRepo.save(estateFromDb);
			return 0 ;
		}else {
			throw new ServiceException("cannot update Estate!") ; 
		}
	}
	
	@Transactional
	public void deleteEstate(int estateId) throws NoEstateFoundException{
		Optional<Estate> estate = estateRepo.findById(estateId);
        if(estate.isEmpty()){
            throw new NoEstateFoundException("No such Employee found");
        }
        estateRepo.deleteById(estateId);
	}
	
	
	public List<Estate> getNotSelledEstates(){
		List<Estate> estatesList = new ArrayList<Estate>() ; 
		for(Estate estate : this.estateRepo.findAll()) {
			if(estate.getClientName().equalsIgnoreCase("none")) {
				estatesList.add(estate);
			}
		}
		return estatesList ; 
	}
	
	@Transactional
	public int sellEstate(Estate estate) {
		
		Optional<Estate> optional = this.estateRepo.findById(estate.getId());
		if(optional.isPresent()) {
//			if(estate.getVersion() != optional.get().getVersion()) {
////				throw new ConflictException();
//			}
			Estate dbEstate = optional.get();
		
			dbEstate.setClientName(estate.getClientName());
			dbEstate.setSellPrice(this.parameterService.getParameterValue("price_ratio") * estate.getPrice());
			dbEstate.setSellDate(LocalDate.now().toString());
			dbEstate.setSelled(true);
			dbEstate = this.estateRepo.save(dbEstate);
//			msgSender.sendOrderCar(dbEstate);
			return 0 ;
		}else {
			throw new ServiceException("cannot sell estate") ; 
		}
	}
	
	
	
	

}
