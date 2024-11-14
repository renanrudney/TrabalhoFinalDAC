import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Funcionario } from '../../../models/funcionario/funcionario.model';
import { FuncionarioService } from '../../../services/funcionario.service';
import { ActivatedRoute } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-alterar-funcionario',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './alterar-funcionario.component.html',
  styleUrl: './alterar-funcionario.component.scss'
})
export class AlterarFuncionarioComponent implements OnInit {

  funcionario!: Funcionario;
  cpf: string = "";

  constructor(
    private funcionarioService: FuncionarioService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const cpfFuncionario = this.route.snapshot.paramMap.get('cpf');
    if (cpfFuncionario) {
      this.funcionarioService.getFuncionarioByCpf(cpfFuncionario).subscribe((funcionario) => {
        if (funcionario) {
          this.funcionario = funcionario;
        }
      });
    }
  }

  onSubmit(form: NgForm) {
    if (form.touched && form.valid) {
      this.funcionarioService.alterarFuncionario(this.funcionario);
      console.log("Funcionário Alterado com sucesso!");
       window.history.back();
    } else {
      alert("Nenhum dado foi alterado");
    }
  }
}
