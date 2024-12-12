import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { FuncionarioService } from '../../../services/funcionario.service';
import { Funcionario } from '../../../models/funcionario/funcionario.model';
import { RemoverFuncionarioComponent } from '../remover-funcionario/remover-funcionario.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-crud-funcionario',
  standalone: true,
  imports: [CommonModule,FormsModule, RouterModule],
  templateUrl: './crud-funcionario.component.html',
  styleUrl: './crud-funcionario.component.scss'
})

export class CrudFuncionarioComponent implements OnInit{
  funcionarios: Funcionario[] = [];
  
  constructor(private router: Router, private funcionarioService: FuncionarioService, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.funcionarioService.getFuncionarios().subscribe(
      (data) => {
        console.log(data)
        this.funcionarios = data.filter((data: Funcionario) => data.ativo == true); // Armazena os funcionários retornados no array
      },
      (error) => {
        console.error('Erro ao carregar funcionários:', error);
      }
    );
  }

  alterarFuncionario(id: string | undefined): void {
    this.router.navigate(['/alterar-funcionario', id]);
  }

  AbrirModalRemoverFuncionario(funcionario: Funcionario): void {
    const modalRef = this.modalService.open(RemoverFuncionarioComponent);
    modalRef.componentInstance.funcionario = funcionario;
  }
}