import axios from "axios";

//Datos de prueba de productos de una lista
const initProducts = [
    {
        id:1,
        name: 'Monitor Samsung 65',
        price: 500,
        description: 'El monitor es nuevo'
    },
    {
        id:2,
        name: 'Iphone 14',
        price: 800,
        description: 'El telefono es bueno y nuevo'
    }
];


export const listProduct = () => {
    return initProducts;
  }


// Traer la data del Backend en springboot

const baseUrl = 'http://localhost:8080/products';

//Funciones para comunicarse con AXIOS
    //Lista los productos
export const findAll = async() => {
    try{ //Try catch para manejar los errores
        //si esta ok devuelve la lista
        const response = await axios.get(baseUrl);
        return response;
    }catch(error){
        //Si hubo error devuelve el error
        console.log(error);
    }
    return null; //si no pasa nada devuleve una lista nula
}

    //Crea productos
export const create = async({name, description, price}) => {
    try {
        const response = await axios.post(baseUrl, { //pasa 2 parametros: La ruta y el cuerpo del objeto en el requestBody(datos del producto)
            name, //name:name, //cuando se llame igual el atributo y el valor se puede abreviar solo a su nombre
            description, //description:description,
            price //price:price
        });
        return response;
    } catch (error) {
        console.log(error);
    }
    return null;
}


    //Actualiza productos
    export const update = async({id, name, description, price}) => {
        try {
            //const response = await axios.put(baseUrl + '/' + id, { //pasa 2 parametros: La ruta y el cuerpo del objeto en el requestBody(datos del producto)
            const response = await axios.put(`${baseUrl}/${id}`, {  //otra forma de pasar la ruta utilizando comillas francesas
                name, //name:name, //cuando se llame igual el atributo y el valor se puede abreviar solo a su nombre
                description, //description:description,
                price //price:price
            });
            return response;
        } catch (error) {
            console.log(error);
        }
        return null;
    }

    //Eliminar producto
    export const remove = async(id) => { //se pasa el id como argumento
        try {
            await axios.delete(`${baseUrl}/${id}`);
        } catch (error) {
            console.log(error);
        }
    }

    