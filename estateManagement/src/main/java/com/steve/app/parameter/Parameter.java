package com.steve.app.parameter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parameter implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1671912937878919304L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "parameter_name", nullable = false, length = 45)
	private String parameterName;
	
	@Column(name = "parameter_value", nullable = false)
	private int ParameterValue;
	
	@Column(nullable = true, length = 50)
	@CreatedBy
	private String addedBy;
	
	@Column(nullable = true, length = 50)
	@LastModifiedBy
	private String modifiedBy;
	
	@Column(nullable = true, length = 40)
	@CreatedDate
	private Date createdAt;
	
	@Column(nullable = true, length = 40)
	@LastModifiedDate
	private Date updatedAt;
	
	@Version
	private long version;
	
	public Parameter (String parameterName, int parameterValue) {
		this.parameterName = parameterName;
		this.ParameterValue = parameterValue;
	}
}
