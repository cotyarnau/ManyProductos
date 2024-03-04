package com.example.helpers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Libro;
import com.example.dao.LibroDao;
import com.example.dao.AutorDao;
import com.example.entities.Autor;

@Configuration
public class LoadSampleData {

    @Bean
    public CommandLineRunner saveSampleData(AutorDao productoRepository,
            LibroDao presentacionRepository) {

        return datos -> {
            Libro presentacion1 = Libro.builder()
                    .name("pares")
                    .description("productos de pares")
                    .build();

            Libro presentacion2 = Libro.builder()
                    .name("cincuenta")
                    .description("productos de a cincuenta")
                    .build();

            Autor producto1 = Autor.builder()
                    .title("falda")
                    .description("algodon")
                    .published(true)
                    .build();

            Autor producto2 = Autor.builder()
                    .title("camiseta")
                    .description("algodon")
                    .published(true)
                    .build();

                    
                    producto1.addPresentacion(presentacion1);
                    producto1.addPresentacion(presentacion2);
                    producto2.addPresentacion(presentacion1);
                    producto2.addPresentacion(presentacion2);


                    
                    productoRepository.save(producto1);
                    productoRepository.save(producto2);
                
                    
        };

    }
}
