import { Component } from "@angular/core";

@Component({
    selector: 'app-footer',
        standalone: true,
        //template: `Agregar codigo html `,
        templateUrl: './footer.component.html', //agrega el html 
        styleUrls: ['./footer.component.css']
})
export class FooterComponent {
    public autor: any = {nombre:'Gerson', apellido: 'Ep'};
}
