package com.parking.commons.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
//@IdClass(FloorCompositeKey.class)
public class Floor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//@IdClass
	private Integer floorNumber;

	//@Id
	@ManyToOne(targetEntity = Building.class)
	@JoinColumn(name = "building_fk")
	@JsonIgnore
	private Building building;
	@OneToMany(targetEntity = Slot.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_floor", referencedColumnName = "id")
	private List<Slot> slot;

}
