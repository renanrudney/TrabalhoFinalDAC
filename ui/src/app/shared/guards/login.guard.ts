import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

export const loginGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    const userTipo = authService.getUserType(); // 'cliente' ou 'funcionario'
    
    // Redireciona conforme o tipo de usuário
    if (userTipo === 'cliente') {
      router.navigate(['/inicial-cliente']);
    } else if (userTipo === 'funcionario') {
      router.navigate(['/home-funcionario']);
    }

    return false; // Impede o acesso à página de login se já autenticado
  }
  
  return true; // Permite acesso à página de login se não autenticado
};
