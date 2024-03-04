package com.example.dao;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Autor;

@Repository
public interface AutorDao extends JpaRepository<Autor, Integer>{




    
     // List<Autor> findProductosByPresentacionesId(int presentacionId);
     List<Autor> findAutoresByLibrosId(int libroId);


    

  

}
