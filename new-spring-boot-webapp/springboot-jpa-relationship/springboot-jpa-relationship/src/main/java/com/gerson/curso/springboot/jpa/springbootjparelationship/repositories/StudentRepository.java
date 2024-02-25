package com.gerson.curso.springboot.jpa.springbootjparelationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gerson.curso.springboot.jpa.springbootjparelationship.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{

    //Trae de la tabla students todos los students y cursos que tengan o no tengan los students estos gracias a left esta es una solucion al error al utilizar FindById 
    //que da error porque existe una relacion entre students y courses otra solucion es agregar en la anotacion ManyToMany de Student el fetch = FetchType.Eager
    @Query("select s from Student s left join fetch s.courses where s.id=?1")
    Optional<Student> findOneWithCourses(long id);

}
