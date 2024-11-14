import { Injectable } from '@angular/core';
import { Funcionario } from '../models/funcionario/funcionario.model';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioService {

  funcionarios: Array<Funcionario> = [
    new Funcionario('Fulano da Silva', '12345678901', 'fulano@gmail.com', '9999-1111', 1),
    new Funcionario('Cicrano da Silva', '22345678902', 'cicrano@gmail.com', '9999-2222', 2),
    new Funcionario('Beltrano da Silva', '32345678903', 'beltrano@gmail.com', '9999-3333', 3)    
  ];

  constructor() { }

  criarFuncionario(novoFuncionario: Funcionario): Observable<Funcionario> {
    this.funcionarios.push(novoFuncionario);
    return of (novoFuncionario);
  }

  alterarFuncionario(funcionarioAlterado: Funcionario): void {
    const funcionarioExistente = this.funcionarios.find(funcionario => funcionario.cpf === funcionarioAlterado.cpf);
    
    if (funcionarioExistente) {
      funcionarioExistente.nome = funcionarioAlterado.nome;
      funcionarioExistente.email = funcionarioAlterado.email;
      funcionarioExistente.telefone = funcionarioAlterado.telefone;
    }
  }
  
  getFuncionarioByCpf(cpfFuncionario: string): Observable<Funcionario | null> {
    const funcionario = this.funcionarios.find(funcionario => funcionario.cpf === cpfFuncionario) || null;
    return of(funcionario);
  }

  getFuncionarios(): Observable<Funcionario[]> {
    return of(this.funcionarios);
  }

  deletarFuncionario(cpf: string): void {
    const index = this.funcionarios.findIndex(funcionario => funcionario.cpf === cpf);
    if (index !== -1) {
      this.funcionarios.splice(index, 1);
    }
  }
}
