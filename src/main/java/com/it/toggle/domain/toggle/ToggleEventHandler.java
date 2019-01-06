package com.it.toggle.domain.toggle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.it.toggle.config.kafka.Greeting;

@RepositoryEventHandler(Toggle.class)
@Component
public class ToggleEventHandler {
	
    @Autowired
    private KafkaTemplate<String, Greeting> greetingKafkaTemplate;
    
	@HandleAfterCreate
	public void handleToggleSave(Toggle toggle) throws InterruptedException {
		
		Greeting greeting = new Greeting();
		greeting.setMsg("Toggle: [" + toggle.getName() + "] updated to Value: ["  + toggle.isValue() + "]");
		greeting.setName(toggle.getName());
		
//		this.greetingKafkaTemplate.send(toggle.getName(), greeting);
	}
	
}
