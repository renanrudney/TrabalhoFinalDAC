import { Component } from '@angular/core';
import { TransacaoMilhasService } from '../../../services/transacao-milhas.service';
import { TransacaoMilhas } from '../../../models/transacaoMilhas/transacao-milhas.model';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-comprar-milhas',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './comprar-milhas.component.html',
  styleUrl: './comprar-milhas.component.scss'
})
export class ComprarMilhasComponent {
  quantidadeMilhas: number = 0;

  constructor(
    private transacaoMilhasService: TransacaoMilhasService,
    private router: Router
  ) {}

  comprarMilhas(quantidade: number): void{
    quantidade = this.quantidadeMilhas;
    if (quantidade > 0){
      let compra = new TransacaoMilhas ('00000000004', new Date(), quantidade, 'entrada', 'COMPRA DE MILHAS');
      this.transacaoMilhasService.pushTransacao(compra);
      alert("Compra realizada!");
      this.router.navigate(['/home-cliente'])
    }
  }
}
