import { Routes } from '@angular/router';
import { AutocadastroComponent } from './autocadastro/autocadastro.component';
//implements {AutocadastroComponent}
import { InicialClienteComponent } from './pages/inicial-cliente';

export const routes: Routes = [

    { path: 'autocadastro', component: AutocadastroComponent },
    { path: 'inicial-cliente', component: InicialClienteComponent },
    { path: '', redirectTo: '/autocadastro', pathMatch: 'full' }
];
