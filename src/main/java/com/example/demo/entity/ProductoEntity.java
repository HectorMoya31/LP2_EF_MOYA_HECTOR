package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;



@Entity
@Table(name = "tb_producto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ProductoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "producto_id")
	private Integer productoId;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "precio", nullable = false)
	private Double precio;
	
	@Column(name = "stock", nullable = false)
	private Integer stock;

	@Column(name = "password", nullable = false)
	private String password;

}
