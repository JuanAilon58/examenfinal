package com.umg.examen.dto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer existencia;
    private ProveedorDTO proveedor;
}
