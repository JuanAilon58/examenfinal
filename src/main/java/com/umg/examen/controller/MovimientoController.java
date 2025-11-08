package com.umg.examen.controller;

import com.umg.examen.dto.MovimientoDTO;
import com.umg.examen.entity.Movimiento;
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
    public List<Movimiento> getAllMovimientos() {
        return movimientoService.getAllMovimientos();
    }

    // Endpoint para crear un nuevo movimiento (entrada o salida)
    @PostMapping
    public ResponseEntity<Movimiento> crearMovimiento(@RequestBody MovimientoDTO request) {
        try {
            Movimiento nuevoMovimiento = movimientoService.registrarMovimiento(request);
            return ResponseEntity.ok(nuevoMovimiento);
        } catch (RuntimeException e) {
            // Manejo simple de errores (ej. "Stock insuficiente")
            return ResponseEntity.badRequest().body(null); // (Mejoraría con un DTO de Error, pero esto funciona)
        }
    }
}