package com.muebleria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_productos")
@Data
public class Productos {
	@Id
	private int cod_prod;
	private int cod_provee;
	private String descripcion;
	private int idtipo;
	private int stock;
	private double precio;
	@Override
	public String toString() {
		return "Productos [cod_prod=" + cod_prod + ", cod_provee=" + cod_provee + ", descripcion=" + descripcion
				+ ", idtipo=" + idtipo + ", stock=" + stock + ", precio=" + precio + "]";
	}
	public Productos() {
		super();
	}
	
	public int getCod_prod() {
		return cod_prod;
	}
	public void setCod_prod(int cod_prod) {
		this.cod_prod = cod_prod;
	}
	public int getCod_provee() {
		return cod_provee;
	}
	public void setCod_provee(int cod_provee) {
		this.cod_provee = cod_provee;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdtipo() {
		return idtipo;
	}
	public void setIdtipo(int idtipo) {
		this.idtipo = idtipo;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}



	



}