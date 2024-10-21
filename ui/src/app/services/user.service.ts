import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario/usuario.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  Usuarios: Array<Usuario> = [
    new Usuario('a@email.com','1234','cliente'),
    new Usuario('b@email.com','5678','funcionario')
  ];

  constructor(private authService: AuthService) { }

  login(username: string, password: string): boolean {
    // Simulação de validação (substitua pela sua lógica de back-end real)
    const usuario: Usuario | undefined = this.Usuarios.find(user => user.login === username && user.senha === password);
    
    if (usuario) {
      // Aqui você pode armazenar o token ou qualquer outra lógica que desejar
      this.authService.logar(usuario);
      console.log('Login bem-sucedido:', usuario);
      return true; // Retorna verdadeiro se o login foi bem-sucedido
    } else {
      console.log('Falha no login: usuário ou senha inválidos.');
      return false; // Retorna falso se o login falhar
    }
  }

  isFuncionario(perfil: string): boolean {
    // Simulação de validação (substitua pela sua lógica de back-end real)
    return perfil === 'funcionario';
  }
  
}
