package com.gerson.curso.springboot.jpa.springbootjparelationship.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    //Ejemplo 1: @ManyToMany Unidireccional en el Student tendra la relacion y no en Course
    //Este Ejemplo 1: @ManyToMany Unidireccional definido en Student tambien es el mismos para ejemplo Bidireccional. Solo que student sera la clase hija dueña de la relacion xq tiene el JoinColumn y Course seerá el padre estará mapeado por mappedBy = "courses". courses definido en esta clase
    //Cascade para que cuando se guarde el student se guarde el Course. No se especifico el CascadeType.All porque al crear el student se asigna el curso, al eliminar el student no se elimina el curso porque estos pueden estar asignados a otros students.
    //Se creará una tabla intermedia automatica llamada por defecto students_courses que tendra las id de Student y Course. El nombre del la tabla y campos se puede personalizar usando @JoinTable. Nota no se creara ningun campo para la relacion en las tablas students y courses. la relacion se hara en la tabla intermedia students_courses
    //@ManyToMany - Many(muchos students) To(tienen) Many(muchos cursos)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //Ejemplo @JoinTable se agrego para definir el nombre de la tabla intermedia que se creará automaticamente o definir para usar una tabla existente asi tambien se define los campos de esta tabla. Si no agrega El @JoinColumn todo esto lo hace automaticament spring Hibernate JPA
    @JoinTable( //En caso de no querer la tabla por defecto la define su nombre y sus campos asi como la restriccion de sus atributos si son unicos
        name = "tbl_alumnos_cursos",  //Define el nombre de la tabla intermedia entre (students y courses)
        joinColumns = @JoinColumn(name ="alumno_id"),  //Se define los campos de la tabla intermedia que contendra los id de (students y courses), debe ir en orden primero va el de Student luego el de courses
        inverseJoinColumns = @JoinColumn(name="curso_id"), //para definir el nombre del campo de la otra tabla cursos, con la cual se tendra relacion. 
        uniqueConstraints = @UniqueConstraint(columnNames = {"alumno_id", "curso_id"}) //Para definir la restriccion: alumno_id y curso_id serán unicas no se podran repetir en la tabla. Un alumno no puede tener un mismo curso repetido, y un curso no puede tener un alumno repetido. que es diferente a Un alumno puede tener muchos cursos, y un curso puede tener muchos alumnos, "pero no repetidos".
        ) 
    private Set<Course> courses;  //Se establece el tipo de dato Set de Course porque sera un conjunto de cursos(muchos cursos), tambien puede ser List pero se usar'a en repository el Left join muchas veces
    

    //Constructores
    public Student() {
        this.courses = new HashSet<>(); //Se inicialia course debido a que sera un conjunto 
    }

    public Student(String name, String lastname) {
        this(); //se reutiliza el constructor vacio de Student() para que pueda instanciar o inicializar los HashSet de la lista de los cursos
        this.name = name;
        this.lastname = lastname;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    //se agrego xq es necesario para el Ejemplo 1: Relacion ManyToMany Bidireccional entre Course y Student
    //Metodo para agregar el curso al Student.
    public void addCourse(Course course){
        this.courses.add(course);
        course.getStudents().add(this);
    }

    //Metodo para eliminar el curso al Student.
    public void removeCourse(Course course){
        this.courses.remove(course);
        course.getStudents().remove(this);
    }


    //Como Students es el dueño de la relacion podemos dejar COurse aca, pero no podemos tener course aca y alla en el toString de Course porque se generaria un tema ciclicoo
    @Override
    public String toString() {
        return "{" + id + 
                ", name=" + name + 
                ", lastname=" + lastname + 
                ", courses=" + courses + 
                "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (lastname == null) {
            if (other.lastname != null)
                return false;
        } else if (!lastname.equals(other.lastname))
            return false;
        return true;
    }

}
