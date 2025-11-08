package com.umg.examen.service;

import com.umg.examen.entity.Proveedor;
import com.umg.examen.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> getProveedorById(Integer id) {
        return proveedorRepository.findById(id);
    }

    public Proveedor createProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Proveedor updateProveedor(Integer id, Proveedor proveedorDetails) {
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Proveedor not found"));
        proveedor.setNombre(proveedorDetails.getNombre());
        proveedor.setDireccion(proveedorDetails.getDireccion());
        proveedor.setTelefono(proveedorDetails.getTelefono());
        return proveedorRepository.save(proveedor);
    }

    public void deleteProveedor(Integer id) {
        proveedorRepository.deleteById(id);
    }
}
