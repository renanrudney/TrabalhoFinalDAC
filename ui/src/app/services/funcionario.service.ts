import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Funcionario } from '../models/funcionario/funcionario.model';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioService {

  private apiUrl = 'URL_DO_SEU_GATEWAY'; // Substitua pela URL do seu API Gateway

  constructor(private http: HttpClient) {}

  criarFuncionario(novoFuncionario: Funcionario): Observable<Funcionario> {
    return this.http.post<Funcionario>(`${this.apiUrl}/funcionarios`, novoFuncionario);
  }

  alterarFuncionario(funcionarioAlterado: Funcionario): Observable<Funcionario> {
    return this.http.put<Funcionario>(`${this.apiUrl}/funcionarios/${funcionarioAlterado.cpf}`, funcionarioAlterado);
  }
  
  getFuncionarioByCpf(cpfFuncionario: string): Observable<Funcionario | null> {
    return this.http.get<Funcionario | null>(`${this.apiUrl}/funcionarios/${cpfFuncionario}`);
  }

  getFuncionarios(): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(`${this.apiUrl}/funcionarios`);
  }

  deletarFuncionario(cpf: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/funcionarios/${cpf}`);
  }
}
