package com.example.helpers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Libro;
import com.example.services.LibroService;
import com.example.dao.LibroDao;
import com.example.dao.AutorDao;
import com.example.entities.Autor;

@Configuration
public class LoadSampleData {

        @Bean
        public CommandLineRunner saveSampleData(AutorDao autorDao,
                        LibroService libroService) {

                return datos -> {
                        Libro libro1 = Libro.builder()
                                        .titulo("Romeo y Julieta")
                                        .fechaPublicacion(LocalDate.of(1597, 3, 01))
                                        .build();

                        Libro libro2 = Libro.builder()
                                        .titulo("Cien años de Soledad")
                                        .fechaPublicacion(LocalDate.of(1967, 5, 27))
                                        .build();

                        Libro libro3 = Libro.builder()
                                        .titulo("Macbeth")
                                        .fechaPublicacion(LocalDate.of(1623, 3, 01))
                                        .build();

                        Libro libro4 = Libro.builder()
                                        .titulo("Relato de un nafrago")
                                        .fechaPublicacion(LocalDate.of(1955, 5, 27))
                                        .build();

                        Autor autor1 = Autor.builder()
                                        .nombre("Pepe Perez")
                                        .build();

                        Autor autor2 = Autor.builder()
                                        .nombre("Gabriel Garcia Marquez")
                                        .build();

                        Autor autor3 = Autor.builder()
                                        .nombre("William Shakespeare")
                                        .build();

                        Autor autor4 = Autor.builder()
                                        .nombre("Romero Mero")
                                        .build();

                        libro1.addAutor(autor3);
                        libro1.addAutor(autor1);
                        libro2.addAutor(autor2);
                        libro2.addAutor(autor4);
                        libro3.addAutor(autor3);
                        libro3.addAutor(autor1);
                        libro4.addAutor(autor2);
                        libro4.addAutor(autor4);
                        

                        libroService.save(libro1);
                        libroService.save(libro2);
                        libroService.save(libro3);
                        libroService.save(libro4);

                };

        }
}
