package com.parking.commons.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Availability {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private LocalDate date = LocalDate.now();
	private String status = "AVAILABLE";

	@ManyToOne(targetEntity = Slot.class)
	@JoinColumn(name = "slot_fk")

	@JsonIgnore
	private Slot slot;
	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "fk_user")
	
	@JsonIgnore
	private User user;
	

}
