import{ PropTypes } from "prop-types"
import { ProductDetail } from "./ProductDetail"

export const ProductGrid = ( {handlerRemove, handlerProductSelected, products = []} ) => { //recibe los props "propiedades del products" de la clase padre, recibe props el metodo para eliminar producto

    return(
            <table className="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>name</th>
                        <th>description</th>
                        <th>price</th>
                        <td>update</td>
                        <th>remove</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map( product => {
                        {/* Necesita el key porque se va a repetir 3 veces: ProductGrid es el padre de ProductDetail al cual se le envia los productos */}
                        {/**Se envian los metodo handler a la fila para poder eliminar, seleccionar */}
                        return <ProductDetail handlerProductSelected={handlerProductSelected} handlerRemove={handlerRemove} product={product}  key={product.name}/>            
                    })}
                </tbody>
            </table>
    )
}

//Validacion de los props
ProductGrid.propTypes = {
    products: PropTypes.array.isRequired,
    handlerRemove: PropTypes.func.isRequired,
    handlerProductSelected: PropTypes.func.isRequired
}