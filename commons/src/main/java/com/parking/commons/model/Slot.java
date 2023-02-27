package com.parking.commons.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Entity
@Data
public class Slot {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "slot no cannot be Empty")
	private String slotId;
	@ManyToOne(targetEntity = Floor.class)
	@JoinColumn(name = "fk_floor")
	
	@JsonIgnore
	private Floor floor;
	
	@OneToMany(targetEntity = Availability.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "slot_fk",referencedColumnName = "id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Availability> availabilty;

}
