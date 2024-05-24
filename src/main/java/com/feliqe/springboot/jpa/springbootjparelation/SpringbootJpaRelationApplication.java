package com.feliqe.springboot.jpa.springbootjparelation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.feliqe.springboot.jpa.springbootjparelation.entities.Address;
import com.feliqe.springboot.jpa.springbootjparelation.entities.Client;
import com.feliqe.springboot.jpa.springbootjparelation.entities.ClientDetails;
import com.feliqe.springboot.jpa.springbootjparelation.entities.Course;
import com.feliqe.springboot.jpa.springbootjparelation.entities.Invoice;
import com.feliqe.springboot.jpa.springbootjparelation.entities.Student;
import com.feliqe.springboot.jpa.springbootjparelation.repositories.ClientDetailsRepository;
import com.feliqe.springboot.jpa.springbootjparelation.repositories.ClientRepository;
import com.feliqe.springboot.jpa.springbootjparelation.repositories.CourseRepository;
import com.feliqe.springboot.jpa.springbootjparelation.repositories.InvoiceRepository;
import com.feliqe.springboot.jpa.springbootjparelation.repositories.StudentRepository;

@SpringBootApplication
public class SpringbootJpaRelationApplication implements CommandLineRunner{

	//creamos los dos acceso para los repositorios
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	//inyectamos el repositorio que esta unido a la clase es para poder usarlo y realizar la relacion
	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	// inyectamos el repositorio que esta unido a la clase es para poder usarlo y realizar la relacion
	@Autowired
	private StudentRepository studentRepository;

	// inyectamos el repositorio que esta unido a la clase es para poder usarlo y realizar la relacion
	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationApplication.class, args);
	}

	//funcion para indicar que metodo se ejecutara
	@Override
	public void run(String... args) throws Exception {
		// manyToOne();
		// manyToOneFindByIdClient();
		// oneToMany();
		// oneToManyFindById();
		// removeAddress();
		// removeAddressFindById();
		// oneToManyInvoiceBidireccional();
		// oneToManyInvoiceBidireccionalFindById();
		// removeInvoiceBidireccionalFindById();
		// removeInvoiceBidireccional();
		// OneToOne();
		// OneToOneFindById();
		// OneToOneBidireccional();
		// OneToOneBidireccionalFindById();
		// manyToMany();
		// manyToManyFind();
		// manyToManyRemoveFind();
		// manyToManyRemove();
		// manyToManyBidireccional();
		// manyToManyBidireccionalRemove();
		// manyToManyBidireccionalFind();
		manyToManyRemoveBidireccionalFind();
	}

	// buscar estudiantes y cursos para relacion de muchos a muchos para eliminar
	@Transactional
	public void manyToManyRemoveBidireccionalFind() {
		Optional<Student> studentOptional1 = studentRepository.findOneWithCourses(1L);
		Optional<Student> studentOptional2 = studentRepository.findOneWithCourses(2L);

		// definir los usuarios
		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findOneWithStudents(1L).get();
		Course course2 = courseRepository.findOneWithStudents(2L).get();

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);
		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);

		// metodo para buscar a eliminar
		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourses(1L);
		if (studentOptionalDb.isPresent()) {
			Student studentDb = studentOptionalDb.get();
			// buscamos el curso por el id
			Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(1L);
			// proceos para eliminar
			if (courseOptionalDb.isPresent()) {
				Course courseDb = courseOptionalDb.get();
				studentDb.getCourses().remove(courseDb);

				studentRepository.save(studentDb);
				System.out.println(studentDb);
			}
		}
	}

	// buscar estudiantes y cursos para relacion de muchos a muchos
	@Transactional
	public void manyToManyBidireccionalFind() {
		Optional<Student> studentOptional1 = studentRepository.findOneWithCourses(1L);
		Optional<Student> studentOptional2 = studentRepository.findOneWithCourses(2L);

		// definir los usuarios
		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findOneWithStudents(1L).get();
		Course course2 = courseRepository.findOneWithStudents(2L).get();

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);
		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	// creamos el studiante y el curso consulta bidireccional para eliminar
	@Transactional
	public void manyToManyBidireccionalRemove() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de java master", "Felipe");
		Course course2 = new Course("Curso de Spring Boot", "Felipe");

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);
		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);

		// metodo para buscar a eliminar
		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourses(3L);
		if (studentOptionalDb.isPresent()) {
			Student studentDb = studentOptionalDb.get();
			// buscamos el curso por el id
			Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(3L);
			// proceos para eliminar
			if (courseOptionalDb.isPresent()) {
				Course courseDb = courseOptionalDb.get();
				studentDb.removeCourse(courseDb);

				studentRepository.save(studentDb);
				System.out.println(studentDb);
			}
		}
	}

	// creamos el studiante y el curso consulta bidireccional
	@Transactional
	public void manyToManyBidireccional() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de java master", "Felipe");
		Course course2 = new Course("Curso de Spring Boot", "Felipe");

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);
		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	// creamos el studiante y el curso con Set para eliminar
	@Transactional
	public void manyToManyRemove() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de java master", "Felipe");
		Course course2 = new Course("Curso de Spring Boot", "Felipe");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);

		// metodo para buscar a eliminar
		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourses(3L);
		if (studentOptionalDb.isPresent()) {
			Student studentDb = studentOptionalDb.get();
			// buscamos el curso por el id
			Optional<Course> courseOptionalDb = courseRepository.findById(3L);
			// proceos para eliminar
			if (courseOptionalDb.isPresent()) {
				Course courseDb = courseOptionalDb.get();
				studentDb.getCourses().remove(courseDb);

				studentRepository.save(studentDb);
				System.out.println(studentDb);
			}
		}
	}

	//buscar estudiantes y cursos para poder eliminar por relacion de muchos a muchos
	@Transactional
	public void manyToManyRemoveFind() {
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		// definir los usuarios
		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);

		//metodo para buscar a eliminar
		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourses(1L);
		if (studentOptionalDb.isPresent()) {
			Student studentDb = studentOptionalDb.get();
			//buscamos el curso por el id
			Optional<Course> courseOptionalDb = courseRepository.findById(2L);
			//proceos para eliminar
			if(courseOptionalDb.isPresent()){
				Course courseDb = courseOptionalDb.get();
				studentDb.getCourses().remove(courseDb);

				studentRepository.save(studentDb);
				System.out.println(studentDb);
			}
		}
	}

	// buscart estudiantes y cursos para relacion de muchos a muchos
	@Transactional
	public void manyToManyFind() {
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		//definir los usuarios
		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	//creamos el studiante y el curso con Set
	@Transactional
	public void manyToMany() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de java master", "Felipe");
		Course course2 = new Course("Curso de Spring Boot", "Felipe");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	// consulta bidireccional relacional uno a uno buscanod por el id
	@Transactional
	public void OneToOneBidireccionalFindById() {
		Optional<Client> clientOptional = clientRepository.findOne(1L);
		clientOptional.ifPresent(client -> {
			ClientDetails clientDetails = new ClientDetails(true, 5000);
			client.setClientDetails(clientDetails);
			clientRepository.save(client);
			System.out.println(client);
		});
	}

	//consulta bidireccional relacional uno a uno creando el cliente
	@Transactional
	public void OneToOneBidireccional() {
		Client client = new Client("Erba", "Pura");
		ClientDetails clientDetails = new ClientDetails(true, 5000);

		client.setClientDetails(clientDetails);
		clientDetails.setClient(client);
		clientRepository.save(client);
		System.out.println(client);
	}

	//consulta  de uno a uno buscando por el id - llena los campos con null
	@Transactional
	public void OneToOneFindById() {
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Optional<Client> clientOptional = clientRepository.findOne(2L);
		clientOptional.ifPresent(client -> {
			client.setClientDetails(clientDetails);
			clientRepository.save(client);

			System.out.println(client);
		});
	}
	//consulta de uno a uno creando el cliente - llena los campos con null
	@Transactional
	public void OneToOne() {
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Erba", "Pura");
		client.setClientDetails(clientDetails);
		clientRepository.save(client);

		System.out.println(client);
	}

	// consulta birideccional - crear el cliente y asociando nueva factura para eliminarlo
	@Transactional
	public void removeInvoiceBidireccional() {
		Client client = new Client("Fran", "Moras");
		Invoice invoice1 = new Invoice("compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("compras de la oficina", 8000L);

		client.addInvoices(invoice1).addInvoices(invoice2);
		clientRepository.save(client);
		System.out.println(client);

		Optional<Client> optionalClientBd = clientRepository.findOne(3L);

		optionalClientBd.ifPresent(clientDb -> {
			// consultar por descripcion
			Invoice invoice3 = new Invoice("compras de la casa", 5000L);
			invoice3.setId(2L);

			Optional<Invoice> invoiceOptional = Optional.of(invoice3);// invoiceRepository.findById(1L);
			invoiceOptional.ifPresent(invoice -> {
				// eliminamos la factura y asociacion al cliente
				clientDb.removeInvoice(invoice);
				clientRepository.save(clientDb);
				System.out.println(clientDb);
			});
		});
	}

	// consulta birideccional - buscar por id y asociando nueva factura para eliminar
	@Transactional
	public void removeInvoiceBidireccionalFindById() {
		// buscamo cliente y creamos campos nuevo de factura
		Optional<Client> optionalClient = clientRepository.findOne(1L);
		// validamos que exista los datos
		optionalClient.ifPresent(client -> {
			Invoice invoice1 = new Invoice("compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("compras de la oficina", 8000L);

			client.addInvoices(invoice1).addInvoices(invoice2);
			clientRepository.save(client);
			System.out.println(client);
		});

		Optional<Client> optionalClientBd = clientRepository.findOne(1L);

		optionalClientBd.ifPresent(client -> {
			//consultar por descripcion
			Invoice invoice3 = new Invoice("compras de la casa", 5000L);
			invoice3.setId(2L);

			Optional<Invoice> invoiceOptional = Optional.of(invoice3);//invoiceRepository.findById(1L);
			invoiceOptional.ifPresent(invoice -> {
				//eliminamos la factura y asociacion al cliente
				client.removeInvoice(invoice);
				clientRepository.save(client);
				System.out.println(client);
			});
		});
	}

	// consulta birideccional - buscar por id y asociando nueva factura
	@Transactional
	public void oneToManyInvoiceBidireccionalFindById() {
		// buscamo cliente y creamos campos nuevo de factura
		Optional<Client> optionalClient = clientRepository.findOne(2L);
		//validamos que exista los datos
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("el verjer", 1234);
			Address address2 = new Address("vasco de gama", 9876);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);

			clientRepository.save(client);
			System.out.println(client);

			//buscamos los datos en client
			Optional<Client> optionalClient2 = clientRepository.findOneWithAddress(2L);
			optionalClient2.ifPresent(c -> {
				//indicamos cual eliminar
				c.getAddresses().remove(address2);
				clientRepository.save(c);
				System.out.println(c);
			});
		});
	}

	//consulta birideccional - creando cliente y asociando nueva factura
	@Transactional
	public void oneToManyInvoiceBidireccional() {
		// valores de los campos a crear de client y invoices
		Client client = new Client("Fran", "Moras");
		Invoice invoice1 = new Invoice("compas de la casa", 5000L);
		Invoice invoice2 = new Invoice("compas de la oficina", 8000L);

		//usamos la funcion del metodo client la add
		client.addInvoices(invoice1).addInvoices(invoice2);

		clientRepository.save(client);
		System.out.println(client);
	}

	// cliente relaccion con direccion (address)
	// remover cliente existente
	@Transactional
	public void removeAddressFindById() {
		// buscamos un id en la tabla client
		Optional<Client> optionalClient = clientRepository.findById(2L);
		// validacion de la busqueda
		optionalClient.ifPresent(client -> {
			// campo de direccion para crear
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de gama", 9876);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);

			// funcion de crear en la base de datos
			clientRepository.save(client);
			System.out.println(client);

			// buscaremos los datos para eliminarlos por el ID recien creado
			Optional<Client> optionalClient2 = clientRepository.findOneWithAddress(2L);
			optionalClient2.ifPresent(c -> {
				c.getAddresses().remove(address1);
				clientRepository.save(c);
				System.out.println(c);
			});
		});
	}

	// eliminar direcciones
	@Transactional
	public void removeAddress() {
		// indicamos un nuevo cliente para crear
		Client client = new Client("Fran", "Moras");

		// campo de direccion para crear
		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de gama", 9876);

		// asociamos los campos de direccion a cliente en la tabla intermedia con id
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		// funcion de crear en la base de datos
		clientRepository.save(client);
		System.out.println(client);

		//buscaremos los datos para eliminarlos por el ID recien creado
		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(c -> {
			c.getAddresses().remove(address1);
			clientRepository.save(c);
			System.out.println(c);
		});
	}

	// cliente relaccion con direccion (address)
	//buscar
	@Transactional
	public void oneToManyFindById() {
		//buscamos un id en la tabla client
		Optional<Client> optionalClient = clientRepository.findById(2L);
		//validacion de la busqueda
		optionalClient.ifPresent(client -> {
			// campo de direccion para crear
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de gama", 9876);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);

			// funcion de crear en la base de datos
			clientRepository.save(client);
			System.out.println(client);
		});
	}

	//cliente relaccion con direccion (address)
	//crear
	@Transactional
	public void oneToMany() {
		//indicamos un nuevo cliente para crear
		Client client = new Client("Fran", "Moras");

		//campo de direccion para crear
		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de gama", 9876);

		//asociamos los campos de direccion a cliente en la tabla intermedia con id
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		//funcion de crear en la base de datos
		clientRepository.save(client);
		System.out.println(client);
	}

	@Transactional
	//creamos el cliente y lo asociamos a una factura (varios a uno)
	public void manyToOne() {
		//importador de la class directa de Client
		Client client = new Client("John", "Doe");
		//accion de crear en sql
		clientRepository.save(client);

		//crear el metodo de la factura y la asociamos al cliente que se creara consicutivamente
		Invoice invoice = new Invoice("compra  de oficina", 2000L);
		//asociamos al cliente creado
		invoice.setClient(client);
		Invoice invoiceDb = invoiceRepository.save(invoice);
		System.out.println(invoiceDb);
	}

	@Transactional
	// buscamos por id  aun cliente xistsne para asociar la factura nueva
	public void manyToOneFindByIdClient() {
		// buscamos por el id
		Optional<Client> optionalClient = clientRepository.findById(1L);
		//validamos si exite el id
		if(optionalClient.isPresent()){
			Client client = optionalClient.orElseThrow();

			Invoice invoice = new Invoice("compra  de oficina", 2000L);
			// asociamos al cliente creado
			invoice.setClient(client);
			Invoice invoiceDb = invoiceRepository.save(invoice);
			System.out.println(invoiceDb);
		}
	}
}
