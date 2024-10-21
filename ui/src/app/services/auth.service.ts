import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario/usuario.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) { }

  logar(usuario: Usuario): void {
    const token = `${usuario.login}-${usuario.senha}-${usuario.tipo}-token`;
    localStorage.setItem('authToken', token);
    localStorage.setItem('userType', usuario.tipo);
    localStorage.setItem('userLogin', usuario.login);
  }

  logout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userType');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  }

  getUserType(): string | null{
    return localStorage.getItem('userType');
  }

  getUserLogin(): string | null{
    return localStorage.getItem('userLogin');
  }
}
