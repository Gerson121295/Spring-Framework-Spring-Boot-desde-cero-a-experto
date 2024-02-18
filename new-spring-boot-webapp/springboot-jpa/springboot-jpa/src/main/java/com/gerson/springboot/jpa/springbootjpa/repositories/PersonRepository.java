package com.gerson.springboot.jpa.springbootjpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gerson.springboot.jpa.springbootjpa.dto.PersonDto;
import com.gerson.springboot.jpa.springbootjpa.entities.Person;


public interface PersonRepository extends CrudRepository<Person, Long> { //Se agrega la Clase "Person", y su tipo de dato del id "Long"
//Tambien se puede extender de JpaRepository este tiene los metodos de CrudRepository y los de PaginAndResorting para la paginacion.

    //JPQL campos personalizados, traer el nombre de la persona pero que no sea tipo Persona, object o Arreglo que sea String
    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id); 

    //JPQL campos personalizados, traer el id de la persona pero que no sea tipo Persona, object o Arreglo que sea tipo Long
    @Query("select p.id from Person p where p.id=?1")
    Long getIdById(Long id);

    //JPQL campos personalizados, traer nombre y apellido concatenado
    @Query("select concat(p.name, ' ', p.lastname) as fullname from Person p where p.id=?1")
    String getFullNameById(Long id);

    //JPQL Campos personalizados Parte 2
    //Devolviendo campos separados y valores del objeto entity - Obtiene una Lista
    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p ")
    List<Object[]> obtenerPersonDataList();

    //Devolviendo campos separados y valores del objeto entity - Obtiene un objeto
    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id=?1")
    //Object obtenerPersonDataById(Long id); //Forma sin optional
    Optional<Object> obtenerPersonDataById(Long id); //Agregando Optional


    //##JPQL Instanciacion dinamica de clase entity personalizada
    //Devolviendo todos los campos de Person y el lenguaje de programacion - Obtiene una Lista
    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> FindAllMixPerson();

    @Query("select new Person(p.name, p.lastname) from Person p") //con new Perso instanciamos la clase Person nosotros no JPA. Es importante que este constructor exista en la clase
    List<Person> findAllObjectPersonPersonalized();


    //## Ejemplo para JPQL instanciacion dinamica de clase DTO personalizada
    @Query("select new com.gerson.springboot.jpa.springbootjpa.dto.PersonDto(p.name, p.lastname) from Person p") //con new Perso instanciamos la clase Person nosotros no JPA. Es importante que este constructor exista en la clase
    List<PersonDto> findAllObjectPersonDto();


    //Usando DISTINCT palabra clave de JPQL/HQL - Filtra los nombres de las personas
    @Query("select p.name from Person p") 
    List<String> findAllNames();

    //Usando DISTINCT palabra clave de JPQL/HQL - //Filtra los nombres de las personas - pero no muestra repetidos
    @Query("select distinct(p.name) from Person p")
     List<String> findAllNamesDistinct();

    //Usando DISTINCT palabra clave de JPQL/HQL - //Filtra los lenguajes de programacion - pero no muestra repetidos
    @Query("select distinct(p.programmingLanguage) from Person p")
    List<String> findAllProgrammingLanguageDistinct();

    //Usando DISTINCT palabra clave de JPQL/HQL - //Filtra los lenguajes de programacion y cuenta la cantidad - pero no muestra repetidos
    @Query("select count(distinct(p.programmingLanguage)) from Person p")
    Long findAllProgrammingLanguageDistinctCount();


    // ## Funciones JPQL concat, upper, lower y operador like
    //Concatena el nombre y el apellido
    @Query("select concat(p.name, ' ', p.lastname) from Person p")
    List<String> findAllFullNameConcat();

    //Forma 2: Concatenar el nombre y el apellido
    @Query("select p.name || ' ' || p.lastname from Person p")
    List<String> findAllFullNameConcatconBarras();

    //Concatenar el nombre y el apellido y convertilo a Mayuscula
    @Query("select upper(p.name || ' ' || p.lastname) from Person p")
    List<String> findAllFullNameConcatUpper();

    //Concatenar el nombre y el apellido y convertilo a Minuscula
    @Query("select lower(concat(p.name, ' ', p.lastname)) from Person p")
    List<String> findAllFullNameConcatLower();

    //Devolviendo campos separados y valores del objeto entity - Obtiene una Lista
    @Query("select p.id, upper(p.name), lower(p.lastname), upper(p.programmingLanguage) from Person p ")
    List<Object[]> findAllPersonDataListCase();


    // ## Usando palabras claves between de JPQL
    //Lista las personas que esten entre el rango de id 2 y 5
    @Query("select p from Person p where p.id between 2 and 5") 
    List<Person> findAllBetweenId();

    //Lista las personas que esten entre el rango de id dados en los parametros
    //@Query("select p from Person p where p.id between ?1 and ?2") 
    //@Query("select p from Person p where p.id between ?1 and ?2 order by p.name asc") //order by p.name  -funcion de ordenar por el nombre por defecto es ascendente del 1 a 100 o A a Z, o se agrega: "asc" y "desc" para descendente
    @Query("select p from Person p where p.id between ?1 and ?2 order by p.name desc, p.lastname asc") //Ejemplo - ordena el nombre ascendente y el apellido descendente
    List<Person> findAllBetweenIdConParametros(Integer id1, Integer id2);
    

    //Lista las personas que esten entre el rango de caracteres: 'J' y 'P'
    @Query("select p from Person p where p.name between 'J' and 'P'") 
    List<Person> findAllBetweenName();

     //Lista las personas que esten entre el rango de caracteres: Se establece parametros
     //@Query("select p from Person p where p.name between ?1 and ?2") 
     //@Query("select p from Person p where p.name between ?1 and ?2 order by p.name desc")  //order by p.name  -funcion de ordenar por el nombre por defecto es ascendente del 1 a 100 o A a Z, o se agrega: "asc" y "desc" para descendente
     @Query("select p from Person p where p.name between ?1 and ?2 order by p.name asc, p.lastname desc")   
     List<Person> findAllBetweenNameConParametros(String c1, String c2);

    //Hacer Between usando la anotacion Between
    List<Person>findByIdBetween(Long id1, Long id2);
    List<Person>findByNameBetween(String name1, String name2);

    //Hacer Between usando la anotacion Between con ordenamiento
    List<Person>findByIdBetweenOrderByIdDesc(Long id1, Long id2);
    List<Person>findByIdBetweenOrderByNameAsc(Long id1, Long id2);
    
    List<Person>findByNameBetweenOrderByNameAsc(String name1, String name2);
    List<Person>findByNameBetweenOrderByNameDescLastnameAsc(String name1, String name2);

    //Consulta personalizada con @Query para buscar por id
    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    //Consulta personalizada con @Query para buscar por nombre
    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    //Consulta personalizada con @Query para buscar por nombre por coincidencia no es necesario agregar todo el nombre, EJemplo: Buscar andres solo podemos agregar: And y lista las coincidencias
    @Query("select p from Person p where p.name like %?1%") //el % sig. buscar coincidencia antes y despues, el ?1 representa una variable.
    Optional<Person> findOneLikeName(String name);

    //Consulta personalizada con Query Methods para buscar por nombre por coincidencia no es necesario agregar todo el nombre, EJemplo: Buscar andres solo podemos agregar: And y lista las coincidencias
    //Optional<Person> findByName(String name); //Busca por nombre
    Optional<Person> findByNameContaining(String name); //Busca por coincidencias en el nombre, hace lo mismo que el metodo: findOneLikeName
    

    //Filtra a las personas que usan un lenguaje de programacion.
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    //Ejemplo con 2 paramettor de filtro por nombre y lenguaje de programacion
    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2") //Person es la clase, p el aleas de la clase, where donde NOTA: Como solo tenemos un parametro agregamos: 1? Si tuvieramos mas parametros seria asi:  @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);

    //Usando Anotacion findBy - Ejemplo con 2 paramettor de filtro por nombre o el lenguaje de programacion
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    //Ejemplo con 1 paramettor de filtro - Filtra personsa que usan un lenguaje de programacion
/*  @Query("select p from Person p where p.programmingLanguage=?1") 
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);
*/


//Usando palabras claves order by parte 2
//@Query("select p from Person p order by p.name desc") //se pude ordenar por id:  by p.id desc
//@Query("select p from Person p order by p.name desc, p.lastname asc")
@Query("select p from Person p order by p.name, p.lastname desc") //ordena el nombre y apellido descente para ambos
List<Person> getAllOrdered();

//Consulta equivalente a la anterior pero usando nombres del metodo / Query Methods
List<Person> findAllByOrderByNameDesc();


// ## Funciones JPQL de agregación count, max y min -Para numeros
//Long count(); //metodo que trae CRUD Repository que devuelve el numero de registros de una tabla
@Query("select count(p) from Person p")
Long getTotalPerson();

//Obtiene el id minimo de la tabla
@Query("select min(p.id) from Person p")
Long getMinId();

//Obtiene el id maximo de la tabla
@Query("select max(p.id) from Person p")
Long getMaxId();


// ## Funcion JPQL Length - Calcula la cantidad de caracteres de una palabra.
//Funcion devuelve el nombre en tipo string y largo (tamaño o cantidad) en caracteres
@Query("select p.name, length(p.name) from Person p")
public List<Object[]> getPersonNameLength(); //Es un objeto ya que guardará el nombre en la posicon [0], y la cantidad de caracteres en la posicon[1]

//Calcular el nombre mas corto
@Query("select min(length(p.name)) from Person p")
public Integer getMinLengthName();

//Calcular el nombre mas largo
@Query("select max(length(p.name)) from Person p")
public Integer getMaxLengthName();


//##Funciones JPQL de agregación count, sum, max, min, avg
@Query("select min(p.id), max(p.id), sum(p.id), avg(length(p.name)), count(p.id) from Person p")
public Object getResumeAggregationFunction();


//## Subquery - Subconsultas en JPQL
//Selecciona y muestra el nombre el tamaño(de caracteres) de personas donde el largo del nombre = al minimo de caracteres de nombre
@Query("select p.name, length(p.name) from Person p where length(p.name)=(select min(length(p.name)) from Person p)")
public List<Object[]> getShorterName();

//## Buscamos las personas donde su id sean los mas grande. Por lo tanto Consulta por el ultimo registro de persona 
@Query("select p from Person p where p.id= (select max(p.id) from Person p)")
public Optional<Person> getLastRegistration();


//## Operador Where in en JPQL
//Forma 1: id hardcodeados
// @Query("select p from Person p where p.id in (1,2,5)") 
//public List<Person> getPersonByIds();

//Forma 2: por variable recibe el id a filtrar
@Query("select p from Person p where p.id in ?1 ") // ?1 porque recibe una lista con los registros a filtrar.
public List<Person> getPersonByIds(List<Long> ids);

//Forma por variable recibe  id que no se va a filtrar: <> sig(not in)
@Query("select p from Person p where p.id not in ?1 ") // ?1 porque recibe una lista con los registros a no filtrar.
public List<Person> getPersonByIdsNoFiltrar(List<Long> ids);



//Devolviendo campos separados y valores del objeto entity
@Query("select p.name, p.programmingLanguage from Person p")
List<Object[]> obtenerPersonData();

//Devolviendo campos separados y valores del objeto entity - Envia parametros
@Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
List<Object[]> obtenerPersonData(String name);

//Devolviendo campos separados y valores del objeto entity - Envia parametros
@Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1")
List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);

//Devolviendo campos separados y valores del objeto entity - Envia parametros
@Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
List<Object[]> obtenerPersonData(String programmingLanguage, String name);




}
