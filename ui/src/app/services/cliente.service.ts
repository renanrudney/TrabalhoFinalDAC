import { Injectable } from '@angular/core';
import { Cliente } from '../models/cliente/cliente.model';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  Clientes:Array<Cliente> = [];

  constructor() { }

  criarCliente(novoCliente: Cliente): Observable<Cliente> {
    this.Clientes.push(novoCliente);
    return of(novoCliente);
  }
}
