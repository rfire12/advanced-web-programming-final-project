package com.pucmm.edu.eventsmicroservice.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pucmm.edu.eventsmicroservice.DTO.InvoiceResponse;
import com.pucmm.edu.eventsmicroservice.DTO.ProductResponse;
import com.pucmm.edu.eventsmicroservice.Entities.Product;
import com.pucmm.edu.eventsmicroservice.Repositories.ProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    ProductsRepository productsRepository;

    @GetMapping("hello")
    public String app(HttpServletRequest request) {
        return "EVENTS, RUNNING ON: " + request.getLocalPort();
    }

    @CrossOrigin
    @PostMapping("invoice")
    public void realizarVenta(@RequestBody VentaResponse ventaResponse, HttpServletResponse response)
            throws IOException {
        ventaService.insertarVenta(ventaResponse);
        ventaService.enviarEmailConfirmacionVenta(ventaResponse);
        Venta vAux = ventaRepository.save(venta);
        VentaResponse vResponse = new VentaResponse();
        vResponse.monto = vAux.getMonto();
        vResponse.producto = producto.getNombreProducto();
        vResponse.usuario = vAux.getUsuario();

        return vResponse;
    }

    @CrossOrigin
    @GetMapping("products")
    public ArrayList<ProductResponse> obtenerProductos() {
        List<Product> products = productsRepository.findAll();
        ArrayList<ProductResponse> misProductos = new ArrayList<>();
        for (Product prod : products) {
            ProductResponse pResponse = new ProductResponse();
            pResponse.price = prod.getPrice();
            pResponse.name = prod.getName();
            misProductos.add(pResponse);
        }
        return misProductos;
    }

    @CrossOrigin
    @GetMapping("invoices")
    public List<InvoiceResponse> obtenerCompras() {
        return ventaService.obtenerAllVentas();
    }

    @CrossOrigin
    @GetMapping("invoices/{username}")
    public List<InvoiceResponse> obtenerVentasCliente(@PathVariable String username) {
        return ventaService.obtenerVentasByUser(username);
    }
}