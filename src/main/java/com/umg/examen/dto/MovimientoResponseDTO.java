package com.umg.examen.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovimientoResponseDTO {
    private Integer idMovimiento;
    private LocalDateTime fecha;
    private String tipo;
    private Integer cantidadMovida;
    private ProductoResponseDTO producto;
}
