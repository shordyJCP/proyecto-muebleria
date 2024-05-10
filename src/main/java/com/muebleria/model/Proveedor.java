package com.muebleria.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_proveedores")
@Data
public class Proveedor {
	@Id
	private int cod_provee;
	private String razon;
	private String ruc;
	
	@Override
	public String toString() {
		return "Proveedor [cod_provee=" + cod_provee + ", razon=" + razon + ", ruc=" + ruc + "]";
	}

	public int getCod_provee() {
		return cod_provee;
	}

	public void setCod_provee(int cod_provee) {
		this.cod_provee = cod_provee;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	



}