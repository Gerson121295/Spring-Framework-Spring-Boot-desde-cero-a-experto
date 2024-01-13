import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";

@Component({
        selector: 'app-header',
        standalone: true,
        //template: `Agregar codigo html `,
        templateUrl: './header.component.html', //agrega el html 
        imports: [RouterModule]
    })
export class HeaderComponent {
titulo:string = 'App Angular';
}