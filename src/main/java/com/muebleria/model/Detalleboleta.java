package com.muebleria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

 @Data
 @Table(name="tb_det_boleta")
 @Entity
public class Detalleboleta {
	 @Id
	private String num_bol;
	
	private int cod_prod;
	private String nomProd;
	private int cantidad;
	private double preciovta;
	private double importe;
}
