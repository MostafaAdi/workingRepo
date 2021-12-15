package com.steve.app.estate;

import java.io.Serializable;
import java.util.Date;

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
public class Estate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1925801523257049307L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

	@Version
	private long version; 
	
	private String estateName = " " ; 
	
	private int price = 0; 
	
	private int sellPrice  = 0 ;
	
	private int sharesCount   = 0 ; 
	
	private String sellDate = " "; 
	
	private String clientName = "none";

	@CreatedBy
	private String addedBy; 
	
	@LastModifiedBy
	private String modifiedBy; 
	
	@CreatedDate
	private Date createdAt;
	
	@LastModifiedDate
	private Date updatedAt;
	
	private boolean selled = false ;

	public Estate(int id, String estateName, int price, int sharesCount, String clientName, boolean selled) {
		super();
		this.estateName = estateName;
		this.price = price;
		this.sharesCount = sharesCount;
		this.clientName = clientName;
		this.selled = selled;
	}

	
	
	
}
