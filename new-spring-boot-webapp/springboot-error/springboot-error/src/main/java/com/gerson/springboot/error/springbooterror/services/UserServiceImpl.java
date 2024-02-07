package com.gerson.springboot.error.springbooterror.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gerson.springboot.error.springbooterror.models.domain.User;

@Service
public class UserServiceImpl implements UserService {

    //Ejemplo de Crear los datos de la lista. Emular una BD en esta clase
/*     
    private List<User> users;

    public UserServiceImpl(){
        this.users = new ArrayList<>();
        users.add(new User(1L, "pepe", "Gonzales"));
        users.add(new User(2L, "Andres", "Mena"));
        users.add(new User(3L, "Maria", "Perez"));
        users.add(new User(4L, "Josefa", "Ramirez"));
        users.add(new User(5L, "Ale", "Gutierrez"));
    }
*/

    //Inyeccion de los datos de la lista desde el bean users de la clase AppConfig para Emular una BD
   @Autowired
    private List<User> users;


    //Realizamos la implementacion de los metodos
    @Override
    public List<User> findAll() {
        return users;
 }

 //Forma para manejo de excepcion 1
/*     @Override
    public User findById(Long id) {
        User user = null;

        for(User u : users){ //devolvemos users
            if(u.getId().equals(id)){
                user = u;
                break;
            }
        }

        //Manejo de excepcion no es recomendado hacerlo en el service(ensucia el service) debe hacer en el controller
//        if(user == null){
//           throw new UserNotFoundException("Error el usuario no existe!");
//       }
        return user;
  }
  */

  //Forma para manejo de excepcion 2
     @Override
    public Optional<User> findById(Long id) {

        //Buscar el id utilizando un for tradicional
/*      User user = null;      
        for(User u : users){ //devolvemos users
            if(u.getId().equals(id)){
                user = u;
                break;
            }
        }
 */
        //Buscar el id con programacion funcional
        Optional<User> user = users.stream().filter(usr -> usr.getId().equals(id)).findFirst();
        return user;
        //o dejaro en una linea de codigo
        //return users.stream().filter(usr -> usr.getId().equals(id)).findFirst();

        //Siguiente codigo es del for traducional: solo el return de abajo
/*  //Otra forma con mas lineas, esto es igual a un solo return(de abajo)
      if(user == null){
            return Optional.empty();
        }
        return Optional.of(user);
*/  
        //Forma reducida
//        return Optional.ofNullable(user); //ofNullable hace como un if si user es nulo devuelve un optional empty si no es nulo devuelve un optional.off del usuario.
  }

}
