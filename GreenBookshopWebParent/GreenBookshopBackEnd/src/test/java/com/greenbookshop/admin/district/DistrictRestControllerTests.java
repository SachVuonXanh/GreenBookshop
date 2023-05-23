package com.greenbookshop.admin.district;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenbookshop.admin.repository.ProvinceRepository;
import com.greenbookshop.admin.repository.DistrictRepository;
import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.District;

@SpringBootTest
@AutoConfigureMockMvc
public class DistrictRestControllerTests {

	@Autowired 
	MockMvc mockMvc;

	@Autowired 
	ObjectMapper objectMapper;

	@Autowired 
	ProvinceRepository provinceRepo;

	@Autowired 
	DistrictRepository districtRepo;

	@Test
	@WithMockUser(username = "ya@a.com", password = "12345678", roles = "ADMIN")
	public void testListByProvinces() throws Exception {
		Integer provinceId = 2;
		String url = "/districts/list_by_province/" + provinceId;

		MvcResult result = mockMvc.perform(get(url))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		String jsonResponse = result.getResponse().getContentAsString();
		District[] districts = objectMapper.readValue(jsonResponse, District[].class);

		assertThat(districts).hasSizeGreaterThan(1);
	}

	@Test
	@WithMockUser(username = "ya@a.com", password = "12345678", roles = "ADMIN")
	public void testCreateDistrict() throws Exception {
		String url = "/districts/save";
		Integer provinceId = 2;
		Province province = provinceRepo.findById(provinceId).get();
		District district = new District("Arizona", province);

		MvcResult result = mockMvc.perform(post(url).contentType("application/json")
				.content(objectMapper.writeValueAsString(district))
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();

		String response = result.getResponse().getContentAsString();
		Integer districtId = Integer.parseInt(response);
		Optional<District> findById = districtRepo.findById(districtId);

		assertThat(findById.isPresent());		
	}

	@Test
	@WithMockUser(username = "ya@a.com", password = "12345678", roles = "ADMIN")
	public void testUpdateDistrict() throws Exception {
		String url = "/districts/save";
		Integer districtId = 9;
		String districtName = "Alaska";

		District district = districtRepo.findById(districtId).get();
		district.setName(districtName);

		mockMvc.perform(post(url).contentType("application/json")
			.content(objectMapper.writeValueAsString(district))
			.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(String.valueOf(districtId)));

		Optional<District> findById = districtRepo.findById(districtId);
		assertThat(findById.isPresent());

		District updatedDistrict = findById.get();
		assertThat(updatedDistrict.getName()).isEqualTo(districtName);

	}

	@Test
	@WithMockUser(username = "ya@a.com", password = "12345678", roles = "ADMIN")
	public void testDeleteDistrict() throws Exception {
		Integer districtId = 6;
		String uri = "/districts/delete/" + districtId;

		mockMvc.perform(get(uri)).andExpect(status().isOk());

		Optional<District> findById = districtRepo.findById(districtId);

		assertThat(findById).isNotPresent();
	}	
}
