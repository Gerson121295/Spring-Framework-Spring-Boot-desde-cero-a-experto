import { Component } from '@angular/core';
import { HeaderComponent } from "./header/header.component";
import { FooterComponent } from './footer/footer.component';
import { DirectivaComponent } from "./directiva/directiva.component";
import { ClientesComponent } from "./clientes/clientes.component";
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';



@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
    imports: [HeaderComponent, FooterComponent, DirectivaComponent, 
      ClientesComponent, CommonModule, RouterModule]
})
export class AppComponent {
  title = 'Bienvenido Angular';
  curso = 'Curso Spring 6 con Angular 17';
  alumno: string = 'Gerson Escobedo';
}
