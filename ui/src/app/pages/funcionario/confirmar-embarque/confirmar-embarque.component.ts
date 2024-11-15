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

  ConfirmarEmbarque(reserva: string) {
    if (this.codigoVoo) {
      const mensagem: string = this.reservaService.embarque(reserva, this.codigoVoo);
      alert(mensagem);    
      this.reserva = "";
    }
  }
}