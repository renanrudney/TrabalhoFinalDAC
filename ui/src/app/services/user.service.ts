import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() { }

  isFuncionario(perfil: string): boolean {
    // Simulação de validação (substitua pela sua lógica de back-end real)
    return perfil === 'funcionario';
  }
  
}
