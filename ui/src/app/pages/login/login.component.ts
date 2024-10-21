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
    if (this.userService.login(this.login, this.senha)) {
      // Redireciona para a página inicial ou outra página se o login for bem-sucedido
      this.router.navigate(['/home-cliente']);
    } else {
      this.loginFailed = true; // Exibir mensagem de erro
    }
  }
}
