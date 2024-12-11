import { Component } from '@angular/core';
import { ReservaService } from '../../../services/reserva.service';
import { CommonModule } from '@angular/common';
import { Reserva } from '../../../models/reserva/reserva.model';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { VooService } from '../../../services/voo.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CancelarReservaComponent } from '../cancelar-reserva/cancelar-reserva.component';
import { Voo } from '../../../models/voo/voo.model';

@Component({
  selector: 'app-consultar-reserva',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './consultar-reserva.component.html',
  styleUrl: './consultar-reserva.component.scss'
})
export class ConsultarReservaComponent {
  codigoReserva: string = '';
  reserva: Reserva | null = null;
  voo: Voo | null = null;
  mensagemErro: string | null = null;

  constructor(private reservaService: ReservaService, private router: Router,private vooService: VooService, private modalService: NgbModal) {}

  consultarReserva(): void {
    this.reservaService.getReserva(this.codigoReserva.toUpperCase()).subscribe(
      (reserva) => {
        if (reserva) {
          this.reserva = reserva;
          this.vooService.getVoo(reserva.codigoVoo).subscribe(
            (voo) => {
              if (voo) {
                console.log('Voo encontrado:', voo);
                this.voo = voo;
              } else {
                console.warn('Voo não encontrado.');
                this.mensagemErro = 'Voo não encontrado!';
                this.voo = null;
              }
            },
            (error) => {
              console.error('Erro ao buscar o voo:', error);
              this.mensagemErro = 'Ocorreu um erro ao buscar o voo.';
              this.voo = null;
            }
          );
          this.mensagemErro = null; // Limpa a mensagem de erro se a reserva for encontrada
        } else {
          this.mensagemErro = 'Reserva não encontrada!';
          this.reserva = null; // Garante que a reserva não exiba dados antigos
        }
      },
      (error) => {
        console.error('Erro ao consultar a reserva:', error);
        this.mensagemErro = 'Ocorreu um erro ao consultar a reserva.';
        this.reserva = null; // Limpa qualquer dado antigo de reserva
      }
    );
  }

  fazerCheckin() {
    confirm("Voce será redirecionado ao Check In.");
    this.router.navigate(['/checkin']);
  }

  isReservadoEValido(reserva: Reserva): boolean {
    // Verifica se a reserva está 'RESERVADO'
    if (reserva.estado !== 'RESERVADO') {
        return false;
    }

    // Busca o voo correspondente à reserva
    const dataHora = this.voo?.data;
    
    // Verifica se o voo foi encontrado e se está dentro do intervalo de 48 horas
    if (dataHora) {
      const agora = new Date();
      const dataLimite = new Date(agora.getTime() + 48 * 60 * 60 * 1000); // 48 horas a partir de agora
      return dataHora > agora && dataHora <= dataLimite;
    }
    
    return false; // Se o voo não for encontrado, retorna false
  }

  abrirModalCancelarReserva(reserva: Reserva) {
    const modalRef = this.modalService.open(CancelarReservaComponent);
    modalRef.componentInstance.reserva = reserva;
    modalRef.componentInstance.voo = this.voo;
  }
}
