import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario/usuario.model';
import { AuthService } from './auth.service';
import { Observable, of } from 'rxjs';
import { environment } from '../shared/environment/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  Usuarios: Array<Usuario> = [
    new Usuario('a@email.com','1234','cliente',1),
    new Usuario('b@email.com','5678','funcionario',2)
  ];

  private baseUrl = `${environment.apiGatewayUrl}`;

  constructor(private authService: AuthService, private http: HttpClient) { }

  login(username: string, password: string): Observable<string> { // Retorna o tipo do usuário como string
    return new Observable<string>((observer) => {
      this.http
        .post<{ auth: boolean; token: string; data: { id: number; tipo: string } }>(
          `${this.baseUrl}/login`,
          { username, password }
        )
        .subscribe(
          (response) => {
            if (response.auth) {
              const { token, data } = response;
              const { id, tipo } = data;
  
              // Armazena os dados no AuthService
              this.authService.logar(token, id, tipo);
  
              console.log('Login bem-sucedido');
              observer.next(tipo); // Emite o tipo do usuário no caso de sucesso
              observer.complete();
            } else {
              console.error('Falha no login: autenticação inválida.');
              observer.error('Autenticação inválida.');
            }
          },
          (error) => {
            console.error('Falha no login', error);
            observer.error(error);
          }
        );
    });
  }
  
  logout(): Observable<void> {
    return new Observable<void>((observer) => {
      this.http.post<void>(this.baseUrl, {}).subscribe(
        () => {
          // Limpa o localStorage no AuthService após a chamada bem-sucedida
          this.authService.logout();
          console.log('Logout realizado com sucesso.');
          observer.next();
          observer.complete();
        },
        (error) => {
          console.error('Erro ao realizar logout', error);
          observer.error(error);
        }
      );
    });
  }

  //tudo abaixo vai ser feito no back
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
