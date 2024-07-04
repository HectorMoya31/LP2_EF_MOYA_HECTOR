package com.example.demo.controller;

import java.awt.PageAttributes.MediaType;
import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.UsuarioEntity;

import service.ProductoService;
import service.UsuarioService;
import service.impl.PdfService;
@Controller
public class ProductoController {
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private PdfService pdfService;
	
	
	@GetMapping("/menu")
	public String showMenu(HttpSession session, Model model) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		
		String correo = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		model.addAttribute("foto",usuarioEntity.getImagen());
		
		List<ProductoEntity>productos = productoService.buscarTodosProductos();
		model.addAttribute("productos", productos);
		
		return "menu";
	}
	
	@PostMapping("/agregar_producto")
	public String agregarProducto(HttpSession session,@RequestParam("prodId") String prod,
			@RequestParam("cant") String cant) {
		
		List<Pedido> productos = null;
		if(session.getAttribute("carrito") == null) {
			productos = new ArrayList<>();
		}else {
			productos = (List<Pedido>) session.getAttribute("carrito");
		}
		
		Integer cantidad = Integer.parseInt(cant);
		Integer prodId = Integer.parseInt(prod);
		Pedido pedido = new Pedido(cantidad, prodId);
		productos.add(pedido);
		session.setAttribute("carrito", productos);
		return "redirect:/menu";
		
	}
	
	@GetMapping("/generar_pdf")
	public ResponseEntity<InputStreamResource>generarPdf(HttpSession session) throws IOException{
		List<Pedido>productoSession = null;
		if(session.getAttribute("carrito") == null) {
			productoSession = new ArrayList<Pedido>();
		}else {
			productoSession = (List<Pedido>) session.getAttribute("carrito");
		}
		List<DetallePedidoEntity> detallePedidoEntityList = new ArrayList<DetallePedidoEntity>();
		Double total = 0.0;
		
		for(Pedido pedido: productoSession) {
			DetallePedidoEntity detallePedidoEntity = new DetallePedidoEntity();
			ProductoEntity productoEntity = productoService.buscarProductoPorId(pedido.getProductoId());
			detallePedidoEntity.setProductoEntity(productoEntity);
			detallePedidoEntity.setCantidad(pedido.getCantidad());
			detallePedidoEntityList.add(detallePedidoEntity);
			total += pedido.getCantidad() * productoEntity.getPrecio();
		}
		
		Map<String, Object>datosPdf = new HashMap<String, Object>();
		datosPdf.put("factura", detallePedidoEntityList);
		datosPdf.put("precio_total", total);
		
		ByteArrayInputStream pdfBytes = pdfService.generarPdfDeHtml("template_pdf", datosPdf);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "inline; filename=productos.pdf");
		
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdfBytes));
	}
}
