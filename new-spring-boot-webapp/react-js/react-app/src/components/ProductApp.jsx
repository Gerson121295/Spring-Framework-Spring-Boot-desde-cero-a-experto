import { useEffect, useState } from "react"
import { findAll, update, create, remove} from "../services/ProductService";
import { ProductGrid } from "./ProductGrid";
import{ PropTypes } from "prop-types";
import { ProductForm } from "./ProductForm";

export const ProductApp = ({title}) => { //ProductApp tiene el props title que viene de la clase padre: main

    //useStates de la App
    const[products, setProducts] = useState([]);
    const[productSelected, setProductSelected] = useState({ //producto seleccionado
        id:'',
        name: '',
        description: '',
        price: ''
    })

    //funcion para obtener los productos del backend a travez de la funcion findAll que esta en clase ProductService
    const getProducts = async () => {
        const result = await findAll(); 
        //console.log(result);
        setProducts(result.data._embedded.products); //asignamos el result al objeto products por medio de setProducts. // _embedded.products - asi devuelve en nuestro JSON en springboot al hacer una peticion GET. Para otro ejemplo puede cambiar ahora "result" es la variable y "data" es de axios para datos
    }



    //Efectos
    useEffect(() => {
        //Ejemplo trae los datos de producto de la lista creada en la clase ProductService
        /*  const result = listProduct(); //result guarda el contenido del metodo listProduct de la Clase ProductService que contiene los productos.
            setProducts(result);
        */
        getProducts(); //Llama a la funcion que trae los datos desde el backend en el useEffect 
    }, []); //[] se ejecuta una vez(asigna los productos) cuando se crea el componente, no cuando se actualiza la vista


    //Handler
        //Guardar los datos del formulario en el listado de la lista
        //Metodo para agregar productos a la lista, al momento de crear la instacia se le agrega el producto a los que ya exiten
        const handlerAddProduct = async(product) => { //product es el nuevo producto que se pasa a la lista 
            //console.log(product);
            // Estos 3 puntos representa los campos de productos: ... name, description, price
            //setProducts([...products, {...product}]); //A la lista products se añade product.
        
            //if(products.includes(product)) {  //otra forma Valida con includes si en el arreglo se encuentra un objeto retorna true o false
            if(product.id> 0) { //Si el producto es mayor a 0 es porque exite y actualizara el registro   
                //si encuentra el producto lo actualiza

                const response = await update(product); //llama al metodo para actualizar producto de la clase ProductService, handleAddProduct se agrego como async porque el metodo update se agrego await

                 //A la lista products se añade product. pero valida si ya exite, si ya existe lo actualiza
                setProducts(products.map(prod => {

                    /* Usando la lista de ProductService
                    if(prod.id == product.id){ //valida si el id del producto es igual
                    return {...product} //reemplazamos el prod.name con el product nuevo. 
                    */
                        //Trae los datos de la BD
                    if(prod.id == response.data.id){ //valida si el id del producto es igual alde la BD
                    return {...response.data} //reemplazamos el prod.name con el product nuevo de la BD.
                    }

                //si no es nuevo retornamos el actual
                return prod;
            })); 
            }else{
                //Usando la lista de ProductService para crear un nuevo producto
                //Si include retorna false el objeto no esta en el arreglo: Se creará un nuevo producto
                //setProducts([...products, {...product, id:new Date().getTime()}]); //A la lista products se añade product., Cuando se crea un producto se le asigna un id, getTime devuelve un numero unico
            
                //Crea un producto usando el metodo create de ProductService que conecta con el Backend
                const response = await create(product); //llama al metodo para create producto de la clase ProductService,
                setProducts([...products, {...response.data}]); 
            
            }
           
        }

        //Handler para eliminar producto
        const handlerRemoveProduct = (id) => { //recibe el id como parametro // o name para prueba sin BD
            //console.log(id);
            remove(id); //eliminamos el producto usando el metodo remove de la clase ProductService
            //setProducts(products.filter(product => product.name != name)); //si el id del producto no es igual al id del parametro lo deja pasar
            setProducts(products.filter(product => product.id != id));
        }

        //Metodo para poblar la fila seleccionada a editar
        const handlerProductSelected = (product) => {//recibe el producto
            setProductSelected({...product});
        }    


        return(
            <div className="container my-4">
                {/* el valor de la varialbe title viene del la clase padre: main.jsx */}
                {/*  <h1> { title }</h1>  */}
                <h2> { title.text }</h2> 
                <div className="row">
                    <div className="col">
                        {/* Se le pasa la funcion para agregar producto a formulario de producto */}
                        <ProductForm handlerAdd = {handlerAddProduct} productSelected={productSelected}/> 
                    </div>
                    <div className="col">

                    
                     {/*ProductApp es clase padre y pasa a la clase ProductGrid con la variable products se le pasa los products que recibe del metodo: listProduct() */}
                    {/* Valida si existe datos de productos si es mayor a 0 existen productos y muestra la tabla si no muestra un mensaje que no existen registros en el sistema*/}
                    {
                        products.length > 0 ? <ProductGrid products={products} handlerRemove={handlerRemoveProduct} handlerProductSelected={handlerProductSelected}/> 
                           : <div className="alert alert-warning">No hay productos en el sistema</div>
                    }
                       
                    </div>
                </div>      
            </div>
        )
}

//Validacion del props title
ProductApp.propTypes = {
    //title:PropTypes.string.isRequired  
    title: PropTypes.object.isRequired  //forma 2
}