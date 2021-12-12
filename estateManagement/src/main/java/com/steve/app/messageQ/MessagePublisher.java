package com.steve.app.messageQ;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MessagePublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@GetMapping("/publish")
	public ModelAndView publish() {
		ModelAndView mav = new ModelAndView("publish/view");
		CustomMessage message = new CustomMessage();
		mav.addObject("message", message);
		return mav;
	}
	
	@PostMapping("/publish/send")
	public String publishMessage(@ModelAttribute CustomMessage message) {
		
		message.setMessageId(UUID.randomUUID().toString());
		message.setMessageId(LocalDate.now().toString());
		rabbitTemplate.convertAndSend(MQConfig.EXCHANGE,
				MQConfig.ROUTING_KEY, message);
		return "message published";
	}
}
