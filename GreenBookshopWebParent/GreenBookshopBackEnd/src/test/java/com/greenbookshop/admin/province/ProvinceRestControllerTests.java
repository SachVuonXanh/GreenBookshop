package com.greenbookshop.admin.province;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenbookshop.admin.repository.ProvinceRepository;
import com.greenbookshop.common.entity.Province;

@SpringBootTest
@AutoConfigureMockMvc
public class ProvinceRestControllerTests {

	MockMvc mockMvc;

	ObjectMapper objectMapper;

	ProvinceRepository repo;
	
	@Autowired
	public ProvinceRestControllerTests(MockMvc mockMvc, ObjectMapper objectMapper, ProvinceRepository repo) {
		super();
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.repo = repo;
	}

	@Test
	@WithMockUser(username = "ya@a.com", password = "12345678", roles = "ADMIN")
	public void testListProvinces() throws Exception {
		String url = "/provinces/list";

		MvcResult result = mockMvc.perform(get(url))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();

		String jsonResponse = result.getResponse().getContentAsString();		
		Province[] provinces = objectMapper.readValue(jsonResponse, Province[].class);

		assertThat(provinces).hasSizeGreaterThan(0);
	}

	@Test
	@WithMockUser(username = "ya@a.com", password = "12345678", roles = "ADMIN")
	public void testCreateProvince() throws JsonProcessingException, Exception {
		String url = "/provinces/save";
		String provinceName = "Germany";
		String provinceCode = "DE";
		Province province = new Province(provinceName, provinceCode);

		MvcResult result = mockMvc.perform(post(url).contentType("application/json")
				.content(objectMapper.writeValueAsString(province))
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();

		String response = result.getResponse().getContentAsString();
		Integer provinceId = Integer.parseInt(response);

		Optional<Province> findById = repo.findById(provinceId);
		assertThat(findById.isPresent());

		Province savedProvince = findById.get();

		assertThat(savedProvince.getName()).isEqualTo(provinceName);
	}

	@Test
	@WithMockUser(username = "ya@a.com", password = "12345678", roles = "ADMIN")
	public void testUpdateProvince() throws JsonProcessingException, Exception {
		String url = "/provinces/save";

		Integer provinceId = 7;
		String provinceName = "China";
		String provinceCode = "CN";
		Province province = new Province(provinceId, provinceName, provinceCode);

		mockMvc.perform(post(url).contentType("application/json")
				.content(objectMapper.writeValueAsString(province))
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(String.valueOf(provinceId)));

		Optional<Province> findById = repo.findById(provinceId);
		assertThat(findById.isPresent());

		Province savedProvince = findById.get();

		assertThat(savedProvince.getName()).isEqualTo(provinceName);
	}

	@Test
	@WithMockUser(username = "ya@a.com", password = "12345678", roles = "ADMIN")
	public void testDeleteProvince() throws Exception {
		Integer provinceId = 7;
		String url = "/provinces/delete/" + provinceId;
		
		mockMvc.perform(get(url)).andExpect(status().isOk());

		Optional<Province> findById = repo.findById(provinceId);

		assertThat(findById).isNotPresent();
	}
}
