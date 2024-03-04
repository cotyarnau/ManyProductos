package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.entities.Autor;

public interface AutorService {

    public Page<Autor> findAll(Pageable pageable);
    public List<Autor> findAllSorted();
    public Autor findById(int libroId);
    public Autor save(Autor autor);
    public void deleteById(int libroId);
    public boolean existsById(int id);
    List<Autor> findAutoresByLibrosId(int libroId);

}