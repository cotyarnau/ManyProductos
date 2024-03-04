package com.example.helpers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Libro;
import com.example.dao.LibroDao;
import com.example.dao.AutorDao;
import com.example.entities.Autor;

@Configuration
public class LoadSampleData {

    @Bean
    public CommandLineRunner saveSampleData(AutorDao autorDao,
            LibroDao libroDao) {

        return datos -> {
            Libro libro1 = Libro.builder()
                    .titulo("Romeo y Julieta")
                    .fechaPublicacion(LocalDate.of(1597, 3, 01))
                    .build();

            Libro libro2 = Libro.builder()
                    .titulo("Cien años de Soledad")
                    .fechaPublicacion(LocalDate.of(1967, 5, 27))
                    .build();

            Autor autor1 = Autor.builder()
                    .nombre("William Shakespeare")
                    .build();

            Autor autor2 = Autor.builder()
                    .nombre("Gabriel Garcia Marquez")
                    .build();

                    
                    libro1.addAutor(autor1);
                    libro1.addAutor(autor2);
                    libro2.addAutor(autor1);
                    libro2.addAutor(autor2);


                    
                    libroDao.save(libro1);
                    libroDao.save(libro2);
                
                    
        };

    }
}
