package com.parking.commons.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Entity
@Data
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String username;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&*<>])(?=\\S+$).{8,}$", message = "Password must be alphanumeric")
	private String password;
	@Column(unique = true)
	private String email;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(joinColumns = @JoinColumn(name = "fk_user", referencedColumnName = "username"))
	private Set<String> role;
	@OneToMany(targetEntity = Building.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_user", referencedColumnName = "id")
	private List<Building> buildings;
	@OneToMany(targetEntity = Availability.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_user", referencedColumnName = "id")
	private List<Availability> availability;

}
