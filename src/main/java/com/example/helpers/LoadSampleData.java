package com.example.helpers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Repository.PresentacionRepository;
import com.example.Repository.ProductoRepository;
import com.example.entities.Presentacion;
import com.example.entities.Producto;

@Configuration
public class LoadSampleData {

    @Bean
    public CommandLineRunner saveSampleData(ProductoRepository productoRepository,
            PresentacionRepository presentacionRepository) {

        return datos -> {
            Presentacion presentacion1 = Presentacion.builder()
                    .name("pares")
                    .description("productos de pares")
                    .build();

            Presentacion presentacion2 = Presentacion.builder()
                    .name("cincuenta")
                    .description("productos de a cincuenta")
                    .build();

            Producto producto1 = Producto.builder()
                    .title("falda")
                    .description("algodon")
                    .published(true)
                    .build();

            Producto producto2 = Producto.builder()
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
