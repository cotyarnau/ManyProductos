package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dao.LibroDao;
import com.example.entities.Libro;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService{

    private final LibroDao libroDao; 

    @Override
    public Page<Libro> findAll(Pageable pageable) {
        return libroDao.findAll(pageable);
    }

    @Override
    public List<Libro> findAllSorted() {
        return libroDao.findAllByOrderByTitulo();
    }

    @Override
    public Libro findById(int libroId) {
        return libroDao.findById(libroId);
    }

    @Override
    public Libro save(Libro libro) {
        return libroDao.save(libro);
    }

    @Override
    public void deleteById(int libroId) {
        libroDao.deleteById(libroId);
    }

    @Override
    public boolean existsById(int id) {
        return libroDao.existsById(id);
    }

    @Override
    public List<Libro> findLibrosByAutoresId(int autorId) {
        return libroDao.findLibrosByAutoresId(autorId);
    }

}
