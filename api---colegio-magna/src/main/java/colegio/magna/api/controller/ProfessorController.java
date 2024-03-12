package colegio.magna.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import colegio.magna.api.domain.professor.DadosAtualizacaoProfessor;
import colegio.magna.api.domain.professor.DadosCadastroProfessor;
import colegio.magna.api.domain.professor.DadosListagemProfessor;
import colegio.magna.api.domain.professor.Disciplina;
import colegio.magna.api.domain.professor.ProfessorRepository;
import colegio.magna.api.services.ProfessorService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("professores")

public class ProfessorController {

	@Autowired
	private ProfessorRepository repository;
	
	@Autowired
	private ProfessorService professorService;

	/*@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroProfessor dados, UriComponentsBuilder uriBuilder) {
		repository.save(new Professor(dados));
		var professor = new Professor(dados);
		var uri = uriBuilder.path("/professores/{id}").buildAndExpand(professor.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosCompletoProfessor(professor));
	}*/
	
	@ApiResponse(responseCode = "200", description = "Professor cadastrado com sucesso", content = @Content(mediaType = "application/json"))
	@PostMapping
	@Transactional
	public ResponseEntity<String> cadastro(@RequestBody @Valid DadosCadastroProfessor dados) {
		professorService.cadastro(dados);
		return ResponseEntity.ok("Professor cadastrado com sucesso");
	}
	

	@GetMapping
	public ResponseEntity<Page<DadosListagemProfessor>>  listagem(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		var pagina = professorService.listagem(paginacao);
		return ResponseEntity.ok(pagina);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/{disciplina}")
	public ResponseEntity listagemDisciplina(@PathVariable Disciplina disciplina, Pageable paginacao) {
		var pagina = professorService.listagemDisicplina(disciplina, paginacao);
		return ResponseEntity.ok(pagina);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/id/{id}")
	public ResponseEntity listagemId(@PathVariable Long id) {
		var professor = professorService.listagemId(id);
		if (professor != null) {
			return ResponseEntity.ok(professor);			
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity atualiza(@RequestBody @Valid DadosAtualizacaoProfessor dados) {
		var professor = professorService.atualiza(dados);
		return ResponseEntity.ok(professor);
	}	
	
	/*@DeleteMapping
	@Transactional
	public ResponseEntity<String> exclusao(@RequestBody @Valid DadosCompletoProfessor dados) {
		professorService.exclusao(dados);
		return ResponseEntity.noContent().build();
	}*/
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity exclusao(@PathVariable Long id) {
		professorService.exclusao(id);
		return ResponseEntity.noContent().build();
	}

}