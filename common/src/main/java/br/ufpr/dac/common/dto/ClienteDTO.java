package br.ufpr.dac.common.dto;

import java.io.Serializable;

public class ClienteDTO implements Serializable {

  private int idUsuario;
	// private EnderecoDTO endereco;
	private String cpf;
	private String nome;
	private String email;
	private double milhas;
	
	public ClienteDTO() {
	}

	public ClienteDTO(int idUsuario, String cpf, String nome, String email, double milhas) {
		this.idUsuario = idUsuario;
		// this.endereco = endereco;
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.milhas = milhas;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	// public EnderecoDTO getEndereco() {
	// 	return endereco;
	// }

	// public void setEndereco(EnderecoDTO endereco) {
	// 	this.endereco = endereco;
	// }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getMilhas() {
		return milhas;
	}

	public void setMilhas(double milhas) {
		this.milhas = milhas;
	}

}
