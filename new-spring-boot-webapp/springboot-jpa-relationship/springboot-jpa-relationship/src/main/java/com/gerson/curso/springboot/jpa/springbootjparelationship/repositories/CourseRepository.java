package com.gerson.curso.springboot.jpa.springbootjparelationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gerson.curso.springboot.jpa.springbootjparelationship.entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

    //Trae de la tabla courses todos los students y cursos que tengan o no tengan los cursos estos gracias a left esta es una solucion al error al utilizar FindById 
    //que da error porque existe una relacion entre students y courses otra solucion es agregar en la anotacion ManyToMany de Cource el fetch = FetchType.Eager
    @Query("select s from Course s left join fetch s.students where s.id=?1")
    Optional<Course> findOneWithStudents(long id);

}
