package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsadeideas.springboot.app.models.entity.Factura;

public interface IFacturaDao extends JpaRepository<Factura, Long> {

}
