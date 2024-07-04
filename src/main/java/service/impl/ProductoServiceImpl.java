package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.ProductoEntity;
import com.example.demo.repository.ProductoRepository;

import service.ProductoService;

public class ProductoServiceImpl implements ProductoService {
	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public List<ProductoEntity> buscarTodosProductos() {
		
		return productoRepository.findAll();
	}

	@Override
	public ProductoEntity buscarProductoPorId(Integer id) {
		// TODO Auto-generated method stub
		return productoRepository.findById(id.longValue()).get();
	}
}
