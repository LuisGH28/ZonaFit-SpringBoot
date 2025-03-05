package gm.zona_fit.controlador;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gm.zona_fit.medelo.Cliente;
import gm.zona_fit.servicio.IClienteServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.View;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

@Component
@Data
@ViewScoped 
public class IndexControlador {

  @Autowired
  IClienteServicio clienteServicio;
  private List<Cliente> clientes;
  private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

  @PostConstruct
  public void init(){
    cargarDatos();
  }

  public void cargarDatos(){
    this.clientes =  this.clienteServicio.listarClientes();
    this.clientes.forEach(cliente -> logger.info(cliente.toString()));
  }
}
