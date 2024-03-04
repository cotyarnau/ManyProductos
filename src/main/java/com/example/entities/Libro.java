package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "libros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Libro implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String titulo;

  @Column(name = "fecha_publicacion")
  private LocalDate fechaPublicacion;

 

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
  @JoinTable(name = "libro_autor", joinColumns = { @JoinColumn(name = "libro_id") }, inverseJoinColumns = {
      @JoinColumn(name = "autor_id") })
  @Builder.Default
  private Set<Autor> autores = new HashSet<>();
  // el builder.default lo tengo que poner porque enrealidad el builder ya me
  // inicializaba el set , lista o mapa

  public void addAutor(Autor autor) {
    this.autores.add(autor);
    autor.getLibros().add(this);
  }

  public void removeAutor(int autorId) {
    Autor autor = this.autores.stream().filter(t -> t.getId() == autorId).findFirst()
        .orElse(null);
    if (autor != null) {
      this.autores.remove(autor);
      autor.getLibros().remove(this);
    }
  }

 

}