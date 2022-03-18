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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.be.energy.repository.ClienteRepository;
import it.be.energy.service.ClienteService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TestCliente {
	@Autowired
	private MockMvc mockMvc;

	@Mock
	ClienteRepository clienteRepository;

	@Mock
	ClienteService clienteService;



	@Test
	@WithMockUser
	final void testFindAllClientiWeb() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/web/mostraelencoclienti")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testFindAllClienti() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/trovatutti")).andDo(print()).andExpect(status().isOk());	
	}

	@Test
	@WithAnonymousUser
	public void testTrovaTuttiClientiWhenUtenteIsAnonymousWeb() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/web/mostraelencoclienti")).andDo(print()).andExpect(status().isOk());
	}
	@Test
	@WithAnonymousUser
	public void testTrovaTuttiClientiWhenUtenteIsAnonymous() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/trovatutti")).andDo(print()).andExpect(status().isUnauthorized());
	}
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPostAdmin() throws Exception {
		String body = "{\r\n"
				+ "  \"ragioneSociale\": \"string\",\r\n"
				+ "  \"piva\": \"string\",\r\n"
				+ "  \"email\": \"string\",\r\n"
				+ "  \"dataInserimento\": \"2020-05-20\",\r\n"
				+ "  \"dataUltimoContatto\": \"2012-03-15\",\r\n"
				+ "  \"fatturatoAnnuale\": 0,\r\n"
				+ "  \"pec\": \"string\",\r\n"
				+ "  \"telefono\": \"string\",\r\n"
				+ "  \"emailContatto\": \"string\",\r\n"
				+ "  \"nomeContatto\": \"string\",\r\n"
				+ "  \"cognomeContatto\": \"string\",\r\n"
				+ "  \"numeroContatto\": \"string\",\r\n"
				+ "  \"tipoCliente\": \"SPA\",\r\n"
				+ "  \"sedeLegale\": {\r\n"
				+ "    \"id\": 2\r\n"
				+ "  },\r\n"
				+ "  \"sedeOperativa\": {\r\n"
				+ "    \"id\": 3\r\n"
				+ "  },\r\n"
				+ "  \"fatture\": [\r\n"
				+ "  ]\r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/cliente/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPostUser() throws Exception {
		String body = "{\r\n"
				+ "  \"ragioneSociale\": \"string\",\r\n"
				+ "  \"piva\": \"string\",\r\n"
				+ "  \"email\": \"string\",\r\n"
				+ "  \"dataInserimento\": \"2020-05-20\",\r\n"
				+ "  \"dataUltimoContatto\": \"2012-03-15\",\r\n"
				+ "  \"fatturatoAnnuale\": 0,\r\n"
				+ "  \"pec\": \"string\",\r\n"
				+ "  \"telefono\": \"string\",\r\n"
				+ "  \"emailContatto\": \"string\",\r\n"
				+ "  \"nomeContatto\": \"string\",\r\n"
				+ "  \"cognomeContatto\": \"string\",\r\n"
				+ "  \"numeroContatto\": \"string\",\r\n"
				+ "  \"tipoCliente\": \"SPA\",\r\n"
				+ "  \"sedeLegale\": {\r\n"
				+ "    \"id\": 2\r\n"
				+ "  },\r\n"
				+ "  \"sedeOperativa\": {\r\n"
				+ "    \"id\": 3\r\n"
				+ "  },\r\n"
				+ "  \"fatture\": [\r\n"
				+ "  ]\r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/cliente/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isForbidden()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPutAdmin() throws Exception {
		String body = "{\r\n"
				+ "  \"ragioneSociale\": \"string\",\r\n"
				+ "  \"piva\": \"string\",\r\n"
				+ "  \"email\": \"string\",\r\n"
				+ "  \"dataInserimento\": \"2020-05-20\",\r\n"
				+ "  \"dataUltimoContatto\": \"2012-03-15\",\r\n"
				+ "  \"fatturatoAnnuale\": 0,\r\n"
				+ "  \"pec\": \"string\",\r\n"
				+ "  \"telefono\": \"string\",\r\n"
				+ "  \"emailContatto\": \"string\",\r\n"
				+ "  \"nomeContatto\": \"string\",\r\n"
				+ "  \"cognomeContatto\": \"string\",\r\n"
				+ "  \"numeroContatto\": \"string\",\r\n"
				+ "  \"tipoCliente\": \"SPA\",\r\n"
				+ "  \"sedeLegale\": {\r\n"
				+ "    \"id\": 2\r\n"
				+ "  },\r\n"
				+ "  \"sedeOperativa\": {\r\n"
				+ "    \"id\": 3\r\n"
				+ "  },\r\n"
				+ "  \"fatture\": [\r\n"
				+ "  ]\r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/cliente/aggiorna/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isAccepted()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPutUser() throws Exception {
		String body = "{\r\n"
				+ "  \"ragioneSociale\": \"string\",\r\n"
				+ "  \"piva\": \"string\",\r\n"
				+ "  \"email\": \"string\",\r\n"
				+ "  \"dataInserimento\": \"2020-05-20\",\r\n"
				+ "  \"dataUltimoContatto\": \"2012-03-15\",\r\n"
				+ "  \"fatturatoAnnuale\": 0,\r\n"
				+ "  \"pec\": \"string\",\r\n"
				+ "  \"telefono\": \"string\",\r\n"
				+ "  \"emailContatto\": \"string\",\r\n"
				+ "  \"nomeContatto\": \"string\",\r\n"
				+ "  \"cognomeContatto\": \"string\",\r\n"
				+ "  \"numeroContatto\": \"string\",\r\n"
				+ "  \"tipoCliente\": \"SPA\",\r\n"
				+ "  \"sedeLegale\": {\r\n"
				+ "    \"id\": 2\r\n"
				+ "  },\r\n"
				+ "  \"sedeOperativa\": {\r\n"
				+ "    \"id\": 3\r\n"
				+ "  },\r\n"
				+ "  \"fatture\": [\r\n"
				+ "  ]\r\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/cliente/aggiorna/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isForbidden()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/cliente/cancella/1")).andDo(print()).andExpect(status().isOk());	
	}

	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testDeleteByIdUser() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/cliente/cancella/1")).andDo(print()).andExpect(status().isForbidden());	
	}
	@Test
	@WithMockUser
	final void testfindById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/trovaperid/1")).andDo(print()).andExpect(status().isOk());	
	}

	@Test
	@WithMockUser
	final void findByRagioneSocialeLike() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/trovaperragionesociale")).andDo(print()).andExpect(status().isOk());	
	}
	@Test
	@WithMockUser
	final void findByOrderByDataUltimoContatto() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/ordinaperdataultimocontatto")).andDo(print()).andExpect(status().isOk());	
	}
	@Test
	@WithMockUser
	final void findByOrderByDataInserimento() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/ordinaperdatainserimento")).andDo(print()).andExpect(status().isOk());	
	}
	@Test
	@WithMockUser
	final void findAllByOrderByFatturatoAnnuale() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/ordinaperfatturato")).andDo(print()).andExpect(status().isOk());	
	}

	@Test
	@WithMockUser
	final void findByNomeContaining() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/trovapernomecontaining/Pag")).andDo(print()).andExpect(status().isOk());	
	}





}
