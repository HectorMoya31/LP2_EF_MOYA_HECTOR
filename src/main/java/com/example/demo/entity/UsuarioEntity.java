package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;



@Entity
@Table(name = "tb_usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class UsuarioEntity {

	@Id
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "apellido", nullable = false)
	private String apellido;
	
	@Column(name = "correo", nullable = false, unique = true)
	private String correo;

	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "fecha", nullable = false)
	private Date fecha;
	
	@Column(name = "imagen", nullable = false)
	private String imagen;

}
