package com.example.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dao.AutorDao;
import com.example.entities.Autor;
import com.example.entities.Libro;
import com.example.exception.ResourceNotFoundException;
import com.example.services.LibroService;

import lombok.RequiredArgsConstructor;



@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/libros")
@RequiredArgsConstructor
public class LibroController {

 
    private final LibroService libroService;

   // Metodo que devuelve todos los libros paginados u ordenados
    @GetMapping
    public ResponseEntity<List<Libro>> findAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {

        ResponseEntity<List<Libro>> responseEntity = null;
        List<Libro> libros = new ArrayList<>();
        Sort sortByTitulo = Sort.by("titulo");
        // Comprobamos si llega page y size
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size, sortByTitulo);
            Page<Libro> pageAttendees = libroService.findAll(pageable);
            libros = pageAttendees.getContent();
            responseEntity = new ResponseEntity<List<Libro>>(libros, HttpStatus.OK);
        } else { // ordenados alfabeticamente por titulo
            libros = libroService.findAllSorted();
            responseEntity = new ResponseEntity<List<Libro>>(libros, HttpStatus.OK);
        }
        return responseEntity;
    }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>>  getLibroById(@PathVariable (name = "id", required = true) int libroId) {
    Map<String, Object> responseAsMap = new HashMap<>();
    ResponseEntity<Map<String, Object>> responseEntity = null;
    Libro libro = libroService.findById(libroId);

    if(libro != null) {
        String succesMessage = "Libro con id " + libroId + ", encontrado";
        responseAsMap.put("succesMessage", succesMessage);
        responseAsMap.put("libro", libro);
        responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
    } else {
        String errorMessage = "Libro con id " + libroId + ", no encontrado";
        responseAsMap.put("errorMessage", errorMessage);
        responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.NOT_FOUND);
    }
       
    return responseEntity;
  }

  // @PostMapping("/productos")
  // public ResponseEntity<Autor> createProducto(@RequestBody Autor producto) {
  //   Autor _producto = productoRepository.save(producto);
  //   return new ResponseEntity<>(_producto, HttpStatus.CREATED);
  // }

  // @PutMapping("/productos/{id}")
  // public ResponseEntity<Autor> updateProducto(@PathVariable("id") int id, @RequestBody Autor producto) {
  //   Autor _producto = productoRepository.findById(id)
  //       .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

  //   _producto.setTitle(producto.getTitle());
  //   _producto.setDescription(producto.getDescription());
  //   _producto.setPublished(producto.isPublished());
    
  //   return new ResponseEntity<>(productoRepository.save(_producto), HttpStatus.OK);
  // }

  // @DeleteMapping("/productos/{id}")
  // public ResponseEntity<HttpStatus> deleteProducto(@PathVariable("id") int id) {
  //   productoRepository.deleteById(id);
    
  //   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  // }

  // @DeleteMapping("/productos")
  // public ResponseEntity<HttpStatus> deleteAllProductos() {
  //   productoRepository.deleteAll();
    
  //   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  // }

  // @GetMapping("/productos/published")
  // public ResponseEntity<List<Autor>> findByPublished() {
  //   List<Autor> productos = productoRepository.findByPublished(true);

  //   if (productos.isEmpty()) {
  //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  //   }
    
  //   return new ResponseEntity<>(productos, HttpStatus.OK);
  // }
}
