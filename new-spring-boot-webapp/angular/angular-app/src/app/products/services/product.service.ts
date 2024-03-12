import { Injectable } from '@angular/core';
import { Product } from '../models/product';
import { Observable, map, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

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

  // Ejemplo consumiendo datos de la lista 
  /*
  constructor() { }

  findAll(): Observable<Product[]>{
    return of(this.products); //of toma el objeto product y lo convierte en un observable
  }
*/

// Ejemplo consumiendo datos del backend con springBoot
  private url: string = 'http://localhost:8080/products';

  constructor(private http: HttpClient) { }

    //Obtener los productos del backend
    findAll(): Observable<Product[]>{
      return this.http.get<Product[]>(this.url).pipe(
        map((response:any) => response._embedded.products as Product[]), // este texto "_embedded.products" se encuentra en el JSON del backend al mostrar los products con GET      
        );
    }

    //Metodo para crear producto
    create(product: Product): Observable<Product>{ //recibe un objeto producto
      return this.http.post<Product>(this.url, product);//pasamos la url, y pasamos el json del producto, post<Product> devuelve un objeto producto
    }

    //Metodo para actualizar producto
    update(product: Product): Observable<Product>{ //recibe un objeto producto
      return this.http.put<Product>(`${this.url}/${product.id}`,product); //pasamos la url/id/objeto product "el json del product", post<Product> devuelve un objeto producto
    }

    //Metodo para eliminar el producto
    remove(id: number): Observable<void>{
      return this.http.delete<void>(`${this.url}/${id}`); //recibe la url/id.  metodo devuelve un void(nada) delete<void>
    }
}
