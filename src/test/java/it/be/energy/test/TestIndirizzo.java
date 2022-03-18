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

import it.be.energy.repository.IndirizzoRepository;
import it.be.energy.service.IndirizzoService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TestIndirizzo {
	
	@Autowired
	private MockMvc mockMvc;

	@Mock
	IndirizzoRepository indirizzoRepository;
	
	@Mock
	IndirizzoService indrizzoService;
	
	@Test
	@WithMockUser
	final void testFindAllIndirizzi() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/indirizzo/trovatutti")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "Admin", password = "Admin", roles = "ADMIN")
	final void testPostAdmin() throws Exception {
		String body = "{\r\n"
				+ "  \"id\": 0,\r\n"
				+ "  \"via\": \"Via di li\",\r\n"
				+ "  \"civico\": \"5\",\r\n"
				+ "  \"cap\": \"00154\"\r\n"
				+ "  }";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/indirizzo/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPostUser() throws Exception {
		String body = "{\r\n"
				+ "  \"id\": 0,\r\n"
				+ "  \"via\": \"Via di li\",\r\n"
				+ "  \"civico\": \"5\",\r\n"
				+ "  \"cap\": \"00154\"\r\n"
				+ "  }";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/indirizzo/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isForbidden()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPutAdmin() throws Exception {
		String body = "{\r\n"
				+ "  \"via\": \"Pluto\",\r\n"
				+ "  \"civico\": \"100\",\r\n"
				+ "  \"cap\": \"string\",\r\n"
				+ "  \"localita\": \"string\"\r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/indirizzo/aggiorna/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isAccepted()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPutUser() throws Exception {
		String body = "{\r\n"
				+ "  \"via\": \"Pluto\",\r\n"
				+ "  \"civico\": \"100\",\r\n"
				+ "  \"cap\": \"string\",\r\n"
				+ "  \"localita\": \"string\"\r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/indirizzo/aggiorna/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isForbidden()).andReturn();
	}
	@Test
	@WithMockUser
	final void testFindAllIndirizziById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/indirizzo/trovatuttiperid/2")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/indirizzo/cancella/2")).andDo(print()).andExpect(status().isOk());	
	}

	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testDeleteByIdUser() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/indirizzo/cancella/2")).andDo(print()).andExpect(status().isForbidden());	
	}
}
