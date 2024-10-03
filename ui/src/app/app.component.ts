import { Component } from '@angular/core';
import { InicialClienteComponent } from './pages/inicial-cliente';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [InicialClienteComponent],
  template: '<app-tela-inicial-cliente></app-tela-inicial-cliente>',
  styleUrls: ['./app.component.css']
})
export class AppComponent {}
