package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Repository.PresentacionRepository;
import com.example.Repository.ProductoRepository;
import com.example.entities.Presentacion;
import com.example.entities.Producto;

import lombok.RequiredArgsConstructor;



@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PresentacionController {

  
  private final ProductoRepository productoRepository;
  private final PresentacionRepository presentacionRepository;

  @GetMapping("/tags")
  public ResponseEntity<List<Presentacion>> getAllTags() {
    List<Presentacion> presentaciones = new ArrayList<>();

    presentacionRepository.findAll().forEach(presentaciones::add);

    if (presentaciones.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(presentaciones, HttpStatus.OK);
  }
  
  @GetMapping("/tutorials/{tutorialId}/tags")
  public ResponseEntity<List<Presentacion>> getAllPresentacionesByProductoslId(@PathVariable(value = "presentacionId") int presentacionId) {
    if (!presentacionRepository.existsById(presentacionId)) {
      throw new ResourceNotFoundException("Not found Producto with id = " + presentacionId);
    }

    List<Presentacion> presentaciones = presentacionRepository.findPresentacionesByProductosId(presentacionId);
    return new ResponseEntity<>(presentaciones, HttpStatus.OK);
  }

  @GetMapping("/tags/{id}")
  public ResponseEntity<Presentacion> getPresentacionById(@PathVariable(value = "id") int id) {
    Presentacion presentacion = presentacionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Presentacion with id = " + id));

    return new ResponseEntity<>(presentacion, HttpStatus.OK);
  }
  
  @GetMapping("/tags/{tagId}/tutorials")
  public ResponseEntity<List<Producto>> getAllProductosByPresentacionId(@PathVariable(value = "presentacionId") int presentacionId) {
    if (!presentacionRepository.existsById(presentacionId)) {
      throw new ResourceNotFoundException("Not found Tag  with id = " + presentacionId);
    }

    List<Presentacion> tutorials = presentacionRepository.findTutorialsByTagsId(presentacionId);
    return new ResponseEntity<>(tutorials, HttpStatus.OK);
  }

  @PostMapping("/tutorials/{tutorialId}/tags")
  public ResponseEntity<Tag> addTag(@PathVariable(value = "tutorialId") Long tutorialId, @RequestBody Tag tagRequest) {
    Tag tag = tutorialRepository.findById(tutorialId).map(tutorial -> {
      long tagId = tagRequest.getId();
      
      // tag is existed
      if (tagId != 0L) {
        Tag _tag = tagRepository.findById(tagId)
            .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
        tutorial.addTag(_tag);
        tutorialRepository.save(tutorial);
        return _tag;
      }
      
      // add and create new Tag
      tutorial.addTag(tagRequest);
      return tagRepository.save(tagRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

    return new ResponseEntity<>(tag, HttpStatus.CREATED);
  }

  @PutMapping("/tags/{id}")
  public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tagRequest) {
    Tag tag = tagRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("TagId " + id + "not found"));

    tag.setName(tagRequest.getName());

    return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);
  }
 
  @DeleteMapping("/tutorials/{tutorialId}/tags/{tagId}")
  public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable(value = "tutorialId") Long tutorialId, @PathVariable(value = "tagId") Long tagId) {
    Tutorial tutorial = tutorialRepository.findById(tutorialId)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
    
    tutorial.removeTag(tagId);
    tutorialRepository.save(tutorial);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/tags/{id}")
  public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
    tagRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
