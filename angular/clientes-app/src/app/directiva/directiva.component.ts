import { CommonModule } from '@angular/common';
import { Component, NgModule } from '@angular/core';

@Component({
  selector: 'app-directiva',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './directiva.component.html'
})
export class DirectivaComponent {
  listaCurso: string[] = ['TypeScript', 'JavaScript', 'Java SE', 'C#', 'php'];
  habilitar: boolean = true;
  
  constructor(){
  }

  //Metodo para mostrar los cursos
  setHabilitar(): void{
    this.habilitar = (this.habilitar==true) ? false: true;
  }

}
