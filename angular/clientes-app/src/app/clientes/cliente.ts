export class Cliente {
    //Definir solo las variables Ejemplo: id: number; marca error la solucion es agregarle signo ! a las variables o atributos
    //Otra solucion es modificar y agregar en el archivo tsconfig.json:  "strictPropertyInitialization": false  
    //Otra forma es modificar el archivo:tsconfig.json: y especificar como false el argumento strict: "strict": false,
    
    id: number; // Solucion 1: id!: number;
    nombre!: string;
    apellido!: string;
    createAt!: string;
    email!: string;
    
}
