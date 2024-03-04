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

import com.example.entities.Autor;
import com.example.entities.Libro;
import com.example.exception.ResourceNotFoundException;
import com.example.services.AutorService;
import com.example.services.LibroService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;
    private final AutorService autorService;

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
            Page<Libro> pageLibros = libroService.findAll(pageable);
            libros = pageLibros.getContent();
            responseEntity = new ResponseEntity<List<Libro>>(libros, HttpStatus.OK);
        } else { // ordenados alfabeticamente por titulo
            libros = libroService.findAllSorted();
            responseEntity = new ResponseEntity<List<Libro>>(libros, HttpStatus.OK);
        }
        return responseEntity;
    }

    // Metodo que devuelve un libro por su id
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getLibroById(@PathVariable(name = "id", required = true) int libroId) {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        Libro libro = libroService.findById(libroId);

        if (libro != null) {
            String succesMessage = "Libro con id " + libroId + ", encontrado";
            responseAsMap.put("succesMessage", succesMessage);
            responseAsMap.put("libro", libro);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
        } else {
            String errorMessage = "Libro con id " + libroId + ", no encontrado";
            responseAsMap.put("errorMessage", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    // Metodo que devuelve los autores de un libro
    @GetMapping("{libroId}/autores")
    public ResponseEntity<List<Autor>> getAllAutoresByLibroId(@PathVariable(value = "libroId") int libroId) {
        if (!libroService.existsById(libroId)) {
            throw new ResourceNotFoundException("No se encontro un libro con id " + libroId);
        }

        List<Autor> autores = autorService.findAutoresByLibrosId(libroId);
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }

    // Metodo para persistir un libro
    @PostMapping
    public ResponseEntity<Libro> saveLibro(@RequestBody Libro libro) {
        Libro _libro = libroService.save(libro);
        return new ResponseEntity<>(_libro, HttpStatus.CREATED);
    }

    // Metodo para añadir autores a un libro
    @PostMapping("/{libroId}/autores")
    public ResponseEntity<Autor> addAutor(@PathVariable(value = "libroId") int libroId,
            @RequestBody Autor autorRequest) {
        // Obtener el libro
        Libro libro = libroService.findById(libroId);
        if (libro == null) {
            throw new ResourceNotFoundException("No se encontró un libro con id " + libroId);
        }

        Autor nuevoAutor = autorService.save(autorRequest);
        libro.addAutor(nuevoAutor);
        libroService.save(libro);
        return new ResponseEntity<>(nuevoAutor, HttpStatus.CREATED);
    }

    // Metodo para eliminar un libro de un autor
    @DeleteMapping("{libroId}/autores/{autorId}")
    public ResponseEntity<HttpStatus> deleteLibroFromAutor(@PathVariable(value = "libroId") int libroId,
            @PathVariable(value = "autorId") int autorId) {
        Autor autor = autorService.findById(autorId);
        if (autor == null) {
            throw new ResourceNotFoundException("No se ha encontrado un autor con id  " + autorId);
        }

        autor.removeLibro(libroId);
        autorService.save(autor);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Metodo para eliminar un libro por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLibroById(@PathVariable(name = "id", required = true) int libroId) {
        Libro libro = libroService.findById(libroId);
        if (libro != null) {
            libroService.deleteById(libroId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
