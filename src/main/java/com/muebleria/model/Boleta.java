package com.muebleria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

	@Entity
	@Data
	@Table(name="tb_boleta")
public class Boleta {
	@Id
	private String num_bol;
	private String fch_bol;
	private int codigo;
}
