package com.clinica.salud.service;

import com.clinica.salud.dao.FacturaDAO;
import com.clinica.salud.exception.RecursoNoEncontradoException;
import com.clinica.salud.modelo.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService {

    private final FacturaDAO facturaDAO;

    @Autowired
    public FacturaService(FacturaDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    public List<Factura> listarTodos() {
        return facturaDAO.listarTodos();
    }

    public Factura buscarPorId(int id) {
        Factura factura = facturaDAO.buscarPorId(id);
        if (factura == null) {
            throw new RecursoNoEncontradoException("Factura con id " + id + " no encontrada");
        }
        return factura;
    }

    public void insertar(Factura factura) {
        facturaDAO.insertar(factura);
    }

    public void actualizar(int id, Factura factura) {
        if (facturaDAO.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Factura con id " + id + " no encontrada");
        }
        factura.setIdFactura(id);
        facturaDAO.actualizar(factura);
    }

    public void eliminar(int id) {
        if (facturaDAO.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Factura con id " + id + " no encontrada");
        }
        facturaDAO.eliminar(id);
    }
}
