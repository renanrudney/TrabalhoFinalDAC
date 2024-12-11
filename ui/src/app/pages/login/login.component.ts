import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router,RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  login: string = '';
  senha: string = '';
  loginFailed: boolean = false;

  constructor(private userService: UserService, private router: Router) {}

  logar() {
    this.userService.login(this.login, this.senha).subscribe(
      (tipo) => {
        if (tipo === 'CLIENTE') {
          // Redireciona para a página inicial do cliente
          this.router.navigate(['/home-cliente']);
        } else if (tipo === 'FUNCIONARIO') {
          // Redireciona para a página inicial do funcionário
          this.router.navigate(['/home-funcionario']);
        } else {
          // Tipo inesperado (apenas para segurança)
          console.error('Tipo de usuário desconhecido:', tipo);
          this.loginFailed = true;
        }
      },
      (error) => {
        console.error('Erro no login:', error);
        this.loginFailed = true; // Exibe mensagem de erro
      }
    );
  }
  
}
