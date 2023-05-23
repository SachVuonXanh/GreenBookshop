package com.greenbookshop.admin.district;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.greenbookshop.admin.repository.DistrictRepository;
import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.District;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DistrictRepositoryTests {

	private DistrictRepository repo;
	
	private TestEntityManager entityManager;
	
	@Autowired 
	public DistrictRepositoryTests(DistrictRepository repo, TestEntityManager entityManager) {
		super();
		this.repo = repo;
		this.entityManager = entityManager;
	}

	@Test
	public void testCreateDistrictsInIndia() {
		Integer provinceId = 1;
		Province province = entityManager.find(Province.class, provinceId);

//		District district = repo.save(new District("Karnataka", province));
//		District district = repo.save(new District("Punjab", province));
//		District district = repo.save(new District("Uttar Pradesh", province));
		District district = repo.save(new District("West Bengal", province));

		assertThat(district).isNotNull();
		assertThat(district.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateDistrictsInUS() {
		Integer provinceId = 2;
		Province province = entityManager.find(Province.class, provinceId);

//		District district = repo.save(new District("California", province));
//		District district = repo.save(new District("Texas", province));
//		District district = repo.save(new District("New York", province));
		District district = repo.save(new District("Washington", province));

		assertThat(district).isNotNull();
		assertThat(district.getId()).isGreaterThan(0);
	}

	@Test
	public void testListDistrictsByProvince() {
		Integer provinceId = 2;
		Province province = entityManager.find(Province.class, provinceId);
		List<District> listDistricts = repo.findByProvinceOrderByNameAsc(province);

		listDistricts.forEach(System.out::println);

		assertThat(listDistricts.size()).isGreaterThan(0);
	}

	@Test
	public void testUpdateDistrict() {
		Integer districtId = 3;
		String districtName = "Tamil Nadu";
		District district = repo.findById(districtId).get();

		district.setName(districtName);
		District updatedDistrict = repo.save(district);

		assertThat(updatedDistrict.getName()).isEqualTo(districtName);
	}

	@Test
	public void testGetDistrict() {
		Integer districtId = 1;
		Optional<District> findById = repo.findById(districtId);
		assertThat(findById.isPresent());
	}

	@Test
	public void testDeleteDistrict() {
		Integer districtId = 8;
		repo.deleteById(districtId);

		Optional<District> findById = repo.findById(districtId);
		assertThat(findById.isEmpty());		
	}
}
