 package com.muebleria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.muebleria.model.Boleta;

public interface IBoletaRepository extends JpaRepository<Boleta, String> {

	
	@Query(value = "SELECT MAX(SUBSTRING(num_bol, 2)) FROM Boleta")
    Optional<String> findMaxNumBoleta();
}
