import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Voo } from '../../../models/voo/voo.model';
import { VooService } from '../../../services/voo.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CancelarVooComponent } from '../cancelar-voo/cancelar-voo.component';

@Component({
  selector: 'app-home-funcionario',
  standalone: true,
  imports: [CommonModule,FormsModule, RouterModule],
  templateUrl: './home-funcionario.component.html',
  styleUrl: './home-funcionario.component.scss'
})

export class HomeFuncionarioComponent implements OnInit {
  
  voos: Voo[] = [];
  mensagemErro: string = "";

  constructor (private vooService: VooService, private router: Router, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.vooService.getVoosProximasHoras().subscribe(
      (voos) => {
        if (voos.length > 0) {
          this.voos = voos; // Atualiza a lista de voos no componente

          //Ordenar por dataHora
          this.voos = this.voos.filter(voo => voo.estado === 'CONFIRMADO');
          this.voos.sort((a, b) => new Date(a.data).getTime() - new Date(b.data).getTime());
        } else {
          this.mensagemErro = 'Nenhum voo encontrado nas próximas 48 horas.';
        }
      },
      (error) => {
        console.error('Erro ao buscar voos próximos:', error);
        this.mensagemErro = 'Ocorreu um erro ao buscar voos. Tente novamente.';
      }
    );
  }
  
  confirmarEmbarque(codigoVoo: string): void {
    this.router.navigate(['/confirmar-embarque', codigoVoo]);
  }

  abrirModalCancelarVoo(voo: Voo): void {
    const modalRef = this.modalService.open(CancelarVooComponent);
    modalRef.componentInstance.voo = voo;
  }

  realizarVoo(codigoVoo: string): void {
    this.vooService.realizarVoo(codigoVoo).subscribe(
      () => {
        // Sucesso: exibe mensagem e recarrega a página
        alert(`Voo ${codigoVoo} foi realizado! Atualizado o estado do voo e das reservas.`);
        location.reload();
      },
      (error) => {
        // Erro: exibe mensagem apropriada
        console.error('Erro ao realizar o voo:', error);
        alert(`Ocorreu um erro ao realizar o voo ${codigoVoo}. Tente novamente.`);
      }
    );
  }  
}