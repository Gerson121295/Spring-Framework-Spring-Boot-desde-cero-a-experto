import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente';
import { Observable, catchError, map, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

//La idea es mover todos los datos de clientes de clientes.component.ts a esta clase
@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  /* //Datos Estaticos Importando archivo.json
  constructor() { }
  getClientes(): Observable<Cliente[]>{
    return of(CLIENTES);
*/

//Importando datos desde el backend de SpringBoot APIRest 
  
private urlEndPoint:string = 'http://localhost:8080/api/clientes'; //URL de acceso a los recursos de la APIRest Backend
private HttpHeaders = new HttpHeaders({'Content-Type':'application/json'})

constructor(private http: HttpClient,
            private router: Router
            ) { }

 //Metodo Obtener Cliente:
  getClientes(): Observable<Cliente[]>{

    //Forma 1:
    return this.http.get<Cliente[]>(this.urlEndPoint); //cuando no es un Observable: this.http.get(this.urlEnPoint);
    
    //Forma 2:
    //return this.http.get<Cliente[]>(`${this.urlEndPoint}`);
    
    //Forma 3:
 /*   return this.http.get(this.urlEndPoint).pipe(
      map(Response => Response as Cliente[]) //Otra forma esta parte: map( function (Response) {return Response as Cliente[]})     
      );
*/
  }

  //Metodo Crear Cliente: Forma 1 conviertiendo el el tipo de retorno de Cliente a any
  //cambio el tipo de dato de retorno antes: Observable<Cliente>  ahora retorna cualquier tipo de dato: Observable<any> esto porque desde el back viene un map json.
/*create(cliente:Cliente) : Observable<any>{ //Observable<Cliente> se cambio ahora retorna cualquier tipo de dato con <any>  //Donde es any era Cliente
  return this.http.post<any>(this.urlEndPoint, cliente, {headers: this.HttpHeaders}).pipe( //apartir de .pipe es codigo para el manejo de errores.
    catchError(e => {
      console.error(e.error.mensaje);
      Swal.fire(e.error.mensaje, e.error.error, 'error');
      return throwError(() => e); //retorna el objeto de error pero convertido en un observable
    })
  )
}
*/

//Metodo Crear Cliente: Forma 2 utilizando Map para convertir response a Cliente
create(cliente:Cliente) : Observable<Cliente>{ //Observable<Cliente> se cambio ahora retorna cualquier tipo de dato con <any>  //Donde es any era Cliente
  return this.http.post(this.urlEndPoint, cliente, {headers: this.HttpHeaders}).pipe( //apartir de .pipe es codigo para el manejo de errores.
    map((response: any)=> response.cliente as Cliente),
  
  catchError(e => {
      console.error(e.error.mensaje);
      Swal.fire(e.error.mensaje, e.error.error, 'error');
      return throwError(() => e); //retorna el objeto de error pero convertido en un observable
    })
  )
}


 //Metodo buscar Cliente por id:
 getCliente(id): Observable<Cliente>{ 
  return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
    catchError(e => {
      this.router.navigate(['/clientes']); //permite reenviar a la pagina de clientes luego de que ocurra el error.
      console.error(e.error.mensaje);
      Swal.fire('Error al editar', e.error.mensaje, 'error');
      return throwError(() => e);
    })
  )
 }

 //Metodo para actualizar datos del cliente
 update(cliente : Cliente) : Observable<any>{ //Observable<Cliente>
  return this.http.put<any>(`${this.urlEndPoint}/${cliente.id}`, cliente, {headers: this.HttpHeaders}).pipe( // en put<Cliente> se castea a <Cliente> porque retorna un objeto cliente actualizado
  catchError(e => {
    console.error(e.error.mensaje);
    Swal.fire(e.error.mensaje, e.error.error, 'error');
    return throwError(() => e); //retorna el objeto de error pero convertido en un observable
  })
  )
}

//Metodo eliminar cliente
delete(id: number): Observable<Cliente>{
  return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`, {headers: this.HttpHeaders}).pipe(
    catchError(e => {
      console.error(e.error.mensaje);
      Swal.fire(e.error.mensaje, e.error.error, 'error');
      return throwError(() => e); //retorna el objeto de error pero convertido en un observable
    })
  )
}
}
