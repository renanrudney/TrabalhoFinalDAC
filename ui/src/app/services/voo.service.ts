import { Injectable } from '@angular/core';
import { Voo } from '../models/voo/voo.model';
import { Aeroporto } from '../models/aeroporto/aeroporto.model';
import { ReservaService } from './reserva.service';

@Injectable({
  providedIn: 'root'
})
export class VooService {

  Voos: Array<Voo> = [
    // Voos que já ocorreram
    new Voo(
      'TADS0001',
      new Date('2024-09-15T10:00:00'),
      'GRU',
      'GIG',
      450.00,
      150,
      150
    ),
    new Voo(
      'TADS0002',
      new Date('2024-10-01T16:30:00'),
      'CWB',
      'BSB',
      550.00,
      100,
      95
    ),
    new Voo(
      'TADS0003',
      new Date('2024-10-10T08:00:00'),
      'POA',
      'SSA',
      600.00,
      180,
      180
    ),
  
    // Voos que irão ocorrer
    new Voo(
      'TADS0004',
      new Date('2024-10-25T14:00:00'),
      'BSB',
      'CWB',
      500.00,
      120,
      50
    ),
    new Voo(
      'TADS0005',
      new Date('2024-11-02T19:30:00'),
      'GIG',
      'GRU',
      400.00,
      200,
      120
    ),
    new Voo(
      'TADS0006',
      new Date('2024-11-10T09:00:00'),
      'CWB',
      'FOR',
      750.00,
      150,
      75
    ),
    new Voo(
      'TADS0007',                 // Código do Voo
      (new Date('2024-11-31T22:00:00')),                    // Data/Hora do Voo
      'CWB',      // Origem
      'GRU',     // Destino
      500,                        // Valor da Passagem
      100,                        // Total de Poltronas
      0                           // Poltronas Ocupadas
    )
  ];

  constructor(private reservaService: ReservaService) { }

  getVoos(): Array<Voo> {
    return this.Voos;
  }

  getVoosCompra(origem: string, destino: string): Array<Voo> {
    const dataAtual = new Date();
    return this.Voos.filter(voo => 
      voo.origem === origem && 
      voo.destino === destino && 
      voo.dataHora > dataAtual
    );
  }

  getVoosProximasHoras(): Array<Voo> {
    const agora = new Date();
    const dataLimite = new Date(agora.getTime() + 48 * 60 * 60 * 1000);
    return this.Voos.filter(voo => {
      return voo.dataHora > agora && voo.dataHora <= dataLimite;
    });
}

  getOrigem(codigoVoo: string): string | undefined{
    const vooEncontrado = this.Voos.find(voo => voo.codigoVoo === codigoVoo);
    return vooEncontrado?.origem;
  }

  getDestino(codigoVoo: string): string | undefined{
    const vooEncontrado = this.Voos.find(voo => voo.codigoVoo === codigoVoo);
    return vooEncontrado?.destino;
  }

  getVooDataHora(codigoVoo: string): Date | undefined {
    const vooEncontrado = this.Voos.find(voo => voo.codigoVoo === codigoVoo);
    return vooEncontrado ? vooEncontrado.dataHora : undefined;
  }

  cancelarVoo(codigoVoo: string): void {
    const vooEncontrado: Voo | undefined = this.Voos.find(voo => voo.codigoVoo === codigoVoo);
    if (vooEncontrado) {
      vooEncontrado.estado = "CANCELADO";
      this.reservaService.vooCancelado(vooEncontrado.codigoVoo);
    }
  }

  realizarVoo(codigoVoo: string): void {
    const vooEncontrado: Voo | undefined = this.Voos.find(voo => voo.codigoVoo === codigoVoo);
    if (vooEncontrado?.estado === "CONFIRMADO") {
      vooEncontrado.estado = "REALIZADO";
      this.reservaService.vooRealizado(vooEncontrado.codigoVoo);
    }
  }

  cadastrarVoo(codigoVoo: string, dataHora: Date, origem: string, destino: string, valorPassagem: number, totalPoltronas: number): void {
    const novoVoo: Voo = new Voo (codigoVoo, dataHora, origem, destino, valorPassagem, totalPoltronas, 0, "CONFIRMADO");
    this.Voos.push(novoVoo);
  }
}
