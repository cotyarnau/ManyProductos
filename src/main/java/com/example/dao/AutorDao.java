package com.example.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entities.Autor;

@Repository
public interface AutorDao extends JpaRepository<Autor, Integer>{




    
     // List<Autor> findProductosByPresentacionesId(int presentacionId);
     List<Autor> findAutoresByLibrosId(int libroId);

      // metodo para recuperar autores paginados
      @Query(value = "SELECT a FROM Autor a LEFT JOIN FETCH a.libros", 
      countQuery = "SELECT COUNT(a) FROM Autor a")      
      public Page<Autor> findAll(Pageable pageable);
  
      // recuperar autores ordenados sin paginacion
      public List<Autor> findAllByOrderByNombre(); 

      // recuperar autor por Id
      public Autor findById(int id); 

     public void delete(Autor autor); 


    

  

}
