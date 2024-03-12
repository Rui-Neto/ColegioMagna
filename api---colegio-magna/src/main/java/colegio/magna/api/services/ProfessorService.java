package colegio.magna.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import colegio.magna.api.domain.professor.DadosAtualizacaoProfessor;
import colegio.magna.api.domain.professor.DadosCadastroProfessor;
import colegio.magna.api.domain.professor.DadosCompletoProfessor;
import colegio.magna.api.domain.professor.DadosListagemProfessor;
import colegio.magna.api.domain.professor.Disciplina;
import colegio.magna.api.domain.professor.Professor;
import colegio.magna.api.domain.professor.ProfessorRepository;

@Service
public class ProfessorService {
	
	@Autowired
	private ProfessorRepository repository;
	
	public DadosCompletoProfessor cadastro(DadosCadastroProfessor dados) {
		var professor = new Professor();
		repository.save(new Professor(dados));
		return new DadosCompletoProfessor(professor);
	}
	
	public Page<DadosListagemProfessor> listagem(Pageable paginacao) {
		return repository.findAllByAtivoTrue(paginacao).map(DadosListagemProfessor::new);
	}
	
	public Page<DadosCompletoProfessor> listagemDisicplina(Disciplina disciplina, Pageable paginacao) {
		return repository.findByDisciplinaAndAtivoIsTrue(disciplina, paginacao).map(DadosCompletoProfessor::new);
	}
	
	public DadosCompletoProfessor atualiza(DadosAtualizacaoProfessor dados) {
		var professor = repository.getReferenceById(dados.id());
		professor.atualizarInformacoes(dados);
		return new DadosCompletoProfessor(professor);
	}
	
	public void exclusao(Long id) {
		var professor = repository.getReferenceById(id);
		professor.excluir();
	}
	
	/*public void exclusao(DadosCompletoProfessor dados) {
		var professor = repository.getReferenceById(dados.id());
		professor.excluir();
	}*/
	
	public DadosCompletoProfessor listagemId(Long id) {
		
		var existe = repository.existsById(id);
		if (existe) {
			var professor = repository.getReferenceById(id);
			if(professor.getAtivo()) {
				return new DadosCompletoProfessor(professor);
			} return null;
		} else {
			return null;
		}
	}
	
}
