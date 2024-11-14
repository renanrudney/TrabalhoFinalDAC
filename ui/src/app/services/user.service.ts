import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario/usuario.model';
import { AuthService } from './auth.service';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  Usuarios: Array<Usuario> = [
    new Usuario('a@email.com','1234','cliente',1),
    new Usuario('b@email.com','5678','funcionario',2)
  ];

  constructor(private authService: AuthService) { }

  login(username: string, password: string): Observable<string> {
    return new Observable<string>(observer => {
      this.getUsuario(username, password).subscribe(usuario => {
        if (usuario) {
          this.authService.logar(usuario);
          console.log('Login bem-sucedido');
          observer.next(usuario.tipo); // Emite o tipo de usuário no caso de sucesso
        } else {
          console.log('Falha no login: usuário ou senha inválidos.');
          observer.next(""); // Emite uma string vazia no caso de falha
        }
        observer.complete(); // Completa o Observable
      });
    });
  }

  getUsuario(username: string, password: string): Observable<Usuario | undefined> {
    const usuario: Usuario | undefined = this.Usuarios.find(user => user.login === username && user.senha === password);
    return of(usuario);
  }

  criarUsuarioCliente(login: string): void {
    const senha: string = this.generateRandomPassword();
    const user: Usuario = new Usuario(login,senha,"cliente",undefined);
    this.Usuarios.push(user);
    //Enviar Senha por email
  }

  criarUsuarioFuncionario(login: string): void {
    const senha: string = this.generateRandomPassword();
    const user: Usuario = new Usuario(login,senha,"funcionario",undefined);
    this.Usuarios.push(user);
    //Enviar Senha por email
  }

  generateRandomPassword(): string {
    let password = '';
    for (let i = 0; i < 4; i++) {
        const randomDigit = Math.floor(Math.random() * 10); // Gera um número entre 0 e 9
        password += randomDigit.toString(); // Adiciona o dígito à senha
    }
    return password;
  }
  
}
