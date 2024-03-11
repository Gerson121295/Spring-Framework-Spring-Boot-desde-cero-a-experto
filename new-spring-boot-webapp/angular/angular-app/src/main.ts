import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

//Para definir eal componente principal en esta clase se agrega: AppComponent o el componente a elegir como principal: ProductComponent
//Asi tambien en la clase main.server.ts agregar: AppComponent o el componente a elegir como principal: ProductComponent
//y en el index se va a colocar el selector del component principal definido en app.component.ts: selector: 'app-root', asi <app-root></app-root> para este caso es "app-product" se agrega en el index.html: <app-product></app-product>
// Al cambiar el componente principal es necesario copiar los imports del componente que trae por defecto app.component.ts a product.componet.ts
bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
