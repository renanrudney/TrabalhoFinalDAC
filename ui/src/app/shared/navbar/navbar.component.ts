import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule,RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit{
  
  userTipo: string | null = null;

  constructor (private authService: AuthService, private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.getUserTipo();
  }

  getUserTipo(): void {
    this.userTipo = this.authService.getItem('userType');
  }

  logout() {
    this.userService.logout().subscribe(
      () => {
        this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Erro ao deslogar:', error);
      }
    );
  }
}
