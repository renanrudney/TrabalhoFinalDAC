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
      this.http.post<void>(`${this.baseUrl}/logout`, {}).subscribe(
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
}
