import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  constructor (private authService: AuthService) {}

  getUserTipo(): string | null {
    return this.authService.getUserType();
  }

  logout() {
    this.authService.logout();
  }
}
