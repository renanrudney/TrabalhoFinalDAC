import { Routes } from '@angular/router';
import { AutocadastroComponent } from './autocadastro/autocadastro.component';
import { ExtratoMilhasComponent } from './pages/cliente/extrato-milhas/extrato-milhas.component';
//implements {AutocadastroComponent}

export const routes: Routes = [

    { path: 'autocadastro', component: AutocadastroComponent },
    { path: 'cliente/extrato-milhas', component: ExtratoMilhasComponent}
    //{ path: '', redirectTo: '/autocadastro', pathMatch: 'full' }
];
