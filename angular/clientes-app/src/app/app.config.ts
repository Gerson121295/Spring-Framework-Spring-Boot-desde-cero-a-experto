

import { ApplicationConfig, LOCALE_ID, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HttpClientModule } from '@angular/common/http';
import localeES from '@angular/common/locales/es' //es-GT podria ser para Guate
import { registerLocaleData } from '@angular/common';


//Agregamos nuestro LOCATE personalizado -para que la fecha aparezca en español o especificamente para  un pais
registerLocaleData(localeES, 'ES'); //Metodo usando en cliente.service.ts y puede ser implementado en cualquier otro metodo

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
    importProvidersFrom(HttpClientModule),
    {provide: LOCALE_ID, useValue:'ES'} //Agrega para formatear fechas a español desde plantilla html(clientes.component.html)
  ]
}; 


