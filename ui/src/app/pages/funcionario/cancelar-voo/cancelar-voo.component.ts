import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Voo } from '../../../models/voo/voo.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { VooService } from '../../../services/voo.service';

@Component({
  selector: 'app-cancelar-voo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cancelar-voo.component.html',
  styleUrl: './cancelar-voo.component.scss'
})
export class CancelarVooComponent {
  @Input() voo!: Voo;

  constructor(public activeModal: NgbActiveModal, private vooService: VooService) {}

  cancelarVoo(codigoVoo: string) {
    this.vooService.cancelarVoo(codigoVoo);
    this.activeModal.close()
  }
}