package com.example.Repository;
 
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Presentacion;

 
public interface PresentacionRepository extends JpaRepository<Presentacion, Integer>{
 
    Set<Presentacion> findPresentacionesByProductosId(int productoId);
}
