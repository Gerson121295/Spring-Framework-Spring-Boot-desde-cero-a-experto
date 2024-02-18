package com.gerson.springboot.jpa.springbootjpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.gerson.springboot.jpa.springbootjpa.dto.PersonDto;
import com.gerson.springboot.jpa.springbootjpa.entities.Person;
import com.gerson.springboot.jpa.springbootjpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{//CommandLineRunner Para habilitar la consola y probar registros en la consola

	@Autowired
	private PersonRepository repository; //Inyeccion de PersonRepository para acceder a sus metodos.

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//list(); //acceso a los metodos del metodo list()
		//findOne();
		//create();
		//update();
		//delete();
		//delete2();
		//personalizedQueries();
		//personalizedQueries2();
		//personalizedQueriesDistinct();
		//personalizedQueriesConcatUpperAndLowerCase();
		//personalizedQueriesBetween();
		//queriesFuncionAggregation();
		//subQueries();
		//whereIn();
		//whereNotIn();

		//create();
		update();

	}



	@Transactional(readOnly = true)
	public void whereIn(){
		System.out.println();
		System.out.println("============ Consulta Where in - selecciona registros por id (1,2,5) ============");
		//List<Person> persons = repository.getPersonByIds(); //Forma 1: Los id harcodeados en la consulta
		List<Person> persons = repository.getPersonByIds(Arrays.asList(1L,2L,5L)); //Forma 2: Recibe una lista con los ids a filtrar
		persons.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void whereNotIn(){
		System.out.println();
		System.out.println("============ Consulta Where not in - selecciona registros por id (1,2,5)  que no se van a filtrar ============");
		List<Person> persons = repository.getPersonByIdsNoFiltrar(Arrays.asList(1L,2L,5L)); //Forma 2: Recibe una lista con los ids a filtrar
		persons.forEach(System.out::println);
	}


	@Transactional(readOnly = true)
	public void subQueries(){
		System.out.println();
		System.out.println("============ Consulta por el nombre mas corto y su largo(cantidad de caracteres )============");
		List<Object[]> registers = repository.getShorterName();
		registers.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name: "+ name + ", length= " + length);
		});

		System.out.println("============ Consulta por el ultimo registro de persona ============");
		Optional<Person> optionalPerson = repository.getLastRegistration();
		optionalPerson.ifPresent(System.out::println);
		System.out.println();
	}



	@Transactional(readOnly = true)
	public void queriesFuncionAggregation(){

		System.out.println();
		System.out.println("============ Consulta con el total de registros en la tabla ============");
		Long count = repository.getTotalPerson();
		System.out.println(count);

		System.out.println();
		System.out.println("============ Consulta con el valor minimo del id ============");
		Long min = repository.getMinId();
		System.out.println(min);

		System.out.println();
		System.out.println("============ Consulta con el valor maximo del id  ============");
		Long max = repository.getMaxId();
		System.out.println(max);

		System.out.println();
		System.out.println("============ Consulta con el nombre y su largo(cantidad de caracteres)  ============");
		List<Object[]> regs = repository.getPersonNameLength();
		regs.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name: "+ name + ", length= " + length);
		});

		System.out.println();
		System.out.println("============ Consulta con el nombre mas corto ============");
		Integer minLengthName = repository.getMinLengthName();
		System.out.println(minLengthName);

		System.out.println();
		System.out.println("============ Consulta con el nombre mas largo ============");
		Integer maxLengthName = repository.getMaxLengthName();
		System.out.println(maxLengthName);

		System.out.println();
		System.out.println("============ Consulta resumen de funciones de agregacion: min, max, sum, avg, count ============");
		Object[] resumeReg = (Object[]) repository.getResumeAggregationFunction();
		System.out.println("min="+ resumeReg[0] + 
							", max=" + resumeReg[1] + 
							", sum=" + resumeReg[2] + 
							", avg=" + resumeReg[3] + 
							", count=" + resumeReg[4]);

		System.out.println();
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesBetween(){
		System.out.println();
		System.out.println("============ Consulta por rangos de id  ============");
		List<Person> persons = repository.findAllBetweenId();
		persons.forEach(System.out::println);

		System.out.println();
		System.out.println("============ Consulta por rangos de id dados por los parametros ============");
		persons = repository.findAllBetweenIdConParametros(6, 8);
		persons.forEach(System.out::println);


		System.out.println();
		System.out.println("============ Consulta por rangos de caracteres en los nombres desde la 'J' a la 'P' ============");
		persons = repository.findAllBetweenName();
		persons.forEach(System.out::println);

		System.out.println();
		System.out.println("============ Consulta por rangos de caracteres en los nombres desde x letra a Y segun los parametros ============");
		persons = repository.findAllBetweenNameConParametros("J", "Q");
		persons.forEach(System.out::println);

		System.out.println();
		System.out.println("============ Consulta por rangos de id dados por los parametros  usando anotacion Between ============");
		//persons = repository.findByIdBetween(6L, 8L); 
		//persons = repository.findByIdBetweenOrderByIdDesc(6L, 8L); //Ejemplo con orden por id de forma descendente
		persons = repository.findByIdBetweenOrderByNameAsc(6L, 8L);
		persons.forEach(System.out::println);

		System.out.println();
		System.out.println("============ Consulta por rangos de caracteres en los nombres desde x letra a Y segun los parametros usnado anotacion Between ============");
		//persons = repository.findByNameBetween("J", "Q");
		//persons = repository.findByNameBetweenOrderByNameAsc("J", "Q");
		persons = repository.findByNameBetweenOrderByNameDescLastnameAsc("J", "Q");
		persons.forEach(System.out::println);

		System.out.println();
		System.out.println("========= select p from Person p order by p.name desc, p.lastname asc - Lista todas Personas pero ordena por nombre desc y apellido asc =========");
		//persons = repository.getAllOrdered(); //Consulta usando la anotacion @Query
		persons = repository.findAllByOrderByNameDesc(); //Consulta usando nombre del metoto - Query Methods
		persons.forEach(System.out::println);

		System.out.println();
	}


	@Transactional(readOnly = true)
	public void personalizedQueriesConcatUpperAndLowerCase(){
		System.out.println();
		System.out.println("============ Consulta con nombres y apellidos de personas Concatena nombre y apellido usa CONCAT ============");
		List<String> names = repository.findAllFullNameConcat();
		names.forEach(System.out::println);

		System.out.println();
		System.out.println("============ Consulta con nombres y apellidos de personas Concatena nombre y apellido usa  || ============");
		List<String> names2 = repository.findAllFullNameConcatconBarras();
		names2.forEach(System.out::println);

		System.out.println();
		System.out.println("============ Consulta nombres y apellidos Mayuscula ============");
		names = repository.findAllFullNameConcatUpper();
		names.forEach(System.out::println);

		System.out.println();
		System.out.println("============ Consulta nombres y apellidos Minuscula ============");
		names = repository.findAllFullNameConcatLower();
		names.forEach(System.out::println);

		System.out.println();
		System.out.println("============ Consulta personalizada personas upper y lower case ============");
		List<Object[]> regs = repository.findAllPersonDataListCase();
		regs.forEach(reg -> System.out.println("id=" + reg[0] + ", nombre=" + reg[1] + ", apellido=" + reg[2] + ", Lenguaje=" + reg[3]));

		System.out.println();
	}




	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct(){
		System.out.println();
		System.out.println("============ Consulta con nombres de personas  ============");
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);

		System.out.println();
		System.out.println("============ Consulta con nombres unicos de personas  ============");
		names = repository.findAllNamesDistinct();
		names.forEach(System.out::println);
	
		System.out.println();
		System.out.println("============ Consulta con Lenguages de programacion unicos ============");
		List<String> languages = repository.findAllProgrammingLanguageDistinct();
		languages.forEach(System.out::println);

		System.out.println();
		System.out.println("============ Consulta con total de Lenguages de programacion unicos  ============");
		Long totalLanguage = repository.findAllProgrammingLanguageDistinctCount();
		System.out.println("Total de lenguage de programacion: " + totalLanguage);

		System.out.println();
	}


	@Transactional(readOnly = true) //la transaccion solo es de consulta
	public void personalizedQueries2(){
		System.out.println();
		System.out.println("=================== Consulta por persona y lenguaje de programacion ===================");
		List<Object[]> personsRegs = repository.FindAllMixPerson();

		personsRegs.forEach(reg -> {
			System.out.println("ProgrammigLanguage=" + reg[1] + ", person=" + reg[0]);
		});

		System.out.println();
		System.out.println("=========== Consulta que puebla y devuelve objeto entity de una instancia personalizada, solo da nombre y apellido lo demas es null =============");
		List<Person> persons = repository.findAllObjectPersonPersonalized();
		persons.forEach(System.out::println);  //forEach(person -> System.out.println(person));

		
		System.out.println();
		System.out.println("=========== Consulta que puebla y devuelve objeto dto de una clase personalizada  ============");
		List<PersonDto> personsDto = repository.findAllObjectPersonDto();
		personsDto.forEach(System.out::println);  //forEach(person -> System.out.println(person));


		System.out.println();
	}


	@Transactional(readOnly = true) //la transaccion solo es de consulta
	public void personalizedQueries(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("=================== Consulta solo el nombre por el id ===================");
		System.out.println("Ingrese el id para el nombre");
		Long id = scanner.nextLong();

		//Para obtener el nombre 
		System.out.println("===== mostrando solo el nombre ======");
		String name = repository.getNameById(id);
		System.out.println(name);

		//Para obtener el nombre 
		System.out.println("===== mostrando solo el id ======");
		Long idBD = repository.getIdById(id);
		System.out.println(idBD);

		//Para obtener el nombre y apellido concatenado
		System.out.println("===== mostrando solo el nombre y apellido concatenado String ======");
		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

		System.out.println("===== Consulta por campos personalizados por el id =====");
		//Forma 1: sin optional
		//Object[] personReg = (Object[]) repository.obtenerPersonDataById(id);
		//System.out.println("id=" + personReg[0] + ", nombre=" + personReg[1] + ", apellido=" + personReg[2] + ", Lenguaje=" + personReg[3]);
		//Forma 2: Usnado Optional
		Optional<Object> optionalReg = repository.obtenerPersonDataById(id);
		if(optionalReg.isPresent()){
			Object[] personReg = (Object[]) optionalReg.orElseThrow();
		System.out.println("id=" + personReg[0] + ", nombre=" + personReg[1] + ", apellido=" + personReg[2] + ", Lenguaje=" + personReg[3]);
		}

		System.out.println("===== Consulta por campos personalizados lista =====");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(reg -> System.out.println("id=" + reg[0] + ", nombre=" + reg[1] + ", apellido=" + reg[2] + ", Lenguaje=" + reg[3]));

		
		scanner.close();
	}


		//Forma 2 del Delete
		@Transactional
		public void delete2(){
	
			repository.findAll().forEach(System.out::println); //listar los registros antes de eliminar
	
			Scanner scanner = new Scanner(System.in);
			System.out.println("Ingrese el id a eliminar");
			Long id = scanner.nextLong();

			Optional<Person> optionalPerson = repository.findById(id);

			//Elimina el registro en caso que este presente
			//optionalPerson.ifPresent(person -> repository.delete(person));
			//Elimina el registro en caso que este presente si no esta presente muestra el mensaje
			optionalPerson.ifPresentOrElse(
			repository::delete,	// person -> repository.delete(person),  //Esta linea de codigo de Otra forma:
			() -> System.out.println("Lo sentimos no existe la persona con ese ID."));
			
			repository.findAll().forEach(System.out::println); //listar los registros despues de eliminar
	
			scanner.close(); //cerrar el scanner
		}

	//Forma 1 del Delete
	@Transactional
	public void delete(){

		repository.findAll().forEach(System.out::println); //listar los registros antes de eliminar

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar");

		Long id = scanner.nextLong();
		repository.deleteById(id);

		repository.findAll().forEach(System.out::println); //listar los registros despues de eliminar

		scanner.close(); //cerrar el scanner

	}

	@Transactional
	public void update(){
		//Solicitamos el id de la persona a editar
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a editar");
		Long id = scanner.nextLong();

/* 
		//Forma 1 
		//Obtenemos la persona a modificar
		Optional<Person> optionalPerson = repository.findById(id); 
		optionalPerson.ifPresent(person -> {
		//muestra los datos de la persona encontrada con el id dado
			System.out.println(person); 
		//Modificacion del camo de la persona	
			System.out.println("Ingrese el lenguaje de programacion: ");
			String programmingLanguage = scanner.next();
			person.setProgrammingLanguage(programmingLanguage); //Modificamos el campo con setProgrammingLanguage y le pasamos su nuevo valor
			Person personDb = repository.save(person);

			//Imprime el objeto modificado
			System.out.println(personDb); 
		});

		scanner.close(); //cerrar el scanner
*/

		//Forma 2 con if y validar si el id no se encuentra
			//Obtenemos la persona a modificar
			Optional<Person> optionalPerson = repository.findById(id); 

			if(optionalPerson.isPresent()){	
				Person personDB = optionalPerson.orElseThrow();
			//muestra los datos de la persona encontrada con el id dado
				System.out.println(personDB); 
			//Modificacion del camo de la persona	
				System.out.println("Ingrese el lenguaje de programacion: ");
				String programmingLanguage = scanner.next();
				personDB.setProgrammingLanguage(programmingLanguage); //Modificamos el campo con setProgrammingLanguage y le pasamos su nuevo valor
				Person personDb = repository.save(personDB);
	
				//Imprime el objeto modificado
				System.out.println(personDb); 
			}else{
				System.out.println("El usuario no esta presente! no existe! ");
			}

			scanner.close(); //cerrar el scanner
	}


	//Creacion de un objeto
	@SuppressWarnings("null")
	@Transactional //Save o delete solo se define transaccional ahora cuando son consultas: select, al transactional se agrega: @Transactional(readOnly = true)  Transactional todo es una sola transaccional,
	public void create(){
		
	/* //FOrma 1 de crear un registro
		Person person = new Person(null, "Lalo", "Thor", "Phyton");
		Person personNew = repository.save(person); //Guardar un nueva entidad en la BD, si el id es nulo lo crea el registro, si no es nulo crea el registro.
		System.out.println(personNew);
	*/	
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre");
		String name = scanner.next();

		System.out.println("Ingrese el apellido");
		String lastname = scanner.next();

		System.out.println("Ingrese el Lenguaje de programacion");
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name,  lastname, programmingLanguage);
		Person personNew = repository.save(person);
		System.out.println(personNew);

		//Mostrar el id del registro que se creo
		repository.findById(personNew.getId()).ifPresent(System.out::println);  //otra forma: (p -> System.out.println(p));

	}


	//Obtiene un solo objeto
	@Transactional(readOnly = true)
	public void findOne(){

		//Person person = repository.findById(1L).orElseThrow(); //Forma 1: el findById si no esta devuelve un optional vacio, si esta devuelve el id devuelve un optional. con orElseThrow() si no esta el id devuelve una exception
		
		//Forma 2 devuelve un Optional de persona y con esto ya lo puedo validar con el if
/*   	Person person = null;
		Optional<Person> optionalPerson = repository.findById(2L);
		if(optionalPerson.isPresent()){ //si esta presente el id lo obtenemos con .get() //Otra forma: if(!optionalPerson.isEmpty()){//Si no esta vacio
			person = optionalPerson.get();
		}else{
			System.out.println("Id no esta en la BD");
		}
		System.out.println(person);
*/	

		//Forma 3 con validacion, Si el id esta presente imprime la persona.
		//repository.findById(1L).ifPresent(person -> System.out.println(person)); 
		//otra forma
		//repository.findById(1L).ifPresent(System.out::println); 

		//repository.findOne(1L).ifPresent(System.out::println); //Usando otro metodo para buscar por id
		//repository.findOneName("Maria").ifPresent(System.out::println); //Usando otro metodo para buscar por Nombre
		//repository.findOneLikeName("Pe").ifPresent(System.out::println); //Usando otro metodo para buscar por Nombre pero por coincidencia si agregamos: Jos aparece Josefa


		//repository.findByName("Pepe").ifPresent(System.out::println); //Busca por nombre
		repository.findByNameContaining("and").ifPresent(System.out::println); //Busca por coincidencias del nombre, si agregamos: Jos aparece Josefa
	
	}

	//Este metodo contiene todos los metodos de filtro
	public void list(){
		//List<Person> persons = (List<Person>) repository.findAll(); //lista todas las personas
		//List<Person> persons = (List<Person>) repository.findByProgrammingLanguage("javaScript"); //Filtra a las personas que usan un lenguaje de programacion. Ej: Java
		
		//List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("Java");//usando Query, filtra por nombre de lenguaje de programacion a usar 
		//List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("Java", "Andres");//usando Query filtra por nombre de lenguaje de programacion a usar y el nombre de la persona (tiene que estar los 2 parametros para retornar)
	
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Andres");//usando anotaciones findBy filtra por nombre de lenguaje de programacion a usar y el nombre de la persona
		persons.stream().forEach(person -> System.out.println(person));

		//Devolviendo campos separados y valores del objeto entity
		List<Object[]> personsValues = repository.obtenerPersonData("Maria"); //Se puede enviar los parametros segun los metodos sobrecargados para este metodo
		personsValues.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]); //Obtenemos el nombre y el lenguaje de programacion
		});


		//Devolviendo campos separados y valores del objeto entity
		List<Object[]> personsValues1 = repository.obtenerPersonData("Java", "Andres");
		personsValues1.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]); //Obtenemos el nombre y el lenguaje de programacion
		});
 

	}

}


