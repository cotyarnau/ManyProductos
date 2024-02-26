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
@Table(name = "productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "published")
  private boolean published;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
  @JoinTable(name = "producto_presentacion", joinColumns = { @JoinColumn(name = "producto_id") }, inverseJoinColumns = {
      @JoinColumn(name = "presentacion_id") })
  @Builder.Default
  private Set<Presentacion> presentaciones = new HashSet<>();
  // el builder.default lo tengo que poner porque enrealidad el builder ya me
  // inicializaba el set , lista o mapa

  public void addPresentacion(Presentacion presentacion) {
    this.presentaciones.add(presentacion);
    presentacion.getProductos().add(this);
  }

  public void removePresentacion(int presentacionId) {
    Presentacion presentacion = this.presentaciones.stream().filter(t -> t.getId() == presentacionId).findFirst()
        .orElse(null);
    if (presentacion != null) {
      this.presentaciones.remove(presentacion);
      presentacion.getProductos().remove(this);
    }
  }

  @Override
  public String toString() {
    return "Producto [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
  }

}
