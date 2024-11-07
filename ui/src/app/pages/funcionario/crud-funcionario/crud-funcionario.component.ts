import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

interface Funcionario {
  id: number;
  nome: String;
  cpf: string;
  email: String;
  telefone: string;
}

@Component({
  selector: 'app-crud-funcionario',
  standalone: true,
  imports: [CommonModule,FormsModule, RouterModule],
  templateUrl: './crud-funcionario.component.html',
  styleUrl: './crud-funcionario.component.scss'
})

export class CrudFuncionarioComponent {
  funcionarios: Funcionario[] = [
    { id: 1, nome: 'Fulano da Silva', cpf: '12345678901', email: 'fulano@gmail.com', telefone: '9999-1111' },
    { id: 2, nome: 'Cicrano da Silva', cpf: '22345678902', email: 'cicrano@gmail.com', telefone: '9999-2222' },
    { id: 3, nome: 'Beltrano da Silva', cpf: '32345678903', email: 'beltrano@gmail.com', telefone: '9999-3333' },
  ];
  
  constructor(private router: Router) {}

  alterarFuncionario(cpf: string): void {
    this.router.navigate(['/alterar-funcionario', cpf]);
    // Lógica para ver lista de confirmação
  }

  removerFuncionario(id: number): void {
    console.log(`remover funcionário com ID: ${id}`);
    // Lógica para cancelar voo
    this.funcionarios = this.funcionarios.filter(funcionario => funcionario.id !== id);
  }
}