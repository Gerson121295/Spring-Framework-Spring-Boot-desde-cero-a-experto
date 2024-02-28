package com.gerson.springboot.app.springbootcrudjpa;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.gerson.springboot.app.springbootcrudjpa.entities.Product;

/* Esta clase es para validar los datos(atributos de Product) 
    con esta clase evitamos agregar message  a las anotaciones(@NotEmpty): asi:  @NotEmpty(message = "{NotEmpty.product.name}") 
    solo se define la anotacion @NotEmpty al atributo para hacer mas limpia la clase, e incluso no es necesario agregar las anotaciones(NotNull, NotEmpty) a los atributos de la clase Product
*/

@Component //para que despues se pueda inyectar en el controlador para manjear las validaciones
public class ProductValidation implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) { //Target es el objeto Product que se pasa por referencia, Errors  es el BindingResult para manejar los errores
        Product product = (Product) target; //Se define la clase Product Como Objetivo.

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null,"es requerido!");
        
        //Forma 1 de validar
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotBlank.product.description");

        //Forma 2 de validar usando if
        //Valida si la descripcion es nulo. o es tiene espacio en blanco o vacio
        if(product.getDescription() == null || product.getDescription().isBlank()){
            errors.rejectValue("description", null, "es requerido, por favor.");
        }

        //Si el precio del producto es nulo y es menor a 500
        if(product.getPrice() == null){
            errors.rejectValue("price", null,"no puede ser nulo, ok.");
        }else if(product.getPrice() < 500){
            errors.rejectValue("price", null,"debe ser mayor o igual a 500");
        }

    }
    

}
