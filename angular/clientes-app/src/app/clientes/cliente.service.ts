import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente';
import { Observable, map, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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

constructor(private http: HttpClient) { }

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

  //Metodo Crear Cliente:
create(cliente:Cliente) : Observable<Cliente>{
  return this.http.post<Cliente>(this.urlEndPoint, cliente, {headers: this.HttpHeaders});
}

 //Metodo buscar Cliente por id:
 getCliente(id): Observable<Cliente>{
  return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`)
 }

 //Metodo para actualizar datos del cliente
 update(cliente : Cliente) : Observable<Cliente>{
  return this.http.put<Cliente>(`${this.urlEndPoint}/${cliente.id}`, cliente, {headers: this.HttpHeaders}) // en put<Cliente> se castea a <Cliente> porque retorna un objeto cliente actualizado
}

//Metodo eliminar cliente
delete(id: number): Observable<Cliente>{
  return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`, {headers: this.HttpHeaders})
}
}
