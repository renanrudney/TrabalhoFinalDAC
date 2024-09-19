import { Routes } from '@angular/router';
import { AutocadastroComponent } from './autocadastro/autocadastro.component';
//implements {AutocadastroComponent}

export const routes: Routes = [

    { path: 'autocadastro', component: AutocadastroComponent },
    { path: '', redirectTo: '/autocadastro', pathMatch: 'full' }
];
