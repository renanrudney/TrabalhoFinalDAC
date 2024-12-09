import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  logar(token: string, userId: number, userType: string): void {
    // Armazena o token e os dados do usuário no localStorage
    localStorage.setItem('authToken', token);
    localStorage.setItem('userId', userId.toString());
    localStorage.setItem('userType', userType);
  
    console.log('Dados armazenados no localStorage:');
    console.log(`Token: ${token}, UserId: ${userId}, UserType: ${userType}`);
  }

  logout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userId');
    localStorage.removeItem('userType');
  }  

  isAuthenticated(): boolean {
    // Verifica se `localStorage` está disponível
    if (!this.isLocalStorageAvailable()) {
      return false;
    }
    // Verifique se o usuário está autenticado
    return !!localStorage.getItem('authToken');
  }

  getUserType(): string | null {
    // Retorna o tipo de usuário (por exemplo, 'cliente' ou 'funcionario')
    if (!this.isLocalStorageAvailable()) {
      return null;
    }
    return localStorage.getItem('userType');
  }

  getItem(key: string): string | null {
    if (!this.isLocalStorageAvailable()) {
      return null;
    }
    return localStorage.getItem(key);
  }

  private isLocalStorageAvailable(): boolean {
    return typeof localStorage !== 'undefined';
  }

}
