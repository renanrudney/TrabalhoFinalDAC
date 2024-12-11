import { Injectable } from '@angular/core';
import { Funcionario } from '../models/funcionario/funcionario.model';
import { Observable, of } from 'rxjs';
import { environment } from '../shared/environment/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioService {
  private baseUrl = `${environment.apiGatewayUrl}/funcionarios`;

  constructor(private http: HttpClient) { }

  criarFuncionario(novoFuncionario: Funcionario): Observable<Funcionario> {
    return this.http.post<Funcionario>(this.baseUrl, novoFuncionario, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }

  alterarFuncionario(funcionarioAlterado: Funcionario): Observable<Funcionario> {
    return this.http.put<Funcionario>(`${this.baseUrl}/${funcionarioAlterado.id}`, funcionarioAlterado, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }
  
  getFuncionarioById(id: string): Observable<Funcionario> {
    return this.http.get<Funcionario>(`${this.baseUrl}/${id}`, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }

  getFuncionarios(): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(this.baseUrl, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }

  deletarFuncionario(id: string): Observable<Funcionario> {
    return this.http.delete<Funcionario>(`${this.baseUrl}/${id}`, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }
}
