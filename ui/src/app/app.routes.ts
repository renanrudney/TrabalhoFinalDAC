import { Routes } from '@angular/router';
import { AutocadastroComponent } from './autocadastro/autocadastro.component';
<<<<<<< Updated upstream
//implements {AutocadastroComponent}
=======
import { InicialClienteComponent } from './pages/inicial-cliente/inicial-cliente.component';
import { ExtratoMilhasComponent } from './pages/cliente/extrato-milhas/extrato-milhas.component';
import { ComprarMilhasComponent } from './pages/cliente/comprar-milhas/comprar-milhas.component';
import { HomeClienteComponent } from './pages/cliente/home-cliente/home-cliente.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeFuncionarioComponent } from './pages/funcionario/home-funcionario/home-funcionario.component';
import { CrudFuncionarioComponent } from './pages/funcionario/crud-funcionario/crud-funcionario.component';
>>>>>>> Stashed changes

export const routes: Routes = [

    { path: 'autocadastro', component: AutocadastroComponent },
<<<<<<< Updated upstream
    { path: '', redirectTo: '/autocadastro', pathMatch: 'full' }
];
=======
    { path: 'inicial-cliente', component: InicialClienteComponent },
    { path: 'extrato-milhas', component: ExtratoMilhasComponent },
    { path: 'comprar-milhas', component: ComprarMilhasComponent },
    { path: 'home-cliente', component: HomeClienteComponent},
    { path: 'home-funcionario', component: HomeFuncionarioComponent},
    { path: 'crud-funcionario', component: CrudFuncionarioComponent},
];
>>>>>>> Stashed changes
