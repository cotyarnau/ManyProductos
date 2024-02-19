package com.example.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Repository.ProductoRepository;
import com.example.entities.Producto;

import lombok.RequiredArgsConstructor;



@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductoController {

 
    private final ProductoRepository productoRepository;

  @GetMapping("/tutorials")
  public ResponseEntity<List<Producto>> getAllProductos(@RequestParam(required = false) String title) {
    List<Producto> productos = new ArrayList<>();

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
  public ResponseEntity<Producto> getProductoById(@PathVariable("id") int id) {
    Producto producto = productoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

    return new ResponseEntity<>(producto, HttpStatus.OK);
  }

  @PostMapping("/productos")
  public ResponseEntity<Producto> createTutorial(@RequestBody Producto producto) {
     Producto _producto = productoRepository.save(new Producto(producto.getTitle(), producto.getDescription(), true));
    return new ResponseEntity<>(_producto, HttpStatus.CREATED);
  }

  @PutMapping("/tutorials/{id}")
  public ResponseEntity<Producto> updateTutorial(@PathVariable("id") int id, @RequestBody Producto producto) {
    Producto _producto = productoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

    _producto.setTitle(producto.getTitle());
    _producto.setDescription(producto.getDescription());
    _producto.setPublished(producto.isPublished());
    
    return new ResponseEntity<>(productoRepository.save(_producto), HttpStatus.OK);
  }

  @DeleteMapping("/tutorials/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") int id) {
    productoRepository.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/tutorials")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    productoRepository.deleteAll();
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/tutorials/published")
  public ResponseEntity<List<Producto>> findByPublished() {
    List<Producto> productos = productoRepository.findByPublished(true);

    if (productos.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }
}
