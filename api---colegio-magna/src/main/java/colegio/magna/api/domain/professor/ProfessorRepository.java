package colegio.magna.api.domain.professor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfessorRepository extends JpaRepository<Professor, Long>{
	Page<Professor> findAllByAtivoTrue(Pageable paginacao);

	Page<Professor> findByDisciplinaAndAtivoIsTrue(Disciplina disciplina, Pageable paginacao);
	
	@Transactional
		@Modifying
		@Query(value = "TRUNCATE professores, endereco;", nativeQuery = true)
		void LimparDadosERedefinirSequence();
	
	/*@Transactional
	@Modifying
	@Query(value = "DELETE professores, endereco FROM professores INNER JOIN endereco; ALTER SEQUENCE professores_id_seq RESTART WITH 1; ALTER SEQUENCE endereco_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();*/
}