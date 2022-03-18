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

import it.be.energy.repository.ClienteRepository;
import it.be.energy.repository.FatturaRepository;
import it.be.energy.service.ClienteService;
import it.be.energy.service.FatturaService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TestFattura {
	
	@Autowired
	private MockMvc mockMvc;

	@Mock
	FatturaRepository fatturaRepository;
	
	@Mock
	FatturaService fatturaService;
	
	@Mock
	ClienteRepository clienteRepository;

	@Mock
	ClienteService clienteService;
	
	@Test
	@WithMockUser
	final void testFindAllFatture() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/trovatutte")).andDo(print()).andExpect(status().isOk());	
	}

	@Test
	@WithMockUser
	final void testFindAllFattureById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/trovaperid/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPostAdmin() throws Exception {
		String body = "{\r\n"
				+ "  \"id\": 0,\r\n"
				+ "  \"numeroFattura\": 100000,\r\n"
				+ "  \"anno\": 2033,\r\n"
				+ "  \"data\": \"2022-03-15T16:10:36.119Z\",\r\n"
				+ "  \"importo\": 1000,\r\n"
				+ "  \"cliente\": {\r\n"
				+ "    \"id\": 1\r\n"
				+ "    },\r\n"
				+ "  \"statoFattura\": {\r\n"
				+ "    \"id\": 1\r\n"
				+ "  }\r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/fattura/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
	}
	
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPostUser() throws Exception {
		String body = "{\r\n"
				+ "  \"id\": 0,\r\n"
				+ "  \"numeroFattura\": 100000,\r\n"
				+ "  \"anno\": 2033,\r\n"
				+ "  \"data\": \"2022-03-15T16:10:36.119Z\",\r\n"
				+ "  \"importo\": 1000,\r\n"
				+ "  \"cliente\": {\r\n"
				+ "    \"id\": 1\r\n"
				+ "    },\r\n"
				+ "  \"statoFattura\": {\r\n"
				+ "    \"id\": 1\r\n"
				+ "  }\r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/fattura/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isForbidden()).andReturn();
	}
	
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPutAdimn() throws Exception {
		String body = "{\r\n"
				+ "  \"numeroFattura\": 10,\r\n"
				+ "  \"anno\": 0,\r\n"
				+ "  \"data\": \"2022-03-15T16:16:15.882Z\",\r\n"
				+ "  \"importo\": 100,\r\n"
				+ "  \"cliente\": {\r\n"
				+ "    \"id\": 2\r\n"
				+ "  },\r\n"
				+ "  \"statoFattura\": {\r\n"
				+ "    \"id\": 3\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/fattura/aggiorna/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isAccepted()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPutUser() throws Exception {
		String body = "{\r\n"
				+ "  \"numeroFattura\": 10,\r\n"
				+ "  \"anno\": 0,\r\n"
				+ "  \"data\": \"2022-03-15T16:16:15.882Z\",\r\n"
				+ "  \"importo\": 100,\r\n"
				+ "  \"cliente\": {\r\n"
				+ "    \"id\": 2\r\n"
				+ "  },\r\n"
				+ "  \"statoFattura\": {\r\n"
				+ "    \"id\": 3\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/fattura/aggiorna/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isForbidden()).andReturn();
	}
	
	
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/fattura/cancella/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testFindAllPerRagioneSociale() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/trovaperragionesocialecliente/Pagnozzi's")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testTrovaPerStatoFattura() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/trovaperidstatofattura/2")).andDo(print()).andExpect(status().isOk());
	
	}
	
	@Test
	@WithMockUser
	final void testTrovaPerData() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/trovaperdata/2019-12-23")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	final void testTrovaPerAnno() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/trovaperanno/2019")).andDo(print()).andExpect(status().isOk());
	}
	
} 