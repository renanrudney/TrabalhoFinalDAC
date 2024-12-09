import { Injectable } from '@angular/core';
import { Cliente } from '../models/cliente/cliente.model';
import { Observable, map } from 'rxjs';
import { environment } from '../shared/environment/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private baseUrl = `${environment.apiGatewayUrl}/clientes`;

  Clientes: Array<Cliente> = [
    new Cliente('00000000000','Joao Silva','a@email.com','Rua x,1',null,'80000000','Curitiba','PR',100,1)
  ];

  constructor(private http: HttpClient) { }

  criarCliente(novoCliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(`${this.baseUrl}`, novoCliente);
  }

  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.baseUrl);
  } 

  getClienteById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.baseUrl}/${id}`);
  }

  getClienteMilhas(id: number): Observable<number | undefined> {
    return this.getClientes().pipe(
      map((clientes: Cliente[]) => {
        const cliente = clientes.find((cliente: Cliente) => cliente.id === id);
        return cliente?.milhas; // Retorna undefined se o cliente não for encontrado
      })
    );
  }

  substrairMilhas(milhas: number, clienteId: number): void {
    this.Clientes.filter(cliente => {
      if(cliente.id === clienteId){
        cliente.milhas -= milhas;
      }
    });
  }

  devolderMilhas(milhas: number, clienteId: number): void {
    this.Clientes.filter(cliente => {
      if(cliente.id === clienteId){
        cliente.milhas += milhas;
      }
    });
  }

}
