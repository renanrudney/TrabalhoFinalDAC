import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import axios from 'axios';

@Component({
  selector: 'app-autocadastro',
  standalone: true,
  templateUrl: './autocadastro.component.html',
  styleUrls: ['./autocadastro.component.css'],
  imports: [FormsModule] 
})
export class AutocadastroComponent {
  user = {
    cpf: '',
    nome: '',
    email: '',
    telefone: '',
    cep: '',
    logradouro: '',
    bairro: '',
    cidade: '',
    estado: ''
  };

  buscarEndereco(cep: string) {
    axios.get(`https://viacep.com.br/ws/${cep}/json/`)
      .then((response: { data: { erro: any; logradouro: string; bairro: string; localidade: string; uf: string; }; }) => {
        if (!response.data.erro) {
          this.user.logradouro = response.data.logradouro;
          this.user.bairro = response.data.bairro;
          this.user.cidade = response.data.localidade;
          this.user.estado = response.data.uf;
        } else {
          alert("CEP não encontrado.");
        }
      })
      .catch((error: any) => {
        console.error(error);
        alert("Erro ao buscar o CEP.");
      });
  }

  onSubmit() {
    // Envio dos dados ao backend (a ser implementado)
    console.log("Autocadastro realizado com sucesso!", this.user);
    // Aqui você poderia gerar uma senha aleatória e enviar um e-mail
  }
}
