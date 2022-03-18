package it.be.energy.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.be.energy.repository.StatoFatturaRepository;
import it.be.energy.service.StatoFatturaService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TestStatoFattura {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	StatoFatturaRepository statoFatturaRepository;

	@Mock
	StatoFatturaService statoFatturaService;
	
	@Test
	@WithMockUser
	final void testFindAllStatoFattura() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/statofattura/trovatutti")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testFindAllStatoFatturaById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/statofattura/trovaperid/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPostAdmin() throws Exception {
		String body ="{\r\n"
				+ "  \"id\": 0,\r\n"
				+ "  \"nome\": \"Pagata\"\r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/statofattura/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPostUser() throws Exception {
		String body ="{\r\n"
				+ "  \"id\": 0,\r\n"
				+ "  \"nome\": \"Pagata\"\r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/statofattura/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isForbidden()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPutAdmin() throws Exception {
		String body = "{\r\n"
				+ "  \"id\": 0,\r\n"
				+ "  \"nome\": \"Pagata\"\r\n"
				+ "}\r\n"
				+ "";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/statofattura/aggiorna/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isAccepted()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPutUser() throws Exception {
		String body = "{\r\n"
				+ "  \"id\": 0,\r\n"
				+ "  \"nome\": \"Pagata\"\r\n"
				+ "}\r\n"
				+ "";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/statofattura/aggiorna/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isForbidden()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testDeleteByIdAdmin() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/statofattura/cancella/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testDeleteByIdUser() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/statofattura/cancella/1")).andDo(print()).andExpect(status().isForbidden());	
	}
	
} 
