import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

export const loginGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    const userTipo = authService.getUserType(); // 'cliente' ou 'funcionario'

    // Evita redirecionamento desnecessário ao acessar a rota de login
    if (state.url === '/login' || state.url === '/autocadastro') {
      if (userTipo === 'cliente') {
        router.navigate(['/home-cliente']);
      } else if (userTipo === 'funcionario') {
        router.navigate(['/home-funcionario']);
      }
      return false;
    }

    return true;
  }
  
  return true; // Permite acesso se o usuário não estiver autenticado
};
