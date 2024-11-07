import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Funcionario } from '../../../models/funcionario/funcionario.model';
import { FuncionarioService } from '../../../services/funcionario.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-alterar-funcionario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './alterar-funcionario.component.html',
  styleUrl: './alterar-funcionario.component.scss'
})
export class AlterarFuncionarioComponent implements OnInit {
  
  funcionario!: Funcionario;

  cpf: string = "";
  nome: string = "";
  email: string = "";
  telefone: string = "";

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
          this.preencherFormulario();
        }
      });
    }
  }

  preencherFormulario(): void {
    if (this.funcionario) {
      this.nome = this.funcionario.nome;
      this.email = this.funcionario.email;
      this.cpf = this.funcionario.cpf;
      this.telefone = this.funcionario.telefone;
    }
  }

  onSubmit(form: NgForm) {
    if (form.touched && form.valid) {
      const funcionarioAlterado: Funcionario = {
        cpf: this.funcionario.cpf,
        nome: this.nome,
        email: this.email,
        telefone: this.telefone
      };

      this.funcionarioService.alterarFuncionario(funcionarioAlterado);
      console.log("Funcionário Alterado com sucesso!");
      window.history.back();
    } else {
      alert("Nenhum dado foi alterado");
    }
  }
}
