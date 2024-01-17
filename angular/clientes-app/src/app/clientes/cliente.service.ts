import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente';
import { Observable, catchError, map, of, tap, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { DatePipe, formatDate, registerLocaleData } from '@angular/common';
//import localeES from '@angular/common/locales/es' //es-GT podria ser para Guate Lacale para que la fecha sea en español

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

 //Metodo Obtener todos los Cliente:  //Obtiene a los clientes con su texto normal
/*  getClientes(): Observable<Cliente[]>{
    //Forma 1:
    //return this.http.get<Cliente[]>(this.urlEndPoint); //cuando no es un Observable: this.http.get(this.urlEnPoint);
    
    //Forma 2:
    //return this.http.get<Cliente[]>(`${this.urlEndPoint}`);
    
    //Forma 3:
   return this.http.get(this.urlEndPoint).pipe(
    map(Response => Response as Cliente[]) //Otra forma esta parte: map( function (Response) {return Response as Cliente[]})     
    );

  }
*/

    //Metodo Obtener todos los Cliente: Pero Convierte los datos  como nombre de los clientes en Mayuscula UPPERCASE
    getClientes(): Observable<Cliente[]>{

      //Forma 3:
     return this.http.get(this.urlEndPoint).pipe(

      //Uso del operador Tap en el Observable //El primer tap es response de tipo object
      tap(response =>{
        //mostrar los datos de clientes en consola - Los nombres no estan en Mayuscula
        let clientes = response as Cliente[];
        console.log('ClienteService: tap 1 - Nombres aún no estan en mayuscula')
        clientes.forEach( cliente => {
          console.log(cliente.nombre);
        })
          
      }),

      map(Response => {
        let clientes = Response as Cliente[];
        return clientes.map(cliente => { //Retornamos y se cambia los datos nombres a Mayuscula
          cliente.nombre = cliente.nombre.toUpperCase(); //Cambia nombres de clientes a mayuscula 

          //Agregamos nuestro LOCATE personalizado -para que la fecha aparezca en español o especificamente para  un pais
          //registerLocaleData(localeES, 'ES'); No es recomendable crear el registerLocaleData en una clase mejor crearla en un componente(se agrego en app.config.ts) aparte para que sea global y se importada a cualquier otra clase que requiera

          //cliente.createAt = formatDate(cliente.createAt, 'dd-MM-yyyy', 'en-US') //Forma1: Cambia el formato de la fecha a la vista en el form: Recibe 3 parametros; fecha original, formato a cambiar, locate Estandar
          //Forma 2 de cambiar el formato de la fecha en la vista del form
          let datePipe = new DatePipe('ES')// Se envia nuestro locale ES que se creo  o usar el 'en-US' locate Estandar americano la fecha aparece en ingles
          //cliente.createAt = datePipe.transform(cliente.createAt, 'EEEE dd, MMMM yyyy');  //Formatos de fecha:'fullDate', 'dd/MM/yyyy', 'EEEE dd, MMMM yyyy' - Tuesday 11, December 2018 |
          return cliente;
        });
      }
      ),
      //El map cambia el response object  a un tipo Cliente [] listado de clientes
      //Uso del operador Tap en el Observable // Aqui el response ya es de tipo cliente por eso usamos el response en lugar de clientes
      tap(response =>{ //Muestra en consola y Los nombres ya estan en Mayuscula
        console.log('ClienteService: tap 2 - Los nombres estan en Mayuscula')
        //mostrar los datos de clientes en consola
        response.forEach( cliente => {
          console.log(cliente.nombre);
        })
      }),    
      );
  
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

    //Controlar el error de validacion de datos al crear el cliente(email cumpla con el formato, nombre y apellido no esten vacios)
    if(e.status==400){
      return throwError(() => e);
    }

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

        //Controlar el error de validacion de datos al crear el cliente(email cumpla con el formato, nombre y apellido no esten vacios)
        if(e.status==400){
          return throwError(() => e);
        }

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
