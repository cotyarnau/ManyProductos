package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "libros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Libro implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo; 

    @Column(name = "fecha_de_publicacion")
    private LocalDate fechaDePublicacion; 

    @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.ALL
      },mappedBy = "libros")
      @JsonIgnore
      @Builder.Default
    private Set<Autor> autores = new HashSet<>();

    public Set<Autor> getAutores() {
        return autores;
      }
    
      public void setAutores(Set<Autor> autores) {
        this.autores = autores;
      }   
      
      
 
}
