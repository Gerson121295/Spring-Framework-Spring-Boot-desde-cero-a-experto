package com.gerson.curso.springboot.jpa.springbootjparelationship.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;
    private String lastname;
    
    //Tendra una relacion Bidireccional Client con Invoices: 1 cliente tiene muchas facturas y a su vez cada Factura esta asocicada a 1 cliente.(Es un ejemplo nada mas)
    //Relacion @OneToMany -  Un cliente puede tener muchas facturas.  One(un client) To(para) Many(muchas Facturas - Invoices).


    //Ejemplo Relacion Unidireccional: @OneToMany - Un cliente puede tener muchas direcciones.
    //Relacion @OneToMany -  Un cliente puede tener muchas Direcciones(es una lista porque son muchas).  One(un client) To(para) Many(muchas Direcciones - Address).
    //Cuando la anotacion de relacion termina en "Many" es un "List" lista porque son muchas. 
    //Cascade se configura para que cuando se crea un cliente en cascada va a guardar las direcciones y cuando se elimina un cliente tambien se elimina las relacion con esas direcciones.
    //orphanRemoval=true - Para eliminar campos huerfanos entre la relacion clients y adresses. Al eliminarse las relaciones de direcciones con cascade quedan huerfanas porque ya no tienen relacion con el cliente que se elimino, queda una relacion null en la tabla, para eliminar esos campos huerfanos se agrega: orphanRemoval=true
    //Con esta relacion OneToMany se crea una tabla intermedia de enlaces entre Client(clients) y Address(addresses), con la FK de cada tabla llamada: clients_addresses los campos de esa tabla se crean por defecto llamados:(client_id, address_id)  Nota: no se crea ningun campo FK en las tablas clients y addresses.
    /* Con @JoinTable definimos el nombre de la tabla intermedia entre Client(clients) y Address(addresses) como "tbl_clientes_to_direcciones" esto para no dejar el nombre que trae por defecto llamada: clients_addresses o bien que tengamos una tabla intermedia para la union entre estas 2 tablas Client(clients) y Address(addresses) y usemos el nombre en @JoinTable.
    @JoinTable recibe como parametros: 
        name: el nombre de la tabla intermedia entre Client(clients) y Address(addresses)
        JoinColumns: se define el nombre de los campos de la tabla intermedia, @JoinColumn(la principal) el id de tabla client "id_cliente" y se usa inverseJoinColumns = @JoinColumn(name = "id_direcciones" para definir el campo del id de la tabla addresses,  "id_direcciones", e
            esto para no dejar por defecto el nombre de la tabla y sus campos que se crea al realizar la relacion entre estas 2 tablas.
        uniqueConstraints: Define la relacion de los campos, Ejemplo: El campo: id_direcciones debe ser unico en la tabla no se tiene que repetir, una direccion pertenece a un cliente, El campo id_cliente si se puede repetir.
    */
    //Agregar Fetch evita agregar esto en application.properties: #spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true
    //fetch = FetchType.EAGER - Funciona pero no se recomienda, carga todo, busca el cliente y las direcciones.
    //En OneToMany si no se agrega el fetch por defecto se usa LAZY(carga perezoza)
    //Lo mejor fue no agregar el fetch y crear una consulta personalizada para buscar por id.

    // Forma 2 de implementar OneToMany con relacion existente: Para no crear una tabla intermedia entre Client(clients) y Address(addresses), Se estable el campo client_id en la tabla addresses agregando la anotacion en la clase Client: @JoinColumn(name="client_id")
    //Con la anotacion @JoinColumn se crea el campo client_id en la tabla addresses (dueña de la relacion) que sera la relacion entre Client(clients) y Address(addresses)
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //, fetch = FetchType.EAGER)  
    //@JoinColumn(name = "client_id")  //Agregando el JoinColumn no se crea una tabla intermedia entre las tablas clients y addresses, si no que crea un campo client_id en la tabla addresses para la relacion entre las tablas.
    @JoinTable(
        name="tbl_clientes_to_direcciones",  //se define el nombre de la tabla intermedia entre las tablas clients y addresses
        joinColumns = @JoinColumn(name = "id_cliente"), inverseJoinColumns = @JoinColumn(name = "id_direcciones"), //se define el nombre de los campos de la tabla intermedia, estos campos hace referencia a la tablas clients y addresses, se define en orden primero las tablas clients y luego addresses, los nombres pueden ser diferente.
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_direcciones"}) //id_direcciones es unica no se puede repetir en la tabla.
    )
    //private List<Address> addresses; 
    //private List<Address> addresses = new ArrayList<>(); //Forma 1: de instanciar el ArrayList 
    private Set<Address> addresses; //al tener varios o 2 left join en ClientRepository da error por lo que se soluciona cambiando el tipo de dato List a Set.  //Set es un conjunto de direcciones.


    //Relacion OneToMany Bidirecciona - Entre Client e Invoice
    //OneToMany - Un cliente(One) tiene(To) muchas(Many) facturas. 
    //mappedBy = "client" - representa la relacion Inversa entre clients e Invoices. MappedBy(mapeado por) el atributo del campo "client" de la clase Invoice que relaciona Client(clients) y Invoice(invoices)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    //private List<Invoice> invoices;
    private Set<Invoice> invoices; //al tener varios o 2 left join en ClientRepository da error por lo que se soluciona cambiando el tipo de dato List a Set. //Set es un conjunto de facturas


    //Ejemplo 1 - relacion Unidireccional OneToOne entre Clients y ClientDetails
    //Un One(un client) To(tiene) One(un ClientDetails)
    //Client es dueño de la relacion, aqui se encuentra la Fk llamada clients_details_id (o se podria cambiar el nombre con JoinColumn) este campo se crea en esta tabla clients
    //@OneToOne cuando la anotacion termina en One el fetch por defecto es Eager.
/*  @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_cliente_detalle") //Si queremos cambiar el FK que se genera (clients_details_id)
    private ClientDetails clientsDetails;
*/
    
    //Ejemplo 1 - Relacion Bidireccional OneToOne Clients y ClientDetails para que sea bidireccional en la clase ClientDetails Se agrega: Ejemplo 1: Bidireccional OneToOne entre ClientDetails y Client
    //No se agrego el @JoinColumn porque la dueña de la relacion será ClientDetails ahi se creara el FK client_id    Nota: Donde esta el @JoinColumn ahi se creará la FK
    //mappedBy = "client", define el campo por el cual será mapeado la clase Client y ClientDetails, en este caso es el campo "client" de la clase ClientDetails.
    //En resumen de las relaciones Bidireccionales: El "mappedBy" va en la clase main o padre, el @JoinColumn va en la clase hija o dependiente pero dueña de la relacion (ahi se creara la FK con el campo de relacion).
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private ClientDetails clientDetails;


    //Constructores
    public Client() {
        //Como se definio atributos de tipo List "lista" para addresses, invoice por lo tanto debe ser inicializada en el constructor.
        //Forma 2: de instanciar el ArrayList  definiendo el atributo tipo List
 /*     addresses = new ArrayList<>();  
        invoices = new ArrayList<>(); 
*/ 
        //Forma 2: de instanciar el ArrayList  definiendo el atributo tipo List
        addresses = new HashSet<>();  
        invoices = new HashSet<>(); 
    }

    public Client(String name, String lastname) {
        this(); //Para llamar al constructor vacio, ya que se instancia el ArrayList
        this.name = name;
        this.lastname = lastname;
    }

    //Getters and Setters
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    //public List<Address> getAddresses() {
    public Set<Address> getAddresses() {
        return addresses;
    }

    //public void setAddresses(List<Address> addresses) {
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    //public List<Invoice> getInvoices() {
    public Set<Invoice> getInvoices() {
        return invoices;
    }

    //public void setInvoices(List<Invoice> invoices) {
    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
    

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    //Este metodo se llama en OneToOneBidireccional()
    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
        clientDetails.setClient(this); //This es para crear
    }

    public void removeClientDetails(ClientDetails clientDetails) {
        clientDetails.setClient(null); //null es para borrar 
        this.clientDetails = null;
    }


    //Metodo para agregar la factura
    //public void addInvoice(Invoice invoice){
     public Client addInvoice(Invoice invoice){ //devolver cliente nos permitira, enlazar client.addInvoice(invoice1).addInvoice(invoice2);
        invoices.add(invoice);
        invoice.setClient(this); //con this le pasamos la instancia del objeto Client.  Nota; this para agregar y null para eliminar
        return this; //esta linea de codigo no van la opcion del metodo void.
    }

    @Override
    public String toString() {
        return "{Id=" + Id + 
                ", name=" + name + 
                ", lastname=" + lastname + 
                ", Invoices=" + invoices + //Se imprime la relacion invoices. //se agrego invoices, si se agrego invoce aqui en el toString de Client no agregar client en el metod toString de la clase Invoice
                ", addresses=" +  addresses  +
                ", ClientDetails=" +  clientDetails  +
                "}";
    }

    //Metodo para eliminar factura del lado del cliente y cliente del lado de la factura como es bidireccional.
    public void removeInvoice(Invoice invoice) {
        this.getInvoices().remove(invoice); //elimina la factura del lado del client
        invoice.setClient(null); //elimina el cliente de Invoice. Nota; this para agregar y null para eliminar
    }

}
