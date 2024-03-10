
import{ PropTypes } from "prop-types";

export const ProductDetail = ({handlerProductSelected, handlerRemove, product = {}}) => { //Recibe props: product como un objeto

    return(
        <tr> 
            {/* <td>{product.id}</td> */}
            <td>{product.name}</td>
            <td>{product.description}</td>
            <td>{product.price}</td>
            <td>
                <button className="btn btn-secondary btn-sm" onClick={() => handlerProductSelected (product)}>
                    update
                </button>
            </td>
            <td>
                <button className="btn btn-danger btn-sm" onClick={() => handlerRemove(product.id)}>
                    remove
                </button>
            </td>
        </tr>
    )
}

//Validacion de los props
ProductDetail.propTypes = {
    product: PropTypes.object.isRequired,
    handlerRemove: PropTypes.func.isRequired,
    handlerProductSelected : PropTypes.func.isRequired
}
