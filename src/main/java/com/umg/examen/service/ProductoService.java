package com.umg.examen.service;

import com.umg.examen.entity.Producto;
import com.umg.examen.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(Integer id) {
        return productoRepository.findById(id);
    }

    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Integer id, Producto productoDetails) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto not found"));
        producto.setNombre(productoDetails.getNombre());
        producto.setDescripcion(productoDetails.getDescripcion());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setExistencia(productoDetails.getExistencia());
        producto.setProveedor(productoDetails.getProveedor());
        return productoRepository.save(producto);
    }

    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }
}
