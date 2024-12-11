import { Injectable } from '@angular/core';
import { Cliente } from '../models/cliente/cliente.model';
import { Observable, map } from 'rxjs';
import { environment } from '../shared/environment/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private baseUrl = `${environment.apiGatewayUrl}/clientes`;

  Clientes: Array<Cliente> = [
    new Cliente('00000000000','Joao Silva','a@email.com','Rua x,1',0,'80000000','Curitiba','PR','100',1)
  ];

  constructor(private http: HttpClient) { }

  criarCliente(novoCliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(`${this.baseUrl}`, novoCliente, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }

  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.baseUrl, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  } 

  getClienteById(id: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.baseUrl}/${id}`, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }

  getClienteByEmail(email: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.baseUrl}?email=${email}`, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }

  getClienteMilhas(id: string): Observable<number | undefined> {
    return this.getClientes().pipe(
      map((clientes: Cliente[]) => {
        const cliente = clientes.find((cliente: Cliente) => cliente.id === id);
        return cliente?.milhas; // Retorna undefined se o cliente não for encontrado
      })
    );
  }

  substrairMilhas(milhas: number, clienteId: string): void {
    this.Clientes.filter(cliente => {
      if(cliente.id === clienteId){
        cliente.milhas -= milhas;
      }
    });
  }

  devolderMilhas(milhas: number, clienteId: string): void {
    this.Clientes.filter(cliente => {
      if(cliente.id === clienteId){
        cliente.milhas += milhas;
      }
    });
  }

}
