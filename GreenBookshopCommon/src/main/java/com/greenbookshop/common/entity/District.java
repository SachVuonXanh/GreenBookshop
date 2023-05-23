package com.greenbookshop.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "districts")
@NoArgsConstructor
@Getter
@Setter
public class District  extends IdBasedEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 45)
	private String name;

//	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "province_id")
	private Province province;

	public District(String name, Province province) {
		this.name = name;
		this.province = province;
	}

	@Override
	public String toString() {
		return "District [id=" + id + ", name=" + name + "]";
	}

}