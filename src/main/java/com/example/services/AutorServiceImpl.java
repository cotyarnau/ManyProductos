package com.example.services;


    import java.util.List;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;

import com.example.dao.AutorDao;
import com.example.entities.Autor;
    
    import lombok.RequiredArgsConstructor;
    
    @Service
    @RequiredArgsConstructor
    public class AutorServiceImpl implements AutorService{
    
        private final AutorDao autorDao; 
    
        @Override
        public Page<Autor> findAll(Pageable pageable) {
            return autorDao.findAll(pageable);
        }
    
        @Override
        public List<Autor> findAllSorted() {
            return autorDao.findAllByOrderByNombre();
        }
    
        @Override
        public Autor findById(int autorId) {
            return autorDao.findById(autorId);
        }
    
        @Override
        public void deleteById(int autorId) {
            autorDao.deleteById(autorId);
        }

        @Override
        public Autor save(Autor autor) {
            return autorDao.save(autor);
        }

        @Override
        public boolean existsById(int id) {
            return autorDao.existsById(id);
        }

        @Override
        public List<Autor> findAutoresByLibrosId(int libroId) {
            return autorDao.findAutoresByLibrosId(libroId);
        }
    
    }
    
