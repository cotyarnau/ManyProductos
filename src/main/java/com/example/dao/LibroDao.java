package com.example.dao;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Libro;

public interface LibroDao extends JpaRepository<Libro, Integer> {

    // List<Libro> findPresentacionesByProductosId(int productoId);
    List<Libro> findLibrosByAutoresId(int autorId);

      // metodo para recuperar libros paginados
      @Query(value = "SELECT l FROM Libro l LEFT JOIN FETCH l.autores", 
      countQuery = "SELECT COUNT(l) FROM Libro l")      
      public Page<Libro> findAll(Pageable pageable);
  
      // recuperar attendees ordenados sin paginacion
      public List<Libro> findAllByOrderByTitulo(); 

      // recuperar libro por Id
      public Libro findById(int id);
  
  
      
  
}
