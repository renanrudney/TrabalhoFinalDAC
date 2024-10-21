import { Component } from '@angular/core';
import { TransacaoMilhasService } from '../../../services/transacao-milhas.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-comprar-milhas',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './comprar-milhas.component.html',
  styleUrl: './comprar-milhas.component.scss'
})
export class ComprarMilhasComponent {
  quantidadeMilhas: number = 0;

  constructor(private transacaoMilhasService: TransacaoMilhasService, private router: Router, private authService: AuthService) {}

  comprarMilhas(quantidade: number): void{
    quantidade = this.quantidadeMilhas;
    if (quantidade > 0){
      const user: string | null = this.authService.getUserLogin()
      if (user)
        this.transacaoMilhasService.novaTransacao(user,quantidade,"COMPRA DE MILHAS");

      alert("Compra realizada!");
      this.router.navigate(['/home-cliente'])
    }
  }
}
