package com.greenbookshop.common.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class AbstractAddressWithProvince extends AbstractAddress{

	@ManyToOne
	@JoinColumn(name = "province_id")
	protected Province province;
	
	@Override
	public String toString() {
		
		String address = firstName;

		if (lastName != null && !lastName.isEmpty()) address += " " + lastName;

		if (!addressLine1.isEmpty()) address += ", " + addressLine1;

		if (addressLine2 != null && !addressLine2.isEmpty()) address += ", " + addressLine2;

		if (!commune.isEmpty()) address += ", " + commune;

		if (district != null && !district.isEmpty()) address += ", " + district;

//		address += ", " + this.province != null? province.getName() : "";

		if (!postalCode.isEmpty()) address += ". Postal Code: " + postalCode;
		if (!phoneNumber.isEmpty()) address += ". Phone Number: " + phoneNumber;

		return address;
	}
}
