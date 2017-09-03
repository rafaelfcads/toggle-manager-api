package com.farfetch.toggle.domain.toggle;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "toggles")
public class Toggle {
	
	@Id
	private String id;
	
	private String name;
	
	private boolean value;
	
	public Toggle(String name, boolean value) {
		this.name = name;
		this.value = value;
	}
	
}
