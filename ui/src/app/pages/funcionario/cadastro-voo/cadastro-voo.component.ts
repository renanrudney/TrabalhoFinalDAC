import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { VooService } from '../../../services/voo.service';
import { Aeroporto } from '../../../models/aeroporto/aeroporto.model';
import { AeroportoService } from '../../../services/aeroporto.service';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';

@Component({
  selector: 'app-cadastro-voo',
  standalone: true,
  imports: [CommonModule, FormsModule,NgxMaskDirective],
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
  errorMessage: string = "";

  constructor(private router: Router, private vooService: VooService, private aeroportoService: AeroportoService) {}

  ngOnInit(): void {
    this.aeroportoService.getAeroportos().subscribe(
      (aeroportos) => {
        this.aeroportos = aeroportos;
        console.log('Aeroportos carregados:', aeroportos);
      },
      (error) => {
        console.error('Erro ao carregar aeroportos:', error);
        this.errorMessage = 'Erro ao carregar aeroportos, tente novamente';
      }
    );
  }

  calcularMilhas() {
    // Exemplo de cálculo simples de milhas: 1 milha a cada R$5,00
    this.equivalenteMilhas = this.valorPassagem / 5;
  }

  cadastrarVoo() {
    // Aqui vai a lógica para salvar o voo no sistema (ex: chamada ao backend)
    this.vooService.cadastrarVoo(this.codigoVoo, new Date(this.dataHora), this.origem, this.destino, this.valorPassagem, this.totalPoltronas).subscribe(
      (vooCadastrado) => {
        // Sucesso: Exibe mensagem e redireciona
        alert(`Novo voo ${vooCadastrado.cod} cadastrado com sucesso!`);
        this.router.navigate(['/home-funcionario']);
      },
      (error) => {
        // Erro: Exibe mensagem apropriada
        console.error('Erro ao cadastrar voo:', error);
        alert('Ocorreu um erro ao cadastrar o voo. Tente novamente.');
      }
    );
  }
}