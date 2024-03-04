package com.example.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "autores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Autor implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String nombre;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {
      CascadeType.ALL
  }, mappedBy = "autores")
  @JsonIgnore
  @Builder.Default
  private Set<Libro> libros = new HashSet<>();

  public Set<Libro> getLibros() {
    return libros;
  }

  public void setLibros(Set<Libro> libros) {
    this.libros = libros;
  }

  public void removeLibro(int libroId) {
    Libro libro = this.libros.stream().filter(l -> l.getId() == libroId).findFirst()
        .orElse(null);
    if (libro != null) {
      this.libros.remove(libro);
      libro.getAutores().remove(this);
    }

  }
}
