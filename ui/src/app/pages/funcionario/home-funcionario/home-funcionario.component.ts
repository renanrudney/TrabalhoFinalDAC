import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Voo } from '../../../models/voo/voo.model';
import { VooService } from '../../../services/voo.service';
import { Router } from '@angular/router';
import { CancelarReservaComponent } from '../../cliente/cancelar-reserva/cancelar-reserva.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

interface Reserva {
  id: number;
  dataHora: Date;
  aeroportoOrigem: string;
  aeroportoDestino: string;
}

@Component({
  selector: 'app-home-funcionario',
  standalone: true,
  imports: [CommonModule,FormsModule, RouterModule],
  templateUrl: './home-funcionario.component.html',
  styleUrl: './home-funcionario.component.scss'
})

export class HomeFuncionarioComponent implements OnInit {
  
  voos: Voo[] = [];

  constructor (private vooService: VooService, private router: Router, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.voos = this.vooService.getVoosProximasHoras();
    //Ordenar por dataHora
    this.voos = this.voos.filter(voo => voo.estado === 'CONFIRMADO');
    this.voos.sort((a, b) => new Date(a.dataHora).getTime() - new Date(b.dataHora).getTime());
  }
  
  confirmarEmbarque(codigoVoo: string): void {
    this.router.navigate(['/confirmar-embarque', codigoVoo]);
  }

  abrirModalCancelarVoo(voo: Voo): void {
    const modalRef = this.modalService.open(CancelarReservaComponent);
    modalRef.componentInstance.voo = voo;
  }

  realizarVoo(codigoVoo: string): void {
    this.vooService.realizarVoo(codigoVoo);
    alert(`Voo ${codigoVoo} foi realizado! Atualizado o estado do voo e das reservas.`);
    location.reload(); // Recarrega a página
  }
}