package com.gerson.curso.springboot.jpa.springbootjparelationship;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.gerson.curso.springboot.jpa.springbootjparelationship.entities.Address;
import com.gerson.curso.springboot.jpa.springbootjparelationship.entities.Client;
import com.gerson.curso.springboot.jpa.springbootjparelationship.entities.ClientDetails;
import com.gerson.curso.springboot.jpa.springbootjparelationship.entities.Course;
import com.gerson.curso.springboot.jpa.springbootjparelationship.entities.Invoice;
import com.gerson.curso.springboot.jpa.springbootjparelationship.entities.Student;
import com.gerson.curso.springboot.jpa.springbootjparelationship.repositories.ClientDetailsRepository;
import com.gerson.curso.springboot.jpa.springbootjparelationship.repositories.ClientRepository;
import com.gerson.curso.springboot.jpa.springbootjparelationship.repositories.CourseRepository;
import com.gerson.curso.springboot.jpa.springbootjparelationship.repositories.InvoiceRepository;
import com.gerson.curso.springboot.jpa.springbootjparelationship.repositories.StudentRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner{ //ommandLineRunner para ejecutar la app en consola

	//Inyeccion de los repositorios que contiene los metodos para acceso a la BD
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//manytoOne();
		//manytoOneFindByIdClient();
		//oneToMany();
		//oneToManyFindById();
		//removeAddress();
		//removeAddressFindById();
		//oneToManyInvoiceBidireccional();
		//oneToManyInvoiceBidireccionalFindById();
		//removeInvoiceBidireccionalFindById();
		//removeInvoiceBidireccional();
		//OneToOne();
		//OneToOneFindById();
		//OneToOneBidireccional();
		//OneToOneBidireccionalFindById();

		//manyToMany();
		//manyToManyFindById();
		//manyToManyRemoveFindById();
		//manyToManyRemove();
		//manyToManyBidireccional();
		//manyToManyBidireccionalRemove();
		//manyToManyBidireccionalFindById();
		manyToManyRemoveBidireccionalFindById();

	}

			//Ejemplo buscar un student por id y se le elimina un cursos
			@Transactional
			public void manyToManyRemoveBidireccionalFindById(){
		
				//Buscamos el students para despues crear curso y asignarle curso a student
				Optional<Student> studentOptional1 = studentRepository.findOneWithCourses(1L); //Se busca el studiante con su curso - no se usa el findById de crud repository debido a que esta clase tiene relacion Bidireccional
				Optional<Student> studentOptional2 = studentRepository.findOneWithCourses(2L); 
		
				//Obtenemos los students
				Student student1 = studentOptional1.get();
				Student student2 = studentOptional2.get();
	
				//Ejemplo 1: se Crea los cursos para luego asignarlo a los students
				//Course course1 = new Course("Curso de Java master", "Andres");
				//Course course2 = new Course("Curso de Spring Boot", "Andres");
		
				//Ejemplo 2 se busca los cursos en la BD para luego asignarlo a los students
				Course course1 = courseRepository.findOneWithStudents(1L).get(); //Se busca el curso con su student - No se usa el findById de crudRepository porque Courses tiene relacion bidireccional con students
				Course course2 = courseRepository.findOneWithStudents(2L).get();
	
				//Asignacion de curso a students usando el metodo addCourse definido en Student xq tiene la relacion inversa para bidireccional
				student1.addCourse(course1);
				student1.addCourse(course2);
				student2.addCourse(course2);
		
				//Persistir el Student, como tiene en la anotacion definido cascade al guardar el Student se guardará su curso asignado.
				studentRepository.saveAll(List.of(student1, student2)); //Otra forma: (Set.of(student1, student2)); //SaveAll porque se guarda una lista de student, al guardar los student automaticamente se guardan los cursos porque en la anotacion @ManyToMany esta definido el Cascade
				System.out.println("\n" + student1 + "\n" + student2 + "\n"); //Se imprime los students


				//Ejemplo - Eliminar el curso 2 del student 1
			Optional<Student> studeOptionalDb = studentRepository.findOneWithCourses(1L);  //Se busca el student a eliminar
		
			if(studeOptionalDb.isPresent()){ //Valida si existe el student
				Student studentDb = studeOptionalDb.get(); //Obtine al student. otra opcion aparte de .get()  es usar: .orElseThrow();
				Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(1L); //Se busca el curso a eliminar
			
				//valida si encuentra el curso a eliminar
				if(courseOptionalDb.isPresent()){
				Course courseDb = courseOptionalDb.get();
				studentDb.removeCourse(courseDb); //Elimina el curso usando metodo removeCourse definido en Student, pero para eliminar el curso es necesario implementar el metodo: Equals y Hashcode en Course. Student es el padre y es necesario que el hijo (Course) tenga el metodo Equals and Hashcode para poder compararlo y eliminarlo.. Cuando exista relacion Bidireccional entoces en el padre  y el hijo deben tener el metodo Equals y HashCode

				studentRepository.save(studentDb); //Se guarda el student
				System.out.println("Studen modificado: " + studentDb); //Se imprime el student el cual se le elimino un curso.
			}
		}

	}


		//Ejemplo buscar un student por id y asignarle cursos
		@Transactional
		public void manyToManyBidireccionalFindById(){
	
			//Buscamos el students para despues crear curso y asignarle curso a student
			Optional<Student> studentOptional1 = studentRepository.findOneWithCourses(1L); //Se busca el studiante con su curso - no se usa el findById de crud repository debido a que esta clase tiene relacion Bidireccional
			Optional<Student> studentOptional2 = studentRepository.findOneWithCourses(2L); 
	
			//Obtenemos los students
			Student student1 = studentOptional1.get();
			Student student2 = studentOptional2.get();

			//Ejemplo 1: se Crea los cursos para luego asignarlo a los students
			//Course course1 = new Course("Curso de Java master", "Andres");
			//Course course2 = new Course("Curso de Spring Boot", "Andres");
	
			//Ejemplo 2 se busca los cursos en la BD para luego asignarlo a los students
			Course course1 = courseRepository.findOneWithStudents(1L).get(); //Se busca el curso con su student - No se usa el findById de crudRepository porque Courses tiene relacion bidireccional con students
			Course course2 = courseRepository.findOneWithStudents(2L).get();

			//Asignacion de curso a students usando el metodo addCourse definido en Student xq tiene la relacion inversa para bidireccional
			student1.addCourse(course1);
			student1.addCourse(course2);
			student2.addCourse(course2);
	
			//Persistir el Student, como tiene en la anotacion definido cascade al guardar el Student se guardará su curso asignado.
			studentRepository.saveAll(List.of(student1, student2)); //Otra forma: (Set.of(student1, student2)); //SaveAll porque se guarda una lista de student, al guardar los student automaticamente se guardan los cursos porque en la anotacion @ManyToMany esta definido el Cascade
			System.out.println("\n" + student1 + "\n" + student2 + "\n"); //Se imprime los students
		}


	@Transactional
	public void manyToManyBidireccionalRemove(){
		//Creamos students para despues crear curso y asignarle curso a lo students

		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		//Crea los cursos
		Course course1 = new Course("Curso de Java master", "Andres");
		Course course2 = new Course("Curso de Spring Boot", "Andres");

		//Asignacion de curso a students
		//student1.setCourses(Set.of(course1, course2));
		//student2.setCourses(Set.of(course2));

		//Asignacion de curso a students usando el metodo addCourse definido en Student xq tiene la relacion inversa para bidireccional
		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		//Persistir el Student, como tiene en la anotacion definido cascade al guardar el Student se guardará su curso asignado.
		studentRepository.saveAll(List.of(student1, student2)); //Otra forma: (Set.of(student1, student2)); //SaveAll porque se guarda una lista de student, al guardar los student automaticamente se guardan los cursos porque en la anotacion @ManyToMany esta definido el Cascade
		System.out.println("\n" + student1 + "\n" + student2 + "\n"); //Se imprime los students

		//Ejemplo - Eliminar el curso 2 del student 1
		Optional<Student> studeOptionalDb = studentRepository.findOneWithCourses(3L);  //Se busca el student a eliminar
		
		if(studeOptionalDb.isPresent()){ //Valida si existe el student
			Student studentDb = studeOptionalDb.get(); //Obtine al student. otra opcion aparte de .get()  es usar: .orElseThrow();
			Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(3L); //Se busca el curso a eliminar
			
			//valida si encuentra el curso a eliminar
			if(courseOptionalDb.isPresent()){
				Course courseDb = courseOptionalDb.get();
				studentDb.removeCourse(courseDb); //Elimina el curso usando metodo removeCourse definido en Student, pero para eliminar el curso es necesario implementar el metodo: Equals y Hashcode en Course. Student es el padre y es necesario que el hijo (Course) tenga el metodo Equals and Hashcode para poder compararlo y eliminarlo.. Cuando exista relacion Bidireccional entoces en el padre  y el hijo deben tener el metodo Equals y HashCode

				studentRepository.save(studentDb); //Se guarda el student
				System.out.println("Studen modificado: " + studentDb); //Se imprime el student el cual se le elimino un curso.
			}
		}

	}


	@Transactional
	public void manyToManyBidireccional(){
		//Creamos students para despues crear curso y asignarle curso a lo students

		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		//Crea los cursos
		Course course1 = new Course("Curso de Java master", "Andres");
		Course course2 = new Course("Curso de Spring Boot", "Andres");

		//Asignacion de curso a students
		//student1.setCourses(Set.of(course1, course2));
		//student2.setCourses(Set.of(course2));

		//Asignacion de curso a students usando el metodo addCourse definido en Student xq tiene la relacion inversa para bidireccional
		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		//Persistir el Student, como tiene en la anotacion definido cascade al guardar el Student se guardará su curso asignado.
		studentRepository.saveAll(Set.of(student1, student2)); //Otra forma: (List.of(student1, student2)); //SaveAll porque se guarda una lista de student, al guardar los student automaticamente se guardan los cursos porque en la anotacion @ManyToMany esta definido el Cascade
		System.out.println("\n" + student1 + "\n" + student2 + "\n"); //Se imprime los students
	}


	@Transactional
	public void manyToManyRemove(){
		//Creamos students para despues crear curso y asignarle curso a lo students

		Student student1 = new Student("Jano", "Pura");//Como ya existen 2 students en la Bd este seria el 3
		Student student2 = new Student("Erba", "Doe");//Como ya existen 2 students en la Bd este seria el 4

		//Crea los cursos
		Course course1 = new Course("Curso de Java master", "Andres"); //Como ya existen 2 cursos en la Bd este seria el 3
		Course course2 = new Course("Curso de Spring Boot", "Andres"); //Como ya existen 2 cursos en la Bd este seria el 4

		//Asignacion de curso a students
		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2)); 

		//Persistir el Student, como tiene en la anotacion definido cascade al guardar el Student se guardará su curso asignado.
		studentRepository.saveAll(List.of(student1, student2)); //Otra forma: (Set.of(student1, student2)); //SaveAll porque se guarda una lista de student, al guardar los student automaticamente se guardan los cursos porque en la anotacion @ManyToMany esta definido el Cascade
		System.out.println("\n" + student1 + "\n" + student2 + "\n"); //Se imprime los students

		//Nota: Usando Set el ingreso a la BD es de arriba hacia abajo. Usando List el insert a la BD es de abajo hacia arriba.

		//Ejemplo - Eliminar el curso 2 del student 1
		Optional<Student> studeOptionalDb = studentRepository.findOneWithCourses(3L);  //Se busca el student a eliminar
		
		if(studeOptionalDb.isPresent()){ //Valida si existe el student
			Student studentDb = studeOptionalDb.get(); //Obtine al student. otra opcion aparte de .get()  es usar: .orElseThrow();
			Optional<Course> courseOptionalDb = courseRepository.findById(3L); //Se busca el curso a eliminar
			
			//valida si encuentra el curso a eliminar
			if(courseOptionalDb.isPresent()){
				Course courseDb = courseOptionalDb.get();
				studentDb.getCourses().remove(courseDb); //Elimina el curso, pero para eliminar el curso es necesario implementar el metodo: Equals y Hashcode en Course. Student es el padre y es necesario que el hijo (Course) tenga el metodo Equals and Hashcode para poder compararlo y eliminarlo.. Cuando exista relacion Bidireccional entoces en el padre  y el hijo deben tener el metodo Equals y HashCode

				studentRepository.save(studentDb); //Se guarda el student
				System.out.println("Studen modificado: " + studentDb); //Se imprime el student el cual se le elimino un curso.
			}
		}
	}


	//Ejemplo eliminar un curso existente de un alumno
	@Transactional
	public void manyToManyRemoveFindById(){

		//Buscamos el student para despues eliminarle el curso que tiene asignado.
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		//Obtenemos los students
		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		//Ejemplo 1: se Crea los cursos para luego asignarlo a los students
		//Course course1 = new Course("Curso de Java master", "Andres");
		//Course course2 = new Course("Curso de Spring Boot", "Andres");

		//Ejemplo 2 se busca los cursos en la BD para luego asignarlo a los students
		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		//Asignacion de curso a students
		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		//Persistir el Student, como tiene en la anotacion definido cascade al guardar el Student se guardará su curso asignado.
		studentRepository.saveAll(List.of(student1, student2)); //Otra forma: (Set.of(student1, student2)); //SaveAll porque se guarda una lista de student, al guardar los student automaticamente se guardan los cursos porque en la anotacion @ManyToMany esta definido el Cascade
		System.out.println("\n" + student1 + "\n" + student2 + "\n"); //Se imprime los students


		//Ejemplo - Eliminar el curso 2 del student 1
		Optional<Student> studeOptionalDb = studentRepository.findOneWithCourses(1L);  //Se busca el student a eliminar
		
		if(studeOptionalDb.isPresent()){ //Valida si existe el student
			Student studentDb = studeOptionalDb.get(); //Obtine al student. otra opcion aparte de .get()  es usar: .orElseThrow();
			Optional<Course> courseOptionalDb = courseRepository.findById(2L); //Se busca el curso a eliminar
			
			//valida si encuentra el curso a eliminar
			if(courseOptionalDb.isPresent()){
				Course courseDb = courseOptionalDb.get();
				studentDb.getCourses().remove(courseDb); //Elimina el curso, pero para eliminar el curso es necesario implementar el metodo: Equals y Hashcode en Course. Student es el padre y es necesario que el hijo (Course) tenga el metodo Equals and Hashcode para poder compararlo y eliminarlo.. Cuando exista relacion Bidireccional entoces en el padre  y el hijo deben tener el metodo Equals y HashCode

				studentRepository.save(studentDb); //Se guarda el student
				System.out.println("Studen modificado: " + studentDb); //Se imprime el student el cual se le elimino un curso.
			}
		}

	}


	//Ejemplo buscar un student por id y asignarle cursos
	@Transactional
	public void manyToManyFindById(){

		//Buscamos el students para despues crear curso y asignarle curso a student
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		//Obtenemos los students
		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		//Ejemplo 1: se Crea los cursos para luego asignarlo a los students
		//Course course1 = new Course("Curso de Java master", "Andres");
		//Course course2 = new Course("Curso de Spring Boot", "Andres");

		//Ejemplo 2 se busca los cursos en la BD para luego asignarlo a los students
		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		//Asignacion de curso a students
		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		//Persistir el Student, como tiene en la anotacion definido cascade al guardar el Student se guardará su curso asignado.
		studentRepository.saveAll(List.of(student1, student2)); //Otra forma: (Set.of(student1, student2)); //SaveAll porque se guarda una lista de student, al guardar los student automaticamente se guardan los cursos porque en la anotacion @ManyToMany esta definido el Cascade
		System.out.println("\n" + student1 + "\n" + student2 + "\n"); //Se imprime los students
	}

	@Transactional
	public void manyToMany(){
		//Creamos students para despues crear curso y asignarle curso a lo students

		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		//Crea los cursos
		Course course1 = new Course("Curso de Java master", "Andres");
		Course course2 = new Course("Curso de Spring Boot", "Andres");

		//Asignacion de curso a students
		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		//Persistir el Student, como tiene en la anotacion definido cascade al guardar el Student se guardará su curso asignado.
		studentRepository.saveAll(Set.of(student1, student2)); //Otra forma: (List.of(student1, student2)); //SaveAll porque se guarda una lista de student, al guardar los student automaticamente se guardan los cursos porque en la anotacion @ManyToMany esta definido el Cascade
		System.out.println("\n" + student1 + "\n" + student2 + "\n"); //Se imprime los students
	}


	@Transactional
	public void OneToOneBidireccionalFindById(){
		//Ejemplo 1: OneToOne Bidireccional Definido en la clase ClientDetails(hija o dependiente) y Client(Padre o principal)	
		
  		//Busca el cliente
		Optional <Client> clientOptional = clientRepository.findOne(2L);  
		clientOptional.ifPresent(client -> {
			//Creamos el detalle del cliente
		ClientDetails clientDetails = new ClientDetails(true, 5000);
	
		//Forma 2: Asignar al cliente el clientDetails y asignar al clientDetails el client
		//Forma 2: En Client Se Modifica el Metodo setClientDetails se agrega: clientDetails.setClient(client);
		client.setClientDetails(clientDetails);//El client guarda el clientDetails
		
		clientRepository.save(client); //AL guardar el client se guarda el ClientDetails porque en Client se definio cascade para que al guardar o eliminar un client se guarda o elimina el ClientDetails.

		System.out.println(client + "\n");
		});
	}


	@Transactional
	public void OneToOneBidireccional(){
		//Ejemplo 1: OneToOne Bidireccional Definido en la clase ClientDetails(hija o dependiente) y Client(Padre o principal)	
		
  		//Creamos el cliente
		Client client = new Client("Erba", "pura");

		//Creamos el detalle del cliente
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		//clientDetailsRepository.save(clientDetails); //Esto no va porque en Client se definio que se guarda en cascada cuando se guarda el client se guarda el ClientDetails

		//Forma 1: Asignar al cliente el clientDetails y asignar al clientDetails el client
		//client.setClientDetails(clientDetails);//El client guarda el clientDetails
		//clientDetails.setClient(client);//El clientDetails guarda al client
		
		//Forma 2: Asignar al cliente el clientDetails y asignar al clientDetails el client
		//Forma 2: En Client Se Modifica el Metodo setClientDetails se agrega: clientDetails.setClient(client);
		client.setClientDetails(clientDetails);//El client guarda el clientDetails
		
		clientRepository.save(client); //AL guardar el client se guarda el ClientDetails porque en Client se definio cascade para que al guardar o eliminar un client se guarda o elimina el ClientDetails.

		System.out.println(client + "\n");
	}


	@Transactional
	public void OneToOneFindById(){
	//Ejemplo 1: OneToOne Unidireccional Definido en la clase Client
		//Creamos el detalle del cliente
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);
	
  		//Buscamos el cliente ya existente
		Optional<Client> clientOptional = clientRepository.findOne(2L); 
		clientOptional.ifPresent(client -> {
		//Asignar al cliente el clientDetails y persistir el client
		client.setClientDetails(clientDetails);
		clientRepository.save(client);
		System.out.println(client + "\n");
		});
		
	}

	@Transactional
	public void OneToOne(){
		//Ejemplo 1: OneToOne Unidireccional Definido en la clase ClientDetails
/*  	//Creamos el cliente
		Client client = new Client("Erba", "pura");
		clientRepository.save(client);

		//Creamos el detalle del cliente
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		
		//Asignar al clientDetails el cliente y persistir el clientDetails
		clientDetails.setClient(client);
		clientDetailsRepository.save(clientDetails);
 */	
		//Ejemplo 1: OneToOne Unidireccional Definido en la clase Client
		//Creamos el detalle del cliente
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);
	
  		//Creamos el cliente
		Client client = new Client("Erba", "pura");
		//Asignar al cliente el clientDetails y persistir el client
		client.setClientDetails(clientDetails);
		clientRepository.save(client);
		System.out.println(client + "\n");
	}


	//Ejemplo: Elimina objetos dependientes OneToMany Bidireccional - Crea el cliente 
@Transactional
public void removeInvoiceBidireccional(){

	//Transaccion 1: Crea al cliente y le asigna la factura.
	//Optional<Client> optionalClient = Optional.of(new Client("Fran", "Moras")); //Forma 1: creamos el cliente lo comvertimos en un optional y se lo asignamos.

	Client client = new Client("Fran", "Moras"); //Forma 2: creamos el cliente lo comvertimos en un optional y se lo asignamos.

	//optionalClient.ifPresent(client ->{ //Forma 1 con Optional
	
	Invoice invoice1 = new Invoice("compras de la casa", 5000L);
	Invoice invoice2 = new Invoice("compras de oficina", 8000L);

	//Se crea un metodo addInvoice en la clase Client
	client.addInvoice(invoice1).addInvoice(invoice2); //Metodo: public Client addInvoice

	//Guardamos el cliente: el cliente contiene las facturas.
	clientRepository.save(client); //Con save si el cliente existe entonces hace un Update porque el id ya existe.
	System.out.println(client);

	//}); //Forma 1 con Optional

	//Transaccion 2: Busca al cliente y le elimina la factura.
	Optional<Client> optionalClientBd = clientRepository.findOne(3L); //findOneWithInvoices(1L);
	
	optionalClientBd.ifPresent(clientDb -> { //client - Forma 1 con Optional

		// ejemplo se define la factura a eliminar: busaca por sus datos: description, total y el id.
		Invoice invoice3 = new Invoice("compras de la casa", 5000L);
		invoice3.setId(1L); //se envia el id de la factura a eliminar

		Optional<Invoice> invoiceOptional = Optional.of(invoice3); //convierte un objeto en un optional

		//Optional<Invoice> invoiceOptional = invoiceRepository.findById(2L); //se busca el id a eliminar, Para habilitar esto, eliminar el ejemplo de arriba.

		invoiceOptional.ifPresent(invoice -> {
			//Forma 1 de eliminar una factura
			//client.getInvoices().remove(invoice); //Eliminamos la factura del cliente,
			//invoice.setClient(null);//debido a la relacion inversa, se elimina el cliente de la factura se agrega en null
			
			//Forma 2 de eliminar una factura usando un metodo creado en la clase Client.
			clientDb.removeInvoice(invoice); //client - Forma 1 con Optional

			clientRepository.save(clientDb); //client - Forma 1 con Optional
			System.out.println(clientDb); //client - Forma 1 con Optional
		});
	});
}


//Ejemplo: Elimina objetos dependientes OneToMany Bidireccional - Busca el cliente por id para eliminar la factura
@Transactional
public void removeInvoiceBidireccionalFindById(){

	//Transaccion 1: Busca al cliente y le asigna la factura.
	Optional<Client> optionalClient = clientRepository.findOne(1L); //findOneWithInvoices(1L);

	optionalClient.ifPresent(client ->{
	
	Invoice invoice1 = new Invoice("compras de la casa", 5000L);
	Invoice invoice2 = new Invoice("compras de oficina", 8000L);

	//Se crea un metodo addInvoice en la clase Client
	client.addInvoice(invoice1).addInvoice(invoice2); //Metodo: public Client addInvoice

	//Guardamos el cliente: el cliente contiene las facturas.
	clientRepository.save(client); //Con save si el cliente existe entonces hace un Update porque el id ya existe.
	System.out.println(client);

	});

	//Transaccion 2: Busca al cliente y le elimina la factura.
	Optional<Client> optionalClientBd = clientRepository.findOne(1L); //findOneWithInvoices(1L);
	
	optionalClientBd.ifPresent(client -> {

		// ejemplo se define la factura a eliminar: busaca por sus datos: description, total y el id.
		Invoice invoice3 = new Invoice("compras de la casa", 5000L);
		invoice3.setId(1L); //se envia el id de la factura a eliminar

		Optional<Invoice> invoiceOptional = Optional.of(invoice3); //convierte un objeto en un optional

		//Optional<Invoice> invoiceOptional = invoiceRepository.findById(2L); //se busca el id a eliminar, Para habilitar esto, eliminar el ejemplo de arriba.

		invoiceOptional.ifPresent(invoice -> {
			//Forma 1 de eliminar una factura
			//client.getInvoices().remove(invoice); //Eliminamos la factura del cliente,
			//invoice.setClient(null);//debido a la relacion inversa, se elimina el cliente de la factura se agrega en null
			
			//Forma 2 de eliminar una factura usando un metodo creado en la clase Client.
			client.removeInvoice(invoice);

			clientRepository.save(client);
			System.out.println(client);
		});
	});
}

	//Codificando ejemplo OneToMany Bidireccional con relacion existente
	@Transactional
	public void oneToManyInvoiceBidireccionalFindById(){
		Optional<Client> optionalClient = clientRepository.findOne(1L); //findOneWithInvoices(1L);

		optionalClient.ifPresent(client ->{
		
		Invoice invoice1 = new Invoice("compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("compras de oficina", 8000L);

		//Forma 2 se crea un metodo addInvoice en la clase Client
		//client.addInvoice(invoice1); //Metodo void: public void addInvoice
		//client.addInvoice(invoice2);
		client.addInvoice(invoice1).addInvoice(invoice2); //Metodo: public Client addInvoice
	
		//Guardamos el cliente: el cliente contiene las facturas.
		clientRepository.save(client); //Con save si el cliente existe entonces hace un Update porque el id ya existe.
		System.out.println(client);

		});
	}


	@Transactional
	public void oneToManyInvoiceBidireccional(){
		Client client = new Client("Fran", "Moras");

		Invoice invoice1 = new Invoice("compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("compras de oficina", 8000L);

		//Forma se crea una lista en la cual se agregan las facturas para luego asignarlas al Client
/*		List<Invoice> invoices = new ArrayList<>();
		invoices.add(invoice1);
		invoices.add(invoice2);
		client.setInvoices(invoices); //Se le asigna la lista client por medio de setInvoices

		// Como es una relacion Bidireccional - OnoToMany a factura le debemos pasar el client
		invoice1.setClient(client);
		invoice2.setClient(client);

		//Guardamos el cliente: el cliente contiene las facturas.
		clientRepository.save(client);
		System.out.println(client);
 */
		//Forma 2 se crea un metodo addInvoice en la clase Client
		//client.addInvoice(invoice1); //Metodo void: public void addInvoice
		//client.addInvoice(invoice2);
		client.addInvoice(invoice1).addInvoice(invoice2); //Metodo: public Client addInvoice
	
		//Guardamos el cliente: el cliente contiene las facturas.
		clientRepository.save(client);
		System.out.println(client);
	}


	//Metodo busca un cliente existente y le agrega 2 direcciones y elimina una- para busca el id utiliza una consulta personalizada. .
	@SuppressWarnings("null")
	@Transactional
	public void removeAddressFindById(){
		Optional<Client> optionalClient = clientRepository.findById(2L);
		
		//Si el id del cliente esta presente.
		optionalClient.ifPresent(client -> {

		//Se definen las direcciones a asignar
		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		//Ejemplo tipo de dato de addresses en Cliente tipo Set - Obtenemos las direcciones y agregamos las direcciones 1 y 2.
		Set<Address> addresses = new HashSet<>(); //Esta linea de codigo se agrega xq se cambio el tipo de datos para addresses en la clase Client de tipo List a Set
		addresses.add(address1);
		addresses.add(address2);

		//Ejemplo tipo de dato de addresses en Cliente tipo List - Obtenemos las direcciones y agregamos las direcciones 1 y 2.
		//client.setAddresses(Arrays.asList(address1, address2));

		//Persistimos(guardamos el cliente en la BD) el cliente y automaticamente se guardará las direcciones asignadas esto gracias al CascadeType.ALL
		 clientRepository.save(client);
		 System.out.println("Cliente guardado con sus 2 direcciones" + client);
		
		//Optional<Client> optionalClient2 = clientRepository.findById(2L);  //Forma 1: Agregando en Client: en el OneToMany: fetch = FetchType.EAGER
		Optional<Client> optionalClient2 = clientRepository.findOneWithAddresses(2L);  //Forma 2: Usar una consulta personalizada para evitar usar el fetch = FetchType.EAGER en la anotacaion OneToMany en la clase Client
		
		optionalClient2.ifPresent(c -> {
		   c.getAddresses().remove(address2); //hace una consulta de las direcciones y elimina la direccion 1. el remove busca por instancia o direccionn en memoria por lo que no lo encuentra entonces en Address se agrega el metodo hash and equals para cambiar esa busqueda y compare por id
		   clientRepository.save(c); //Guarda la direccion
		   System.out.println(c);

		});
	});
	}


	@Transactional
	public void removeAddress(){
		Client client = new Client("Fran", "Moras");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		//Obtenemos las direcciones y agregamos las direcciones 1 y 2.
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		//Persistimos(guardamos el cliente en la BD) el cliente y automaticamente se guardará las direcciones asignadas esto gracias al CascadeType.ALL
		 clientRepository.save(client);
		 System.out.println("Cliente guardado con sus 2 direcciones" + client);

		 Optional<Client> optionalClient = clientRepository.findById(3L);
		 optionalClient.ifPresent(c -> {
			c.getAddresses().remove(address1); //hace una consulta de las direcciones y elimina la direccion 1. el remove busca por instancia o direccionn en memoria por lo que no lo encuentra entonces en Address se agrega el metodo hash and equals para cambiar esa busqueda y compare por id
			clientRepository.save(c); //Guarda la direccion
		 });
	}


	//Metodo busca un cliente existente y le agrega direccion.
	@SuppressWarnings("null")
	@Transactional
	public void oneToManyFindById(){
		Optional<Client> optionalClient = clientRepository.findById(2L);

		//Si el id del cliente esta presente.
		optionalClient.ifPresent(client -> {

		//Se definen las direcciones a asignar
		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		//Ejemplo tipo de dato de addresses en Cliente tipo List - Obtenemos las direcciones y agregamos las direcciones 1 y 2.
		//client.setAddresses(Arrays.asList(address1, address2));

		//Ejemplo tipo de dato de addresses en Cliente tipo Set - Obtenemos las direcciones y agregamos las direcciones 1 y 2.
		Set<Address> addresses = new HashSet<>(); //Esta linea de codigo se agrega xq se cambio el tipo de datos para addresses en la clase Client de tipo List a Set
		addresses.add(address1);
		addresses.add(address2);

		//Persistimos(guardamos el cliente en la BD) el cliente y automaticamente se guardará las direcciones asignadas esto gracias al CascadeType.ALL
		 clientRepository.save(client);
		 System.out.println("Cliente guardado con sus 2 direcciones" + client);
		});
		
	}


	@Transactional
	public void oneToMany(){
		Client client = new Client("Fran", "Moras");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9875);

		//Obtenemos las direcciones y agregamos las direcciones 1 y 2.
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		//Persistimos(guardamos el cliente en la BD) el cliente y automaticamente se guardará las direcciones asignadas esto gracias al CascadeType.ALL
		 clientRepository.save(client);
		 System.out.println("Cliente guardado con sus 2 direcciones" + client);
	}


	//Crea cliente y crea una factura con un cliente 
	@Transactional //para metodos que modifiquen save, update, ahora solo para Select es: @Transactional(readOnly = true) 
	public void manytoOne(){
		Client client = new Client("John", "Doe");
		clientRepository.save(client); //Guardamos el cliente instanciado anteriormente.

		Invoice invoice = new Invoice("Compras de oficina", 2000L);
		invoice.setClient(client); //Asignamos el cliente anterior John Doe a esta factura.
		Invoice invoiceDB = invoiceRepository.save(invoice);
		System.out.println(invoiceDB);
	}

	//Buscar cliente y si esta presente le asignamos una factura
	@Transactional
	public void manytoOneFindByIdClient(){
		Optional<Client> optionalClient = clientRepository.findById(1L); //se envia el id del cliente
		
		if(optionalClient.isPresent()){
			Client client = optionalClient.orElseThrow();

			Invoice invoice = new Invoice("Compras de oficina", 2000L);
			invoice.setClient(client); //Asignamos el cliente anterior John Doe a esta factura.
			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);
		}
		
	}

}
