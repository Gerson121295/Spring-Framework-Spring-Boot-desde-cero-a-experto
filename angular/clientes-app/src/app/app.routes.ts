

import { Routes } from '@angular/router';
import { DirectivaComponent } from './directiva/directiva.component';
import { ClientesComponent } from './clientes/clientes.component';
import { FormComponent } from './clientes/form.component';

export const routes: Routes = [
    {path: '', redirectTo: '/clientes', pathMatch: 'full'}, //Path vacio que redirige a la ruta /clientes
    {path: 'directivas', component: DirectivaComponent},
    {path: 'clientes', component: ClientesComponent}, //Muestra todos los clientes
    {path: 'clientes/form', component: FormComponent}, //Abre formulario para crear cliente
    {path: 'clientes/form/:id', component: FormComponent} //buscar cliente por id
    
    
];
