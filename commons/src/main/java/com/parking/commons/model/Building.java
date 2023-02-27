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
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Building implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Length(min = 8, max = 20, message = "Length must be in greater than 8 and less than 20")
	private String buildingName;
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "fk_user")
	@JsonIgnore
	private User user;
	@OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "addr_id", referencedColumnName = "id")
	private Address address;

	@OneToMany(targetEntity = Floor.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "building_fk", referencedColumnName = "id")
	private List<Floor> floor;

}
