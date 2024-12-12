import { CanActivateFn } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const clienteGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated() && authService.getUserType() === 'CLIENTE') {
    return true; // Permite acesso
  } else if (authService.getUserType() === 'CLIENTE') {
    router.navigate(['/home-funcionario']);
    return true;
  } else {
    router.navigate(['/login']); // Redireciona para a página de login
    return false; // Bloqueia o acesso
  }
};
