package com.muebleria.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DetalleboletaId implements Serializable{
	private String num_bol;
    private String cod_prod;
}
