package colegio.magna.api.domain.professor;

import colegio.magna.api.domain.endereco.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "professores")
@Entity(name = "Professor")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_professor")

public class Professor {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Pattern(regexp ="\\d{11}")
	private String cpf;
	
	@NotBlank
	@Pattern(regexp ="\\d{2,3}")
	private String idade;
	
	@NotBlank
	@Email
	private String email;

	@Enumerated(EnumType.STRING)
	private Disciplina disciplina;
	
	@OneToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name="endereco_id", referencedColumnName="id")
	private Endereco endereco;
	
	private Boolean ativo;
	
	public Professor() {
	}

	public Professor(DadosCadastroProfessor dados) {
		this.ativo = true;
		this.nome = dados.nome();
		this.cpf = dados.cpf();
		this.idade = dados.idade();
		this.email = dados.email();
		this.disciplina = dados.disciplina();
		this.endereco = new Endereco(dados.endereco());
	}
	
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public Long getId() {
		return id;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public String getIdade() {
		return idade;
	}
	
	public boolean getAtivo() {
		return ativo;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void atualizarInformacoes(DadosAtualizacaoProfessor dados) {
		if (dados.nome() != null) {
			this.nome = dados.nome();
		}
		if (dados.idade() != null) {
			this.idade = dados.idade();
		}
		if (dados.endereco() != null) {
			this.endereco.atualizarInformacoes(dados.endereco());
		}
	}
	
	public void excluir() {
		this.ativo = false;
	}

}


