package com.umg.examen.service;

import com.umg.examen.dto.ProveedorDTO;
import com.umg.examen.entity.Proveedor;
import com.umg.examen.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<ProveedorDTO> getAllProveedores() {
        return proveedorRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProveedorDTO> getProveedorById(Integer id) {
        return proveedorRepository.findById(id)
                .map(this::toDto);
    }

    public ProveedorDTO createProveedor(ProveedorDTO proveedorDTO) {
        Proveedor proveedor = toEntity(proveedorDTO);
        return toDto(proveedorRepository.save(proveedor));
    }

    public ProveedorDTO updateProveedor(Integer id, ProveedorDTO proveedorDetails) {
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Proveedor not found"));
        proveedor.setNombre(proveedorDetails.getNombre());
        proveedor.setDireccion(proveedorDetails.getDireccion());
        proveedor.setTelefono(proveedorDetails.getTelefono());
        return toDto(proveedorRepository.save(proveedor));
    }

    public void deleteProveedor(Integer id) {
        proveedorRepository.deleteById(id);
    }

    private ProveedorDTO toDto(Proveedor proveedor) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNombre(proveedor.getNombre());
        dto.setTelefono(proveedor.getTelefono());
        dto.setDireccion(proveedor.getDireccion());
        return dto;
    }

    private Proveedor toEntity(ProveedorDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(dto.getIdProveedor());
        proveedor.setNombre(dto.getNombre());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setDireccion(dto.getDireccion());
        return proveedor;
    }
}
