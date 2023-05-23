package com.greenbookshop.shippingrate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.ShippingRate;
import com.greenbookshop.repository.ShippingRateRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ShippingRateRepositoryTests {

	@Autowired 
	private ShippingRateRepository repo;

	@Test
	public void testFindByProvinceAndDistrict() {
		Province usa = new Province(234);
		String district = "New York";
		ShippingRate shippingRate = repo.findByProvinceAndDistrict(usa, district);

		assertThat(shippingRate).isNotNull();
		System.out.println(shippingRate);
	}
}