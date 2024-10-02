import { Routes } from '@angular/router';
import { AutocadastroComponent } from './autocadastro/autocadastro.component';
import { ExtratoMilhasComponent } from './pages/cliente/extrato-milhas/extrato-milhas.component';
import { ComprarMilhasComponent } from './pages/cliente/comprar-milhas/comprar-milhas.component';

export const routes: Routes = [

    { path: 'autocadastro', component: AutocadastroComponent },
    { path: 'cliente/extrato-milhas', component: ExtratoMilhasComponent },
    { path: 'cliente/comprar-milhas', component: ComprarMilhasComponent }
    //{ path: '', redirectTo: '/autocadastro', pathMatch: 'full' }
];
