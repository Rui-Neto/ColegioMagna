package colegio.magna.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus.Series;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import colegio.magna.api.domain.endereco.DadosEndereco;
import colegio.magna.api.domain.professor.DadosAtualizacaoProfessor;
import colegio.magna.api.domain.professor.DadosCadastroProfessor;
import colegio.magna.api.domain.professor.DadosCompletoProfessor;
import colegio.magna.api.domain.professor.DadosListagemProfessor;
import colegio.magna.api.domain.professor.Disciplina;
import colegio.magna.api.domain.professor.Professor;
import colegio.magna.api.domain.professor.ProfessorRepository;
import colegio.magna.api.services.ProfessorService;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@RunWith(MockitoJUnitRunner.class)
class Teste {
	@MockBean
	private ProfessorRepository repository;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JacksonTester<DadosCadastroProfessor> dadosCadastroProfessorJson;

	@Autowired
	private JacksonTester<DadosAtualizacaoProfessor> dadosAtualizacaoProfessorJson;

	private ProfessorService professorService;

	@Test
	@DisplayName("cadastro com info invalida deve dar erro 400 - BadRequest")
	void cadastro_cenario1() {
		var professorDtoTeste = new DadosCadastroProfessor(null, null, null, null, null, null);

		ResponseEntity<Professor> response = restTemplate.postForEntity("/professores", professorDtoTeste,
				Professor.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

		restTemplate = new TestRestTemplate();
	}

	@Test
	@DisplayName("Deveria devolver codigo http 200 e mensagem quando informacoes estao validas")
	void cadasto_cenario2() throws Exception {
		var professorDtoTeste = new DadosCadastroProfessor("Teste de Moraes", "61009999999", "50",
				"professorTeste@colegio.magna", Disciplina.MATEMATICA, dadosEndereco());

		when(repository.save(any())).thenReturn(new Professor(professorDtoTeste));

		var response = mvc
				.perform(post("/professores").contentType(MediaType.APPLICATION_JSON)
						.content(dadosCadastroProfessorJson.write(professorDtoTeste).getJson()))
				.andReturn().getResponse();

		assertThat(response.getContentAsString()).isEqualTo("Professor cadastrado com sucesso");
	}

	private DadosEndereco dadosEndereco() {
		return new DadosEndereco("rua xpto", "bairro", "00000000", "Brasilia", "DF", "2002", null);
	}

	@Test
	@DisplayName("Deve dar codigo http 404 - NotFound quando o id nao existe")
	void listagemId_cenario1() {

		ResponseEntity<Professor> response = restTemplate.getForEntity("/professores/id/10", Professor.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}

	@Test
	@DisplayName("Deve dar codigo http 204 - NO_CONTENT")
	void exclusaoProfessores() throws Exception {
		
		ResultActions response = mvc.perform(delete("/professores/1"));

		assertThat(response.andExpect(status().isNoContent()));
	}

	/*
	 * @Test
	 * 
	 * @DisplayName("Deve dar codigo http 204 - NO_CONTENT") void
	 * exclusaoProfessores() { var professorDtoTeste = new
	 * DadosCadastroProfessor("Teste de Moraes", "61009999999", "50",
	 * "professorTeste@colegio.magna", Disciplina.MATEMATICA, dadosEndereco());
	 * 
	 * repository.save(new Professor(professorDtoTeste));
	 * 
	 * ResponseEntity<Professor> response = restTemplate.exchange("/professores/1",
	 * HttpMethod.DELETE, null, Professor.class);
	 * 
	 * assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT); }
	 */

	/*
	 * @Test
	 * 
	 * @DisplayName("deve devolver codigo 400 - Bad Request quando info e invalida")
	 * public void atualiza_cenario2() { ResponseEntity<Professor> response =
	 * restTemplate.exchange("/professores", HttpMethod.PUT, null, Professor.class);
	 * assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST); }
	 */

	@Test
	@DisplayName("Deve devolver código 400 - Bad Request quando info é inválida")
	void atualiza_cenario1() {

		DadosAtualizacaoProfessor professor = new DadosAtualizacaoProfessor(4L, "Teste", "23", dadosEndereco());

		HttpEntity<DadosAtualizacaoProfessor> requestEntity = new HttpEntity<>(professor);

		ResponseEntity<Professor> response = restTemplate.exchange("/professores", HttpMethod.PUT, requestEntity,
				Professor.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@DisplayName("Deve devolver código 400 - Bad Request quando info é inválida")
	void atualiza_cenario2() {

		DadosAtualizacaoProfessor invalidProfessor = new DadosAtualizacaoProfessor(null, null, null, null);

		HttpEntity<DadosAtualizacaoProfessor> requestEntity = new HttpEntity<>(invalidProfessor);

		ResponseEntity<Professor> response = restTemplate.exchange("/professores", HttpMethod.PUT, requestEntity,
				Professor.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	/*
	 * @Test
	 * 
	 * @DisplayName("Deve dar codigo http 200 - Ok quando pesquisado e lista vir nao nula"
	 * ) public void listagemProfessores() {
	 * ResponseEntity<Page<DadosListagemProfessor>> response =
	 * restTemplate.getForEntity("/professores", null, new
	 * ParameterizedTypeReference<Page<DadosListagemProfessor>>() { });
	 * 
	 * assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	 * assertThat(response.getBody()).isNotNull(); }
	 */

	@Test
	@DisplayName("Deve dar codigo http 200 - OK e imprimir as info completas do professor do id pesquisado")
	void listagemId_cenario2() {

		ResponseEntity<Professor> response = restTemplate.exchange("/professores/id/4", HttpMethod.GET, null,
				Professor.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	/*
	 * @Test
	 * 
	 * @DisplayName("deve ser cadastrado corretamente e devolver um codigo http 200 - OK"
	 * ) public void cadastro_cenario2() { var professorDtoTeste = new
	 * DadosCadastroProfessor( "Teste Silva", "61999999999", "50",
	 * "professor@colegio.magna", Disciplina.MATEMATICA, dadosEndereco());
	 * 
	 * when(repository.save(any())).thenReturn(new Professor(professorDtoTeste));
	 * 
	 * ResponseEntity<Professor> response =
	 * restTemplate.postForEntity("/professores", professorDtoTeste,
	 * Professor.class);
	 * 
	 * assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); }
	 */
}

/*
 * @Test
 * 
 * @DisplayName("Deve dar codigo http 200 - Ok quando pesquisado") public void
 * listagemDisciplina() {
 * 
 * ResponseEntity<Professor> response =
 * restTemplate.getForEntity("/professores", Professor.class);
 * 
 * assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); }
 */

/*
 * @Test
 * 
 * @DisplayName("Deve dar codigo http 200 - Ok quando pesquisado e lista vir nao nula"
 * ) public void listagemProfessores() {
 * 
 * ResponseEntity<Page<DadosListagemProfessor>> response =
 * restTemplate.exchange("/professores", HttpMethod.GET, null, new
 * ParameterizedTypeReference<Page<DadosListagemProfessor>>() { });
 * 
 * assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
 * assertThat(response.getBody()).isNotNull(); }
 */
