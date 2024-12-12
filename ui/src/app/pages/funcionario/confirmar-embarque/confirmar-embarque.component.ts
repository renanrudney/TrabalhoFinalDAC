import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { ReservaService } from '../../../services/reserva.service';

@Component({
  selector: 'app-confirmar-embarque',
  standalone: true,
  imports: [CommonModule,FormsModule, RouterModule],
  templateUrl: './confirmar-embarque.component.html',
  styleUrl: './confirmar-embarque.component.scss'
})

export class ConfirmarEmbarqueComponent implements OnInit{
  
  codigoVoo: string | null = null;
  reserva: string = "";

  constructor(private route: ActivatedRoute, private reservaService: ReservaService) {}

  ngOnInit(): void {
    this.codigoVoo = this.route.snapshot.paramMap.get('codigoVoo');
  }

  ConfirmarEmbarque(reserva: string): void {
    if (this.codigoVoo) {
      this.reservaService.embarque(reserva, this.codigoVoo).subscribe(
        (reservaAtualizada) => {
          // Sucesso: Exibe mensagem de confirmação
          alert(`Embarque confirmado para a reserva: ${reservaAtualizada.cod}.`);
          this.reserva = ""; // Limpa o campo de entrada da reserva
          location.reload(); // Recarrega a página para atualizar os dados
        },
        (error) => {
          // Erro: Exibe mensagem apropriada
          console.error('Erro ao confirmar embarque:', error);
          alert('Ocorreu um erro ao confirmar o embarque. Tente novamente.');
        }
      );
    }
  }
}