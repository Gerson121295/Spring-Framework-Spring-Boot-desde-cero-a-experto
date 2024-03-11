
//Clase Product que contiene los atributos de product

export class Product{
    //Forma de arreglar el warning al definir las variables es en el archivo tsconfig.json cambiar de true a false el campo: "strict": false,
   // id: number = 0; forma 1 de arreglar el error de warning es inicializar la variable
   id!: number;  //Forma 2 de arreglar el error de warning es agregar signo de exclamacion
    //name: string= '';
    name: string;
    description: string = '';
    price: number = 0;

}