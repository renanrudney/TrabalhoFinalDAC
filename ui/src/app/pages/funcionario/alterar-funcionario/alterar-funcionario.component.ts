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
  id: number = 0;
  errorMessage: string = "";

  constructor(
    private funcionarioService: FuncionarioService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
  
    if (!id || isNaN(id)) {
      console.error('ID inválido.');
      this.errorMessage = 'ID do funcionário inválido. Verifique a URL e tente novamente.';
      return;
    }
  
    this.funcionarioService.getFuncionarioById(id).subscribe(
      (funcionario) => {
        if (funcionario) {
          this.funcionario = funcionario;
        } else {
          console.warn(`Funcionário com ID ${id} não encontrado.`);
          this.errorMessage = `Funcionário com ID ${id} não encontrado.`;
        }
      },
      (error) => {
        console.error('Erro ao buscar o funcionário:', error);
        this.errorMessage = 'Erro ao carregar os dados do funcionário. Tente novamente mais tarde.';
      }
    );
  }
  

  onSubmit(form: NgForm): void {
    if (form.touched && form.valid) {
      this.funcionarioService.alterarFuncionario(this.funcionario).subscribe(
        (funcionarioAtualizado) => {
          // Sucesso: exibe mensagem de confirmação
          console.log("Funcionário Alterado com sucesso!", funcionarioAtualizado);
          alert(`Funcionário ${funcionarioAtualizado.nome} alterado com sucesso!`);
          window.history.back(); // Volta à página anterior
        },
        (error) => {
          // Erro: exibe mensagem apropriada
          console.error('Erro ao alterar o funcionário:', error);
          alert('Ocorreu um erro ao alterar o funcionário. Tente novamente.');
        }
      );
    } else {
      alert("Nenhum dado foi alterado ou o formulário é inválido.");
    }
  }
  
}
