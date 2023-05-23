package com.greenbookshop.common.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "provinces")
@NoArgsConstructor
@Getter
@Setter
public class Province extends IdBasedEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 45)
	private String name;

	@Column(nullable = false, length = 5)
	private String code;

//	@JsonBackReference
	@JsonIgnore
	@OneToMany(mappedBy = "province")
	private Set<District> districts;

	public Province(String name, String code) {
		this.name = name;
		this.code = code;
	}
	
	public Province(Integer id, String name, String code) {
		this.id = id;
		this.name = name;
		this.code = code;
	}
	
	public Province(String name) {
		this.name = name;
	}
	
	public Province(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Province [id=" + id + ", name=" + name + ", code=" + code + "]";
	}

}
