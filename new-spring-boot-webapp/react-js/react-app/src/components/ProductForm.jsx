import { useEffect, useState } from "react"
import{ PropTypes } from "prop-types";

export const ProductForm = ({productSelected, handlerAdd} ) => {

    //Valores iniciales del formulario
    const initialDataForm = {
        id:'',
        name: '',
        description: '',
        price: ''
    }

    //Para manejar el estado del Formulario se usa useEfect
    const[form, setForm] = useState(initialDataForm); //recibe el estado inicial de los datos del formulario

    //Conjunto de variables que recibe sus valores del objeto form que tiene sus campos name, description, price.  name=form.name, description=form.description, price=form.price
    //descomponemos los valores del objeto form en variables separadas para poder poblar los campos por medio del value del input
    const{id, name, description, price} = form;

    //Cuando hace clic en el boton update cambia el estado del boton productSelect y cuandp cambia el estado se pobla los datos del producto seleccionado en el state del formulario
    useEffect(() => {
        setForm(productSelected); //se pasa el producto seleccionado,al formulario el parametro [productSelected] es la condicon para que suceda o sea se pasa al formulario cuando cambie el productSelected
    }, [productSelected]) //este parameto [productSelected] se gatille cuando cambie el productSelected


    return(
        <form onSubmit={(event) => {
            event.preventDefault(); //preventDefault hace que cuando se envie el formulario con el submit se quede los datos en el la pagina, para que no haga un refresh(se pierdan los datos)
        
            if(!name || !description || !price){ //valida los datos del formulario
                alert('Debe de completar los datos del formulario')
                return ;
            }

            //Guardar los datos del formulario en el listado de la lista
            //console.log(form);
            handlerAdd(form); //al metodo handlerAdd se le pasa form que contiene le producto a agregar
            //Limpiar el formulario luego de enviar los datos
            setForm(initialDataForm); //Se envia los valores iniciales al formulario
   

        }}>
            <div>
                <input 
                    placeholder="Name"
                    // style={{marginBottom: '4px'}}
                    className="form-control my-3 w-75"
                    name="name"
                    value={name} //otra forma era hacerlo sin variable: form.name
                    onChange={(event) => setForm({
                        ...form,
                        name: event.target.value
                    })}
                />
            </div>

            <div>
                <input 
                    placeholder="Description"
                    //style={{marginBottom: '4px'}}
                    className="form-control my-3 w-75"
                    name="description"
                    value={description}
                    onChange={(event) => setForm({
                        ...form,
                        description: event.target.value
                    })}
                />
            </div>

            <div>        
                <input 
                    placeholder="Price"
                    //style={{marginBottom: '4px'}}
                    className="form-control my-3 w-75"
                    name="price"
                    value={price}
                    onChange={(event) => setForm({
                        ...form,
                        price: event.target.value
                    })}
                />
            </div>

            <div>
                <button type="submit" className="btn btn-primary">
                    {id>0 ? 'Update' : 'Create'}
                </button>
            </div>
        </form>
    )
}

ProductForm.propTypes = {
    handlerAdd: PropTypes.func.isRequired,
    productSelected:  PropTypes.object.isRequired
}