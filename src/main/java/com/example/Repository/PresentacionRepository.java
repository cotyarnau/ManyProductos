package com.example.Repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Presentacion;

 
public interface PresentacionRepository extends JpaRepository<Presentacion, Integer>{
 
    List<Presentacion> findPresentacionesByProductosId(int ProductoId);
}