import { Injectable } from '@angular/core';
import { Funcionario } from '../models/funcionario/funcionario.model';
import { Observable, of } from 'rxjs';
import { environment } from '../shared/environment/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioService {

  funcionarios: Array<Funcionario> = [
    new Funcionario('Fulano da Silva', '12345678901', 'fulano@gmail.com', '9999-1111', 1),
    new Funcionario('Cicrano da Silva', '22345678902', 'cicrano@gmail.com', '9999-2222', 2),
    new Funcionario('Beltrano da Silva', '32345678903', 'beltrano@gmail.com', '9999-3333', 3)    
  ];

  private baseUrl = `${environment.apiGatewayUrl}/funcionarios`;

  constructor(private http: HttpClient) { }

  criarFuncionario(novoFuncionario: Funcionario): Observable<Funcionario> {
    return this.http.post<Funcionario>(this.baseUrl, novoFuncionario);
  }

  alterarFuncionario(funcionarioAlterado: Funcionario): Observable<Funcionario> {
    return this.http.put<Funcionario>(`${this.baseUrl}/${funcionarioAlterado.id}`, funcionarioAlterado);
  }
  
  getFuncionarioById(id: number): Observable<Funcionario> {
    return this.http.get<Funcionario>(`${this.baseUrl}/${id}`);
  }

  getFuncionarios(): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(this.baseUrl);
  }

  deletarFuncionario(id: number): Observable<Funcionario> {
    return this.http.delete<Funcionario>(`${this.baseUrl}/${id}`);
  }
}
