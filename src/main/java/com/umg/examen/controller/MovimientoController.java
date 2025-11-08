package com.umg.examen.controller;

import com.umg.examen.dto.MovimientoDTO;
import com.umg.examen.dto.MovimientoResponseDTO;
import com.umg.examen.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<MovimientoResponseDTO> getAllMovimientos() {
        return movimientoService.getAllMovimientos();
    }

    // Endpoint para crear un nuevo movimiento (entrada o salida)
    @PostMapping
    public ResponseEntity<?> crearMovimiento(@RequestBody MovimientoDTO request) {
        try {
            MovimientoResponseDTO nuevoMovimiento = movimientoService.registrarMovimiento(request);
            return ResponseEntity.ok(nuevoMovimiento);
        } catch (RuntimeException e) {
            // Manejo de errores (ej. "Stock insuficiente") devolviendo el mensaje.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}