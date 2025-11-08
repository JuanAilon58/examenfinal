package com.umg.examen.service;

import com.umg.examen.dto.MovimientoDTO;
import com.umg.examen.entity.Movimiento;
import com.umg.examen.entity.Producto;
import com.umg.examen.repository.MovimientoRepository;
import com.umg.examen.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    // Usamos @Transactional para que, si algo falla (ej. no hay stock),
    // no se guarde ni el movimiento ni la actualización del producto.
    // Es "todo o nada".
    @Transactional
    public Movimiento registrarMovimiento(MovimientoDTO request) {

        // 1. Validar y encontrar el producto
        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + request.getIdProducto()));

        // 2. Aplicar la lógica de negocio (ENTRADA o SALIDA)
        if ("ENTRADA".equalsIgnoreCase(request.getTipo())) {
            
            // Si es ENTRADA (compra), se suma al stock
            int nuevoStock = producto.getExistencia() + request.getCantidad();
            producto.setExistencia(nuevoStock);
            
        } else if ("SALIDA".equalsIgnoreCase(request.getTipo())) {
            
            // Si es SALIDA (venta), se resta del stock
            if (producto.getExistencia() < request.getCantidad()) {
                // No permitir la venta si no hay stock
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            int nuevoStock = producto.getExistencia() - request.getCantidad();
            producto.setExistencia(nuevoStock);
            
        } else {
            throw new RuntimeException("Tipo de movimiento no válido: " + request.getTipo());
        }

        // 3. Guardar el producto actualizado (con el nuevo stock)
        productoRepository.save(producto);

        // 4. Crear y guardar el registro del movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setProducto(producto);
        movimiento.setCantidadMovida(request.getCantidad());
        movimiento.setTipo(request.getTipo().toUpperCase());
        // La fecha se asigna automáticamente gracias al @PrePersist en la Entidad

        return movimientoRepository.save(movimiento);
    }
}