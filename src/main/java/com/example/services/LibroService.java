package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.entities.Libro;

public interface LibroService {

    public Page<Libro> findAll(Pageable pageable);
    public List<Libro> findAllSorted();
    public Libro findById(int libroId);
    public Libro save(Libro libro);
    public void deleteById(int libroId);
    public boolean existsById(int id);
    List<Libro> findLibrosByAutoresId(int autorId);
}
