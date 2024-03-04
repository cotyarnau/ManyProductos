package com.example.controller;
import java.util.ArrayList;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dao.AutorDao;
import com.example.entities.Autor;
import com.example.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;



@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductoController {

 
    private final AutorDao productoRepository;

  @GetMapping("/productos")
  public ResponseEntity<List<Autor>> getAllProductos(@RequestParam(required = false) String title) {
    List<Autor> productos = new ArrayList<>();

    if (title == null)
      productoRepository.findAll().forEach(productos::add);
    else
    productoRepository.findByTitleContaining(title).forEach(productos::add);

    if (productos.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(productos, HttpStatus.OK);
  }

  @GetMapping("/productos/{id}")
  public ResponseEntity<Autor> getProductoById(@PathVariable("id") int id) {
    Autor producto = productoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

    return new ResponseEntity<>(producto, HttpStatus.OK);
  }

  @PostMapping("/productos")
  public ResponseEntity<Autor> createProducto(@RequestBody Autor producto) {
    Autor _producto = productoRepository.save(producto);
    return new ResponseEntity<>(_producto, HttpStatus.CREATED);
  }

  @PutMapping("/productos/{id}")
  public ResponseEntity<Autor> updateProducto(@PathVariable("id") int id, @RequestBody Autor producto) {
    Autor _producto = productoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

    _producto.setTitle(producto.getTitle());
    _producto.setDescription(producto.getDescription());
    _producto.setPublished(producto.isPublished());
    
    return new ResponseEntity<>(productoRepository.save(_producto), HttpStatus.OK);
  }

  @DeleteMapping("/productos/{id}")
  public ResponseEntity<HttpStatus> deleteProducto(@PathVariable("id") int id) {
    productoRepository.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/productos")
  public ResponseEntity<HttpStatus> deleteAllProductos() {
    productoRepository.deleteAll();
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/productos/published")
  public ResponseEntity<List<Autor>> findByPublished() {
    List<Autor> productos = productoRepository.findByPublished(true);

    if (productos.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }
}
