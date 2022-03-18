package it.be.energy.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import it.be.energy.repository.ProvinciaRepository;
import it.be.energy.service.ProvinciaService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TestProvincia {
	
	@Autowired
	private MockMvc mockMvc;

	@Mock
	ProvinciaRepository provinciaRepository;
	
	@Mock
	ProvinciaService provinciaService;

	
	@Test
	@WithMockUser
	final void testFindAllProvince() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/provincia/trovatutte")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testFindAllProvinceById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/provincia/trovaperid/5")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	final void testFindAllProvinceByName() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/provincia/trovapernome/Roma")).andDo(print()).andExpect(status().isOk());
	}
	
}
