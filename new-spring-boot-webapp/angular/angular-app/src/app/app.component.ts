import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { ProductComponent } from './products/components/product/product.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, ProductComponent], //se importa el componente: ProductComponent
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title: string = 'Hola mundo angular 17';
  enabled:boolean = false;

  courses: string[] = ['Angular', 'React', 'Spring Boot']
  
  //Al llamar el metodo setEnabled cambia el estado de enabled a true
  setEnabled(): void{
    this.enabled= this.enabled ? false : true; // Si enable es true cambia a false sino a true
    console.log('Hemos hecho clic en setEnabled');
  }

}
