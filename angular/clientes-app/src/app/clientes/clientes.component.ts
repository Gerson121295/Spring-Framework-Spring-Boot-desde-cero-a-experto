import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { CommonModule } from '@angular/common';
import { ClienteService } from './cliente.service';
import { RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { tap } from 'rxjs';


@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule,RouterModule], 
  templateUrl: './clientes.component.html'
})
export class ClientesComponent implements OnInit{

  /*// FORMA 1: DE AGREGAR DATOS ESTATICOS:
  clientes: Cliente[] = [
    {id:1,  nombre:'Gerson', apellido:'Ep', email:'ger@gmail.com', createAt:'2017-12-11'},
    {id:2,  nombre:'Danna', apellido:'Perez', email:'dav@gmail.com', createAt:'2018-12-21'},
    {id:3,  nombre:'Sara', apellido:'Gomez', email:'saar@gmail.com', createAt:'2019-02-11'},
    {id:4,  nombre:'Karla', apellido:'Santizo', email:'karl@gmail.com', createAt:'2018-03-01'},
    {id:5,  nombre:'Mariana', apellido:'Rodas', email:'maria@gmail.com', createAt:'2019-06-11'},
    {id:6,  nombre:'Andrea', apellido:'Mar', email:'andrea@gmail.com', createAt:'2020-12-16'},
    {id:7,  nombre:'Luis', apellido:'Rodriguez', email:'luid@gmail.com', createAt:'2021-08-19'},
    {id:8,  nombre:'Lucia', apellido:'Luz', email:'luiLuz@gmail.com', createAt:'2022-12-11'},
    {id:9,  nombre:'Raquel', apellido:'Esrt', email:'estrra@gmail.com', createAt:'2017-10-18'},
    {id:10,  nombre:'Alberto', apellido:'Riss', email:'alber@gmail.com', createAt:'2023-11-19'}
  ];
 */

  //FORMA 2: DE AGREGAR DATOS IMPORTANDO UN ARCHIVO .JSON.TS
/*
  constructor(){}
clientes: Cliente[]; //Definir variable clientes como un arreglo,

  ngOnInit(): void {
    this.clientes = CLIENTES; //asignale el arreglo clientes a los datos de la variable CLIENTES del archivo clientes.json.ts
  }
*/

//FORMA 3: AGREGAR DATOS CREAR Y USAR CLASE CLIENTE.SERVICE.TS IMPORTANDO UN ARCHIVO .JSON.TS

//Forma 1 de importar una clase de servicio
constructor(private clienteService: ClienteService){ 
} 


//Forma 2 de importar una clase de servicio:
/*
private clienteService: ClienteService;
constructor(clienteService: ClienteService){ //se agrega la clase ClienteService
  this.clienteService = clienteService;
} 
*/

clientes: Cliente[]; //Definir variable clientes como un arreglo,

/*
  ngOnInit(): void {
     this.clienteService.getClientes().subscribe( //asignale el arreglo clientes a los datos de la variable CLIENTES del archivo clientes.json.ts
        clientes => this.clientes = clientes //otra forma cuando sea mas de 1 parametro: function (clientes) { this.clientes = clientes }
     ); 
  }
*/

//Code usando tap para mostrar en consola
ngOnInit(): void {
  this.clienteService.getClientes().pipe(
    tap(clientes => {  //Incluso el tap solo podria ser: --  tap(clientes => this.clientes = clientes)
      this.clientes = clientes //linea de codigo descrita en el subscribe
      console.log('ClientesComponent: tap 3 - data')
        //mostrar los datos de clientes en consola
        clientes.forEach(cliente => {
          console.log(cliente.nombre);
        });
    })
  )
  .subscribe(); // Se podria dejar el subscribe asi y dentro del tap definir:  clientes => this.clientes = clientes
  /*
    .subscribe( //asignale el arreglo clientes a los datos de la variable CLIENTES del archivo clientes.json.ts
     clientes => this.clientes = clientes //otra forma cuando sea mas de 1 parametro: function (clientes) { this.clientes = clientes }
  ); 
*/
}



//Metodo para eliminar un cliente
delete(cliente: Cliente): void {
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success",
      cancelButton: "btn btn-danger"
    },
    buttonsStyling: false
  });
  swalWithBootstrapButtons.fire({
    title: "Esta seguro?",
    text: `¿Seguro que desea eliminar al cliente ${cliente.nombre} ${cliente.apellido}?`, //"You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Si, Eliminar",
    cancelButtonText: "No, Cancelar",
    reverseButtons: true
  }).then((result) => {
    if (result.isConfirmed) {
      this.clienteService.delete(cliente.id).subscribe(
        response => {
          this.clientes = this.clientes.filter(cli => cli !== cliente)
          swalWithBootstrapButtons.fire({
            title: "Eliminado!",
            text: `Cliente ${cliente.nombre} ha sido eliminado. con éxito.`,
            //text: "El cliente ha sido eliminado.",
            icon: "success"
          });
        }
      )
    } else if (
      result.dismiss === Swal.DismissReason.cancel
    ) {
      swalWithBootstrapButtons.fire({
        title: "Cancelado",
        text: `El cliente ${cliente.nombre} ${cliente.apellido} no fue eliminado`,//text: "El cliente no fue eliminado",
        icon: "error"
      });
    }
  });
}

}

