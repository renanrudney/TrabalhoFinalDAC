import { Component } from '@angular/core';
import { TransacaoMilhasService } from '../../../services/transacao-milhas.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-comprar-milhas',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './comprar-milhas.component.html',
  styleUrl: './comprar-milhas.component.scss'
})
export class ComprarMilhasComponent {
  quantidadeMilhas: number = 0;

  constructor(private transacaoMilhasService: TransacaoMilhasService, private router: Router, private authService: AuthService) {}

  comprarMilhas(quantidade: number): void{
    quantidade = this.quantidadeMilhas;
    if (quantidade > 0){
      const clienteId: string | null = this.authService.getItem('clienteId');
      if (clienteId)
        this.transacaoMilhasService.novaTransacao(clienteId,quantidade,"COMPRA DE MILHAS").subscribe({
          next: (transacao) => {
            console.log('Compra realizada com sucesso:', transacao);
            alert('Compra realizada com sucesso.');
            this.router.navigate(['/home-cliente'])
          },
          error: (err: any) => {
            console.error('Erro ao comprar  milhas:', err);
            alert('Ocorreu um erro ao tentar comprar milhas. Por favor, tente novamente.');
          }
        }
      );
      
      this.router.navigate(['/home-cliente'])
    }
  }
}