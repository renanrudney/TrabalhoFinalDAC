import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  Usuarios: Array<Usuario> = [
    new Usuario('a@email.com','1234','cliente'),
    new Usuario('b@email.com','5678','funcionario')
  ];

  constructor() { }

  login(username: string, password: string): boolean {
    // Simulação de validação (substitua pela sua lógica de back-end real)
    const usuario = this.Usuarios.find(user => user.login === username && user.senha === password);
    
    if (usuario) {
      // Aqui você pode armazenar o token ou qualquer outra lógica que desejar
      console.log('Login bem-sucedido:', usuario);
      return true; // Retorna verdadeiro se o login foi bem-sucedido
    } else {
      console.log('Falha no login: usuário ou senha inválidos.');
      return false; // Retorna falso se o login falhar
    }
  }
}
