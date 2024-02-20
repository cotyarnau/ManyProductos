package com.example.Repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
// En el diamante ponemos el tipo de la entidad e Integer (en el diamante no se pueden primitivos)
// por el tipo del id.



    
     List<Producto> findProductosByPresentacionesId(int presentacionId);

     List<Producto> findByPublished(boolean b);

     List<Producto> findByTitleContaining(String title);

  

}
