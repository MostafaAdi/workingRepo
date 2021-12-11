package com.steve.app.parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ParameterController {

	@Autowired 
	private ParameterService parameterService;
	
	@RequestMapping(method = RequestMethod.GET , value = "/params/view")
	public ModelAndView getParams() {
		ModelAndView mav = new ModelAndView("params/view");
	    mav.addObject("params", this.parameterService.getAllParameters());
		return mav ;
	}
}
