import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ClienteService } from './cliente.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  standalone: true,
  imports: [CommonModule,RouterModule,FormComponent, FormsModule],
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit{
  
  protected cliente: Cliente = new Cliente(); //Inyectamos la clase Cliente
  protected titulo:string = "Crear Cliente";
  constructor(private clienteService: ClienteService,
              private router: Router, //Crear cliente
              private activateRoute: ActivatedRoute //cargar cliente -//Metodo buscar Cliente por id:
              ){}

  ngOnInit(): void {
    this.cargarcliente(); //Llama al metodo cuando se inicializa el componente
  }

  cargarcliente(): void{
    this.activateRoute.params.subscribe(params => {
      let id = params['id']
      if(id){
        this.clienteService.getCliente(id).subscribe((cliente)=> this.cliente = cliente)
      }
    })
  }

  //Metodo Crear Cliente:
  //Forma 1: en cliente.service.ts utiliza el tipo de dato any
/*  public create():void{
    this.clienteService.create(this.cliente).subscribe(
      //response => this.router.navigate(['/clientes']) //otra forma
      json => { //se  cambio a json que era cliente
        this.router.navigate(['/clientes'])
        //Swal.fire('Nuevo Cliente', `Cliente ${json.cliente.nombre} creado con éxito!`, 'success'); //se agrego json. para acceder al nombre, Esta forma escribimos el mensaje a mostrar al crear un cliente
          Swal.fire('Nuevo Cliente', `${json.mensaje} :  ${json.cliente.nombre}`, 'success'); //Forma2: Esta forma muesta el mensaje de notificacion desde el backend al crear un cliente.
       // swal.fire({title: 'Nevo cliente', `Cliente ${cliente.nombre} creado con éxito`, icon:'success'})
      }
    );
  }
*/

   //Forma 2: Utiliza la forma anterior:  en cliente.service.ts, utiliza Map para convertir response a Cliente
   public create():void{
    this.clienteService.create(this.cliente).subscribe(
      //response => this.router.navigate(['/clientes']) //otra forma
      cliente => { 
        this.router.navigate(['/clientes'])
        Swal.fire('Nuevo Cliente', `Cliente ${cliente.nombre} creado con éxito!`, 'success'); //Describimos la notificacion al crear el cliente.
       // swal.fire({title: 'Nevo cliente', `Cliente ${cliente.nombre} creado con éxito`, icon:'success'})
      }
    );
  }



  //Metodo actualizar cliente  - busca al Cliente por id:
update():void{
  this.clienteService.update(this.cliente)
  .subscribe(json => {  //cliente paso a ser json debido a que se cambio en cliente.service.ts
    this.router.navigate(['/clientes'])
    //Swal.fire('Cliente Actualizado', `Cliente ${cliente.nombre} actualizado con éxito!`, 'success');
    Swal.fire('Nuevo Cliente', `${json.mensaje} :  ${json.cliente.nombre}`, 'success'); //Forma2: Esta forma muesta el mensaje de notificacion desde el backend al crear un cliente.
  })
}


}
