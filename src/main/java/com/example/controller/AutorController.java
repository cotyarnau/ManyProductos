package com.example.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entities.Libro;
import com.example.entities.Autor;
import com.example.exception.ResourceNotFoundException;
import com.example.services.AutorService;
import com.example.services.LibroService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;
    private final LibroService libroService;

    // Metodo que devuelve todos los autores paginados u ordenados
    @GetMapping
    public ResponseEntity<List<Autor>> findAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {

        ResponseEntity<List<Autor>> responseEntity = null;
        List<Autor> autores = new ArrayList<>();
        Sort sortByTitulo = Sort.by("nombre");
        // Comprobamos si llega page y size
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size, sortByTitulo);
            Page<Autor> pageAutores = autorService.findAll(pageable);
            autores = pageAutores.getContent();
            responseEntity = new ResponseEntity<List<Autor>>(autores, HttpStatus.OK);
        } else { // ordenados alfabeticamente por titulo
            autores = autorService.findAllSorted();
            responseEntity = new ResponseEntity<List<Autor>>(autores, HttpStatus.OK);
        }
        return responseEntity;

    }

    // Metodo que devuelve un autor por su id
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getLibroById(@PathVariable(name = "id", required = true) int autorId) {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        Autor autor = autorService.findById(autorId);

        if (autor != null) {
            String succesMessage = "Autor con id " + autorId + ", encontrado";
            responseAsMap.put("succesMessage", succesMessage);
            responseAsMap.put("autor", autor);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
        } else {
            String errorMessage = "Autor con id " + autorId + ", no encontrado";
            responseAsMap.put("errorMessage", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    // Metodo que devuelve todos los libros correspondientes a un autor
    @GetMapping("{autorId}/libros")
    public ResponseEntity<List<Libro>> getAllLibrosByAutorId(@PathVariable(value = "autorId") int autorId) {
        if (!autorService.existsById(autorId)) {
            throw new ResourceNotFoundException("No se encontro un autor con id " + autorId);
        }

        List<Libro> libros = libroService.findLibrosByAutoresId(autorId);
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    // Metodo que devuelve todos los libros de un autor iguales a posteriores a una
    // fecha
    @GetMapping("/{autorId}/libros/fecha-publicacion/:{fecha}")
    public ResponseEntity<List<Libro>> getAllLibrosByDate(@PathVariable(value = "autorId") int autorId,
            @PathVariable(value = "fecha") LocalDate fecha) {
        if (!autorService.existsById(autorId)) {
            throw new ResourceNotFoundException("No se encontro un autor con id " + autorId);
        }

        List<Libro> libros = libroService.findLibrosByAutoresId(autorId);
        List<Libro> librosPostFecha = libros.stream()
                .filter(l -> l.getFechaPublicacion().isEqual(fecha) || l.getFechaPublicacion().isAfter(fecha))
                .collect(Collectors.toList());
        return new ResponseEntity<>(librosPostFecha, HttpStatus.OK);
    }

    // Metodo que actualiza un autor por su id
    @PutMapping("/{id}")
    public ResponseEntity<Autor> updateAutor(@PathVariable("id") int id, @RequestBody Autor autorRequest) {
        Autor autor = autorService.findById(id);
        if (autor == null) {
            throw new ResourceNotFoundException("Autor con id  " + id + "no encontrado");

        }

        autor.setNombre(autorRequest.getNombre());

        return new ResponseEntity<>(autorService.save(autor), HttpStatus.OK);
    }

    // Metodo que elimina un autor por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAutor(@PathVariable("id") int id) {
        autorService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
