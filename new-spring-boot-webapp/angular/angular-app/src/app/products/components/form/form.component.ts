import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Product } from '../../models/product';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'product-form',
  standalone: true,
  imports: [FormsModule, CommonModule], //se importa FormsModule para poder usarlo en form.component.html en el formulario para mapear los campos de los atributos con los datos de la clase Product
  templateUrl: './form.component.html',
  styleUrl: './form.component.css'
})
export class FormComponent {

  //Valores a mostrar por defecto en el formulario
  //Input sirve para poblar los datos cuando se va a actualizar: esta funcion esta definida en product.component.html [product]="productSelected"
 @Input() product: Product = {
    id: 0, 
    name:'', 
    description:'', 
    price:0
  };

  @Output() newProductEvent = new EventEmitter(); //transferir una informacion al padre.


  onSubmit(): void{
    this.newProductEvent.emit(this.product); //recibe el objeto y se le envia al padre ahora en product.component.html el padre lo recibe por lo que se agrega (newProductEvent)="addProduct($event) la funcion que llama un metodo descrito en el padre product.component.ts
    console.log(this.product);
  }

  //limpia el formulario
  clean(): void{
    //this.product = new Product(); //forma 1: limpia el formulario crea una nueva instancia de producto con datos vacios.
    //Forma 2: limpiar el formulario asignandole datos vacios
    this.product={
      id: 0, 
    name:'', 
    description:'', 
    price:0
    };

  }
}
