package com.umg.examen.service;

import com.umg.examen.dto.MovimientoDTO;
import com.umg.examen.dto.MovimientoResponseDTO;
import com.umg.examen.dto.ProductoResponseDTO;
import com.umg.examen.dto.ProveedorDTO;
import com.umg.examen.entity.Movimiento;
import com.umg.examen.entity.Producto;
import com.umg.examen.entity.Proveedor;
import com.umg.examen.repository.MovimientoRepository;
import com.umg.examen.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    public List<MovimientoResponseDTO> getAllMovimientos() {
        return movimientoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MovimientoResponseDTO registrarMovimiento(MovimientoDTO request) {

        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + request.getIdProducto()));

        if ("ENTRADA".equalsIgnoreCase(request.getTipo())) {
            int nuevoStock = producto.getExistencia() + request.getCantidad();
            producto.setExistencia(nuevoStock);
        } else if ("SALIDA".equalsIgnoreCase(request.getTipo())) {
            if (producto.getExistencia() < request.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            int nuevoStock = producto.getExistencia() - request.getCantidad();
            producto.setExistencia(nuevoStock);
        } else {
            throw new RuntimeException("Tipo de movimiento no válido: " + request.getTipo());
        }

        productoRepository.save(producto);

        Movimiento movimiento = new Movimiento();
        movimiento.setProducto(producto);
        movimiento.setCantidadMovida(request.getCantidad());
        movimiento.setTipo(request.getTipo().toUpperCase());

        return toResponseDto(movimientoRepository.save(movimiento));
    }

    private MovimientoResponseDTO toResponseDto(Movimiento movimiento) {
        MovimientoResponseDTO dto = new MovimientoResponseDTO();
        dto.setIdMovimiento(movimiento.getIdMovimiento());
        dto.setFecha(movimiento.getFecha());
        dto.setTipo(movimiento.getTipo());
        dto.setCantidadMovida(movimiento.getCantidadMovida());
        if (movimiento.getProducto() != null) {
            dto.setProducto(toProductoResponseDto(movimiento.getProducto()));
        }
        return dto;
    }

    private ProductoResponseDTO toProductoResponseDto(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setExistencia(producto.getExistencia());
        if (producto.getProveedor() != null) {
            dto.setProveedor(toProveedorDto(producto.getProveedor()));
        }
        return dto;
    }

    private ProveedorDTO toProveedorDto(Proveedor proveedor) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNombre(proveedor.getNombre());
        dto.setTelefono(proveedor.getTelefono());
        dto.setDireccion(proveedor.getDireccion());
        return dto;
    }
}