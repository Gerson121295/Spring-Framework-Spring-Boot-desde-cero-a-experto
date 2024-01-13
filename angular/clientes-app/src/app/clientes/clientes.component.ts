import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { CommonModule } from '@angular/common';
import { CLIENTES } from './clientes.json';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule],
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

  ngOnInit(): void {
     this.clienteService.getClientes().subscribe( //asignale el arreglo clientes a los datos de la variable CLIENTES del archivo clientes.json.ts
        clientes => this.clientes = clientes //otra forma cuando sea mas de 1 parametro: function (clientes) { this.clientes = clientes }
     ); 
  }



}

