import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule,RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit{
  
  userTipo: string | null = null;

  constructor (private authService: AuthService) {}

  ngOnInit(): void {
    this.getUserTipo();
  }

  getUserTipo(): void {
    this.userTipo = this.authService.getItem('userType');
  }

  logout() {
    this.authService.logout();
  }
}
