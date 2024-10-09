import { RouterModule, Routes } from '@angular/router';
import { AutocadastroComponent } from './autocadastro/autocadastro.component';
<<<<<<< HEAD
//implements {AutocadastroComponent}
import { InicialClienteComponent } from './pages/inicial-cliente';
=======
import { ExtratoMilhasComponent } from './pages/cliente/extrato-milhas/extrato-milhas.component';
import { ComprarMilhasComponent } from './pages/cliente/comprar-milhas/comprar-milhas.component';
import { HomeClienteComponent } from './pages/cliente/home-cliente/home-cliente.component';
import { NgModule } from '@angular/core';
>>>>>>> luizsenji

export const routes: Routes = [

    { path: 'autocadastro', component: AutocadastroComponent },
<<<<<<< HEAD
    { path: 'inicial-cliente', component: InicialClienteComponent },
    { path: '', redirectTo: '/autocadastro', pathMatch: 'full' }
=======
    { path: 'extrato-milhas', component: ExtratoMilhasComponent },
    { path: 'comprar-milhas', component: ComprarMilhasComponent },
    { path: 'home-cliente', component: HomeClienteComponent}
    //{ path: '', redirectTo: '/autocadastro', pathMatch: 'full' }
>>>>>>> luizsenji
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
export class AppRoutingModule {}