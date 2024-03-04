// package com.example.controller;

// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.example.entities.Libro;
// import com.example.dao.LibroDao;
// import com.example.dao.AutorDao;
// import com.example.entities.Autor;
// import com.example.exception.ResourceNotFoundException;

// import lombok.RequiredArgsConstructor;



// @CrossOrigin(origins = "http://localhost:8081")
// @RestController
// @RequestMapping("/api")
// @RequiredArgsConstructor
// public class PresentacionController {

  
//   private final AutorDao productoRepository;
//   private final LibroDao presentacionRepository;

//   @GetMapping("/presentaciones")
//   public ResponseEntity<Set<Libro>> getAllTags() {
//    Set<Libro> presentaciones = new HashSet<>();;

//     presentacionRepository.findAll().forEach(presentaciones::add);

//     if (presentaciones.isEmpty()) {
//       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//     }

//     return new ResponseEntity<>(presentaciones, HttpStatus.OK);
//   }
  
//   @GetMapping("/productos/{productoId}/presentaciones")
//   public ResponseEntity<List<Libro>> getAllPresentacionesByProductosId(@PathVariable(value = "productoId") int productoId) {
//     if (!productoRepository.existsById(productoId)) {
//       throw new ResourceNotFoundException("Not found Producto with id = " + productoId);
//     }

//     List<Libro> presentaciones = presentacionRepository.findPresentacionesByProductosId(productoId);
//     return new ResponseEntity<>(presentaciones, HttpStatus.OK);
//   }


//   @GetMapping("/presentaciones/{id}")
//   public ResponseEntity<Libro> getPresentacionById(@PathVariable(value = "id") int id) {
//     Libro presentacion = presentacionRepository.findById(id)
//         .orElseThrow(() -> new ResourceNotFoundException("Not found Presentacion with id = " + id));

//     return new ResponseEntity<>(presentacion, HttpStatus.OK);
//   }
  
//   @GetMapping("/presentaciones/{presentacionId}/productos")
//   public ResponseEntity<List<Autor>> getAllProductosByPresentacionesId(@PathVariable(value = "presentacionId") int presentacionId) {
//     if (!presentacionRepository.existsById(presentacionId)) {
//       throw new ResourceNotFoundException("Not found Tag  with id = " + presentacionId);
//     }

//     List<Autor> productos = productoRepository.findProductosByPresentacionesId(presentacionId);
//     return new ResponseEntity<>(productos, HttpStatus.OK);
//   }
    
//   @PostMapping("/productos/{productoId}/presentaciones")
//   public ResponseEntity<Libro> addPresentacion(@PathVariable(value = "productoId") int productoId, @RequestBody Libro presentacionRequest) {
//     Libro presentacion = productoRepository.findById(productoId).map(producto -> {
//       int presentacionId = presentacionRequest.getId();
      
//       // presentacion is existed
//       if (presentacionId != 0L) {
//         Libro _presentacion = presentacionRepository.findById(presentacionId)
//             .orElseThrow(() -> new ResourceNotFoundException("Not found Presentacion with id = " + presentacionId));
//         producto.addPresentacion(_presentacion);
//         productoRepository.save(producto);
//         return _presentacion;
//       }
      
//       // add and create new Tag
//       producto.addPresentacion(presentacionRequest);
//       return presentacionRepository.save(presentacionRequest);
//     }).orElseThrow(() -> new ResourceNotFoundException("Not found Producto with id = " + productoId));

//     return new ResponseEntity<>(presentacion, HttpStatus.CREATED);
//   }


//   @PutMapping("/presentaciones/{id}")
//   public ResponseEntity<Libro> updatePresentacion(@PathVariable("id") int id, @RequestBody Libro presentacionRequest) {
//     Libro presentacion = presentacionRepository.findById(id)
//         .orElseThrow(() -> new ResourceNotFoundException("PresentacionId " + id + "not found"));

//     presentacion.setName(presentacionRequest.getName());

//     return new ResponseEntity<>(presentacionRepository.save(presentacion), HttpStatus.OK);
//   }
 
//   @DeleteMapping("/productos/{productoId}/presentaciones/{presentacionId}")
//   public ResponseEntity<HttpStatus> deletePresentacionFromProducto(@PathVariable(value = "productoId") int productoId, @PathVariable(value = "productoId") int presentacionId) {
//     Autor producto = productoRepository.findById(productoId)
//         .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + productoId));
    
//     producto.removePresentacion(presentacionId);
//     productoRepository.save(producto);
    
//     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//   }
  
//   @DeleteMapping("/presentaciones/{id}")
//   public ResponseEntity<HttpStatus> deletePresentacion(@PathVariable("id") int id) {
//     presentacionRepository.deleteById(id);

//     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//   }
// }
