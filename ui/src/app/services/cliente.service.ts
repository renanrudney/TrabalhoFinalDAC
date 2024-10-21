import { Injectable } from '@angular/core';
import { Cliente } from '../models/cliente/cliente.model';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  Clientes: Array<Cliente> = [
    new Cliente('00000000000','Joao Silva','a@email.com','Rua x,1',null,'80000000','Curitiba','PR',100,1)
  ];

  constructor() { }

  criarCliente(novoCliente: Cliente): Observable<Cliente> {
    this.Clientes.push(novoCliente);
    return of(novoCliente);
  }

  getClienteByLogin(login: string | null): Cliente | undefined{
    return this.Clientes.find(cliente => cliente.email === login);
  }

  substrairMilhas(milhas: number, email: string): void {
    this.Clientes.filter(cliente => {
      if(cliente.email === email){
        cliente.milhas -= milhas;
      }
    });
  }

}
