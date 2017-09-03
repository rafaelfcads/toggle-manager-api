package com.farfetch.toggle.domain.user;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.farfetch.toggle.domain.toggle.Toggle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class User {
	
	@Id
	private String id;
	
	@Indexed(unique=true)
	private String userName;
	
	private String password;
	
	private String version;
	
	private String[] roles;
	
	@DBRef
	@Field("toggles")
	private List<Toggle> toggles;
	
	public User(String userName, String password, String version, String... roles) {
		this.userName = userName;
		this.password = password;
		this.version = version;
		this.roles = roles;
	}
	
}