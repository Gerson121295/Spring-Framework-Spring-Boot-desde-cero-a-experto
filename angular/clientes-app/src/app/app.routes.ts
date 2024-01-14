

import { Routes } from '@angular/router';
import { DirectivaComponent } from './directiva/directiva.component';
import { ClientesComponent } from './clientes/clientes.component';

export const routes: Routes = [
    {path: '', redirectTo: '/clientes', pathMatch: 'full'}, //Path vacio que redirige a la ruta /clientes
    {path: 'directivas', component: DirectivaComponent},
    {path: 'clientes', component: ClientesComponent}
    
    
];
