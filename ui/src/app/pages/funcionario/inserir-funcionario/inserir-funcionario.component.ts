import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { NgxMaskDirective,provideNgxMask } from 'ngx-mask';
import { FuncionarioService } from '../../../services/funcionario.service';
import { Funcionario } from '../../../models/funcionario/funcionario.model';

@Component({
  selector: 'app-inserir-funcionario',
  standalone: true,
  templateUrl: './inserir-funcionario.component.html',
  styleUrl: './inserir-funcionario.component.scss',
  imports: [FormsModule,CommonModule,RouterModule,NgxMaskDirective],
  providers: [provideNgxMask()]
})

export class InserirFuncionarioComponent {

  cpf: string = "";
  nome: string = "";
  email: string = "";
  telefone: string = "";

  constructor(private funcionarioService: FuncionarioService,private router: Router) {}

  onSubmit() {
    // Envio dos dados ao backend (a ser implementado)
    const novoFuncionario: Funcionario = {
      cpf: this.cpf,
      nome: this.nome,
      email: this.email,
      telefone: this.telefone
    };

    this.funcionarioService.criarFuncionario(novoFuncionario).subscribe(
      (response) => {
        console.log('Funcionario criado com sucesso:', response);
        alert('Cadastro realizado. O funcionário receberá um e-mail contendo sua senha');
        this.router.navigate(['/home-funcionario']);
      },
      (error) => {
        console.error('Erro ao criar o funcionario:', error);
      }
    );    
  }
}
