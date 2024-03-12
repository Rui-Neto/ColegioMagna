package colegio.magna.api.domain.endereco;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "endereco")
@Entity(name = "Endereco")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_endereco")

public class Endereco {
	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String logradouro;
	
	@NotBlank
	private String bairro;
	
	@NotBlank
	@Pattern(regexp = "\\d{8}")
	private String cep;
	
	@NotBlank
	private String cidade;
	
	@NotBlank
	private String uf;
	
	@NotBlank
	private String numero;
	
	private String complemento;
		
	public Endereco() {
	}

	public Endereco(DadosEndereco dados) {
		this.logradouro = dados.logradouro();
		this.bairro = dados.bairro();
		this.cep = dados.cep();
		this.uf = dados.uf();
		this.cidade = dados.cidade();
		this.numero = dados.numero();
		this.complemento = dados.complemento();
	}

	public void atualizarInformacoes(DadosEndereco dados) {
		if (dados.logradouro() != null) {
			this.logradouro = dados.logradouro();
		}
		if (dados.bairro() != null) {
			this.bairro = dados.bairro();
		}
		if (dados.cep() != null) {
			this.cep = dados.cep();
		}
		if (dados.uf() != null) {
			this.uf = dados.uf();
		}
		if (dados.cidade() != null) {
			this.cidade = dados.cidade();
		}
		if (dados.numero() != null) {
			this.numero = dados.numero();
		}
		if (dados.complemento() != null) {
			this.complemento = dados.complemento();
		}
	}

	public String getComplemento() {
		return complemento;
	}

	public @NotBlank String getBairro() {
		return bairro;
	}

	public @NotBlank String getLogradouro() {
		return logradouro;
	}

	public @NotBlank String getNumero() {
		return numero;
	}

	public @NotBlank @Pattern(regexp = "\\d{8}") String getCep() {
		return cep;
	}

	public @NotBlank String getUf() {
		return uf;
	}

	public @NotBlank String getCidade() {
		return cidade;
	}

}
