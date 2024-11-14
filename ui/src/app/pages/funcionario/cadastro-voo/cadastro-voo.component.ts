import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { VooService } from '../../../services/voo.service';
import { Aeroporto } from '../../../models/aeroporto/aeroporto.model';
import { AeroportoService } from '../../../services/aeroporto.service';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-cadastro-voo',
  standalone: true,
  imports: [CommonModule, FormsModule,NgxMaskDirective,HttpClientModule],
  providers: [provideNgxMask()],
  templateUrl: './cadastro-voo.component.html',
  styleUrls: ['./cadastro-voo.component.scss']
})
export class CadastroVooComponent implements OnInit{
  codigoVoo: string = "";
  dataHora: string = new Date().toISOString().slice(0, 16);;;
  origem: string = "";
  destino: string = "";
  valorPassagem: number = 0;
  totalPoltronas: number = 0;
  poltronasOcupadas: number = 0;
  equivalenteMilhas: number = 0;
  aeroportos: Aeroporto[] = [];

  constructor(private router: Router, private vooService: VooService, private aeroportoService: AeroportoService) {}

  ngOnInit(): void {
    this.aeroportos = this.aeroportoService.getAeroportos();
  }

  calcularMilhas() {
    // Exemplo de cálculo simples de milhas: 1 milha a cada R$5,00
    this.equivalenteMilhas = this.valorPassagem / 5;
  }

  cadastrarVoo() {
    // Aqui vai a lógica para salvar o voo no sistema (ex: chamada ao backend)
    this.vooService.cadastrarVoo(this.codigoVoo, new Date(this.dataHora), this.origem, this.destino, this.valorPassagem, this.totalPoltronas);
    alert(`Novo voo ${this.codigoVoo} casdastrado!`);
    // Exemplo de redirecionamento ou lógica pós-cadastro
    this.router.navigate(['/home-funcionario']);
  }
}