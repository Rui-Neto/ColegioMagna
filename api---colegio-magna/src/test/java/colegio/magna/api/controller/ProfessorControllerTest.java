package colegio.magna.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import colegio.magna.api.domain.endereco.DadosEndereco;
import colegio.magna.api.domain.professor.DadosAtualizacaoProfessor;
import colegio.magna.api.domain.professor.DadosCadastroProfessor;
import colegio.magna.api.domain.professor.Disciplina;
import colegio.magna.api.domain.professor.Professor;
import colegio.magna.api.domain.professor.ProfessorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProfessorControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ProfessorRepository repository;
	
	@LocalServerPort
	private int randomServerPort;

	private ResponseEntity<Professor> response;

	@Transactional
	@AfterEach
	public void redefinirSequenciaELimparDados() {
		repository.LimparDadosERedefinirSequence();
	}

	@BeforeEach
	public void inicializar() {
		DadosEndereco dadosEndereco = new DadosEndereco("rua xpto", "bairro", "00000000", "Brasilia", "DF", "2002", null);
		DadosCadastroProfessor dtoProfessor = new DadosCadastroProfessor("Teste Silva", "61999999999", "50", 
				"professor@colegio.magna", Disciplina.MATEMATICA, dadosEndereco); 

		
		ResponseEntity<Professor> response = restTemplate.postForEntity("/professores", dtoProfessor,
				Professor.class);
	}


	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar um Professor já que suas informações estão validas")
	void cadastro_cenario1() {
		
		DadosEndereco dadosEndereco = new DadosEndereco("rua cadastro", "cadastro", "11000000", "Brasilia", "DF", "2000", null);
		DadosCadastroProfessor dtoProfessor = new DadosCadastroProfessor("Teste Cadastro", "61999999900", "40", 
				"cadastro@colegio.magna", Disciplina.MATEMATICA, dadosEndereco); 

		HttpEntity<DadosCadastroProfessor> requestEntity = new HttpEntity<>(dtoProfessor);
		
		ResponseEntity<Professor> response = restTemplate.exchange("/professores", HttpMethod.POST, requestEntity,
				Professor.class);

		/*HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);*/
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		/*String responseBody = response.getBody();
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(responseBody.contains("Professor cadastrado com sucesso"));*/

	}
}
	/*@Test
	@DisplayName("cadastro com info invalida deve dar erro 400 - BadRequest")
	void cadastro_cenario2() {
		DadosCadastroProfessor professorDtoTeste = new DadosCadastroProfessor(null, null, null, null, null, null);

		ResponseEntity<Professor> response = restTemplate.postForEntity("/professores", professorDtoTeste,
				Professor.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

		restTemplate = new TestRestTemplate();
	}

	
	// TESTES GET
	
	@Test
	@DisplayName("Deveria listar todas os Professores e dar codigo 200")
	void listagem() {
		ResponseEntity<List<Professor>> response = restTemplate.exchange(
				"/professores", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Professor>>() {
				});
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		
		List<Professor> professores = response.getBody();
		Assert.assertNotNull(professores);
		
	}
	
	@Test
	@DisplayName("Deveria listar o professor de id 1 e dar codigo 200")
	void listagemId_cenario1() {
		ResponseEntity<Professor> response = restTemplate
				.getForEntity("professores/id/1", Professor.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria dar not found pois professor de id 30 nao existe")
	public void testLitarUmBairroComIdInvalido() {
		ResponseEntity<Professor> response = restTemplate
				.getForEntity("professor/id/30", Professor.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

	// TESTES PUT
	
	@Test
	@DisplayName("Deveria retornar o bairro de ID 1 alterado com sucesso")
	public void atualiza_cenario1() {
		DadosEndereco dadosEnderecoAtualizado = new DadosEndereco("rua atualizado", "bairro atualizado", "00000002", "Sao Paulo", "SP", "2000", null);
		DadosAtualizacaoProfessor professorDtoAtualizado = new DadosAtualizacaoProfessor(1L, "Teste Atualizado", "57", dadosEnderecoAtualizado);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<DadosAtualizacaoProfessor> request = new HttpEntity<>(professorDtoAtualizado, headers);
		
		ResponseEntity<Professor> response = restTemplate.exchange("/professores",
				HttpMethod.PUT, request, Professor.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}
}*/