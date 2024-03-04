package com.example.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

 

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
  @JoinTable(name = "autor_libro", joinColumns = { @JoinColumn(name = "autor_id") }, inverseJoinColumns = {
      @JoinColumn(name = "libro_id") })
  @Builder.Default
  private Set<Libro> libros = new HashSet<>();
  
  public void addLibro(Libro libro) {
    this.libros.add(libro);
    libro.getAutores().add(this);
  }

  public void removeAutor(int libroId) {
    Libro libro = this.libros.stream().filter(t -> t.getId() == libroId).findFirst()
        .orElse(null);
    if (libro != null) {
      this.libros.remove(libro);
      libro.getAutores().remove(this);
    }
  }

 

}
