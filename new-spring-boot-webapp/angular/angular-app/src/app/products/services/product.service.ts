import { Injectable } from '@angular/core';
import { Product } from '../models/product';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  //Arreglos productos para guardar datos.
  private products: Product[] = [
    {
      id: 1,
      name:"Mesa comedor",
      description:"Para el comedor",
      price:700
    },
    {
      id: 2,
      name:"Teclado mecanido",
      description:"Para noche con lampara",
      price:1700
    },
    {
      id: 3,
      name:"TV SONY",
      description:"Nueva 29plg,",
      price:5000
    }
  ];

  constructor() { }

  findAll(): Observable<Product[]>{
    return of(this.products); //of toma el objeto product y lo convierte en un observable
  }

}
