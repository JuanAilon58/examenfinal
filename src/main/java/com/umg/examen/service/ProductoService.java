package com.umg.examen.service;

import com.umg.examen.dto.ProductoRequestDTO;
import com.umg.examen.dto.ProductoResponseDTO;
import com.umg.examen.dto.ProveedorDTO;
import com.umg.examen.entity.Producto;
import com.umg.examen.entity.Proveedor;
import com.umg.examen.repository.ProductoRepository;
import com.umg.examen.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<ProductoResponseDTO> getAllProductos() {
        return productoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductoResponseDTO> getProductoById(Integer id) {
        return productoRepository.findById(id)
                .map(this::toResponseDto);
    }

    public ProductoResponseDTO createProducto(ProductoRequestDTO requestDTO) {
        Producto producto = toEntity(requestDTO);
        return toResponseDto(productoRepository.save(producto));
    }

    public ProductoResponseDTO updateProducto(Integer id, ProductoRequestDTO productoDetails) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto not found"));
        Proveedor proveedor = proveedorRepository.findById(productoDetails.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor not found"));

        producto.setNombre(productoDetails.getNombre());
        producto.setDescripcion(productoDetails.getDescripcion());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setExistencia(productoDetails.getExistencia());
        producto.setProveedor(proveedor);
        return toResponseDto(productoRepository.save(producto));
    }

    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    private ProductoResponseDTO toResponseDto(Producto producto) {
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

    private Producto toEntity(ProductoRequestDTO requestDTO) {
        Producto producto = new Producto();
        producto.setNombre(requestDTO.getNombre());
        producto.setDescripcion(requestDTO.getDescripcion());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setExistencia(requestDTO.getExistencia());

        Proveedor proveedor = proveedorRepository.findById(requestDTO.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor not found"));
        producto.setProveedor(proveedor);
        return producto;
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
