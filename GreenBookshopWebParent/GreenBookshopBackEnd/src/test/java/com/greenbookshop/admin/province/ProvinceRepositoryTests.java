package com.greenbookshop.admin.province;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.greenbookshop.admin.repository.ProvinceRepository;
import com.greenbookshop.common.entity.Province;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProvinceRepositoryTests {

	@Autowired 
	private ProvinceRepository repo;

	@Test
	public void testCreateProvince() {
		Province province = repo.save(new Province("China", "CN"));
		assertThat(province).isNotNull();
		assertThat(province.getId()).isGreaterThan(0);
	}

	@Test
	public void testListProvinces() {
		List<Province> listProvinces = repo.findAllByOrderByNameAsc();
		listProvinces.forEach(System.out::println);

		assertThat(listProvinces.size()).isGreaterThan(0);
	}

	@Test
	public void testUpdateProvince() {
		Integer id = 1;
		String name = "Republic of India";

		Province province = repo.findById(id).get();
		province.setName(name);

		Province updatedProvince = repo.save(province);

		assertThat(updatedProvince.getName()).isEqualTo(name);
	}

	@Test
	public void testGetProvince() {
		Integer id = 3;		
		Province province = repo.findById(id).get();
		assertThat(province).isNotNull();
	}

	@Test
	public void testDeleteProvince() {
		Integer id = 5;
		repo.deleteById(id);

		Optional<Province> findById = repo.findById(id);
		assertThat(findById.isEmpty());
	}
}
