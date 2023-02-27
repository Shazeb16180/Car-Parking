package com.parking.commons.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "Area Cannot be Empty")
	private String area;
	@NotBlank(message = "Town Cannot be Empty")
	private String town;
	@NotBlank(message = "State Cannot be Empty")
	private String state;
	@NotBlank(message = "landMark Cannot be Empty")
	private String landmark;
	@NotBlank(message = "pinCode Cannot be Empty")
	private String pincode;

	@JsonIgnore
	@OneToOne(targetEntity = Building.class, mappedBy = "address")
	// @JoinColumn(name="building_fk",referencedColumnName = "id")
	private Building building;
}
