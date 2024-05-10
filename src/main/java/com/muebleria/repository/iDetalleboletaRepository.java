package com.muebleria.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.muebleria.model.Boleta;
import com.muebleria.model.Detalleboleta;
import com.muebleria.model.DetalleboletaId;
import com.muebleria.model.Productos;


@Repository
public interface iDetalleboletaRepository  extends JpaRepository<Detalleboleta,Integer>{
	
	@Query(value = "SELECT MAX(SUBSTRING(num_bol, 2)) FROM tb_boleta", nativeQuery = true)
    Optional<String> findMaxNumBoleta();

    default String generaNumBoleta() {
        String codigo = "B0001";  // valor del código por default, cuando no hay datos

        Optional<String> maxNumBoleta = findMaxNumBoleta();
        if (maxNumBoleta.isPresent()) {
            int maxNumBol = Integer.parseInt(maxNumBoleta.get());
            codigo = String.format("B%04d", maxNumBol + 1);
        }

        return codigo;
    }

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tb_boleta VALUES (?1, CURDATE(), ?2)", nativeQuery = true)
    int insertarCabeceraBoleta(String numBoleta, int codigo);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tb_det_boleta (num_bol, cod_prod, cantidad, preciovta , importe , nom_prod) VALUES (?1, ?2, ?3, ?4, ?5 , ?6)", nativeQuery = true)
    int insertarDetalleBoleta(String numBoleta, int codProd, int cantidad, double precioVta , double importe , String nom_prod);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_productos SET stock = stock - ?1 WHERE cod_prod = ?2", nativeQuery = true)
    int actualizarStock(int cantidad, int codProd);

    default int realizarVenta(Boleta cab, ArrayList<Detalleboleta> det) {
        int rs = 0;
        try {
            String numBoleta = generaNumBoleta();

          
            rs += insertarCabeceraBoleta(numBoleta, cab.getCodigo());

  
            for (Detalleboleta d : det) {
                rs += insertarDetalleBoleta(numBoleta, d.getCod_prod(), d.getCantidad(), d.getPreciovta(), d.getImporte() , d.getNomProd());
                rs += actualizarStock(d.getCantidad(), d.getCod_prod());
            }
        } catch (Exception e) {
            System.out.println("Error en realizarVenta: " + e.getMessage());
            rs = 0;
        }
        return rs;
    }

    
    
}
