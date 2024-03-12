import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { FormComponent } from '../form/form.component';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [FormComponent], //se importa los componentes que se utiliza (dentro de su html) en este componete
  //imports: [CommonModule, RouterOutlet], //si se elige este componente principal copiar los imports del componente principal por defecto app.component.ts
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit{

  products: Product[] = []; //products guardara los datos que vienen del backend

  productSelected: Product = new Product(); //productSelected - guarda el producto seleccionado para editarlo 

  constructor(private service: ProductService){   }//se inyecta el service ProductService
    //Implementar un metodo que se ejecuta despes de haberse instanciado la clase componente pero solo una vez
  //Este metodo se ejecuta cuando se crea el componente por primera vez 

  ngOnInit(): void {
    // forma 1
   this.service.findAll().subscribe(products => this.products = products) //forma basica sin llaves
      //products =>{ this.products = products;}) //a this.products le asignamos los products valores que vienen del backend 
  }


  //Recibe el objeto para agregarlo a la lista de productos
  addProduct(product: Product): void{ //recibe como parametro el objeto producto del formulario

    //Valida si el objeto product que recibe de parametro su id es > 0 entonces ese producto ya existe entonces se actualizara, si el id es menor entonces ese producto no existe se tendra que crear. 
    if(product.id > 0){
      //Actualiza un producto y Conecta con el backend y guarda en la BD 
      this.service.update(product).subscribe(productUpdated => {

        this.products = this.products.map(prod => {
          if(prod.id == product.id){ //si el prod.id(de la BD) es igual product.id(id del producto que recibe del formulario)
            return {... productUpdated}; //cambiamos el elemento o registros de la BD por el nuevo que viene del formulario
          }
          return prod; //si no es igual retornamos el objeto existente
        })
      })

      //Ejemplo de actualizar el product a la lista local
/*      this.products = this.products.map(prod => {
        if(prod.id == product.id){ //si el prod.id(de la BD) es igual product.id(id del producto que recibe del formulario)
          return {...product}; //cambiamos el elemento o registros de la BD por el nuevo que viene del formulario
        }
        return prod; //si no es igual retornamos el objeto existente
      })
*/      
    }else{ 
      //El id es menor entonces ese producto no existe se tendra que crear. 
      //Crea un producto a la lista local
    //product.id = new Date().getTime(); //Forma 1: genera un id unico random a partir de la fecha
    //this.products.push(product); //Forma1: mutable: agrega el objeto al final de la lista
    //this.products = [... this.products, { ...product, id: new Date().getTime() }]; //Forma 2 inmutable - agrega el objeto a la lista de productos. cada punto representa un campo del objeto
  
    //Crea un producto y conecta con el backend y guarda en la BD 
    this.service.create(product).subscribe(productNew => { //productNew viene de la BD
    //this.products.push({ ...productNew}); //Forma1: mutable: agrega el productNew al final 
    this.products = [... this.products, { ...productNew}]; //Forma 2 inmutable - agrega el objeto a productos. cada punto representa un campo del objeto
    })
  
  }

  //Limpiar los campos del formulario luego de actualizar o crear un registro
  this.productSelected = new Product();
  }

  //Poblar el registro seleccionado para editar con sus datos
  onUpdateProduct(productRow: Product): void{
    this.productSelected = {... productRow};
  }

  //Eliminar producto
  onRemoveProduct(id:number): void{
    //Elimina un producto de la lista local
    //this.products = this.products.filter(product => product.id != id); //filtro pasan todos menos el que tenga el id del producto a eliminar recibido en el parametro, se crea un nuevo lista sin el producto del id a eliminar
  
    //Elimina un producto y conecta con el backend y elimna el producto en la BD 
    this.service.remove(id).subscribe(() =>{ //no se subscribe a nada: ()
      this.products = this.products.filter(product => product.id != id); //filtro pasan todos menos el que tenga el id del producto a eliminar recibido en el parametro, se crea un nuevo lista sin el producto del id a eliminar
   })
  
  }
}


