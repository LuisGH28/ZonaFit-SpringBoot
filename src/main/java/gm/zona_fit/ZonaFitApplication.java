package gm.zona_fit;

import gm.zona_fit.medelo.Cliente;
import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

//@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger =
			LoggerFactory.getLogger(ZonaFitApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion");
		// Levantar la fabrica de spring
		 SpringApplication.run(ZonaFitApplication.class, args);
		 logger.info("Aplicacion finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("*** Aplicacion Zona Fit (GYM) ***");
		zonaFitApp();
	}

	private void zonaFitApp(){
		var out = false;
		var console = new Scanner(System.in);

		while(!out){
			try{
				var option = showMenu(console);
				out = optionExecute(console, option);
				logger.info("");
			}catch (Exception e){
                logger.info("Error al ejecutar opciones: {}", e.getMessage());
			}
		}
	}

	public int showMenu(Scanner console){
		logger.info("""
				*** Zona Fit (GYM)
					1. Listar Clientes
					2. Buscar Cliente
					3. Agregar Cliente
					4. Modificar Cliente\s
					5. Eliminar Cliente
					6. Salir
					Elige una opcion:\s
				""");
		return Integer.parseInt(console.nextLine());
	}

	public boolean optionExecute(Scanner console, int option){
		var out = false;
		switch (option){
			case 1 ->{
                logger.info("{} --- Listado de clientes --- {}", nl, nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info("{}{}", cliente.toString(), nl));
			}
			case 2 -> {
				logger.info(" --- Buscar cliente por id --- ");
				logger.info("Ingresa el id del cliente a buscar: ");
				var idCliente = Integer.parseInt(console.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null) logger.info("Cliente encontrado: {}", cliente);
				else logger.info("Cliente no encontrado: {}", cliente);
			}
			case 3 -> {
				logger.info(" --- Agregar cliente --- ");
				logger.info("Nombre: ");
				var name = console.nextLine();
				logger.info("Apellido: ");
				var lastName = console.nextLine();
				logger.info("Membresia: ");
				var membership = Integer.parseInt(console.nextLine());
				Cliente cliente = new Cliente();
				cliente.setNombre(name);
				cliente.setApellido(lastName);
				cliente.setMembresia(membership);
				clienteServicio.guardarCliente(cliente);
                logger.info("Cliente agregado: {}", cliente);
			}
			case 4 -> {
				logger.info(" --- Modificar cliente --- {}", nl);
				logger.info(" Id cliente: ");
				var idCliente = Integer.parseInt(console.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null) {
					logger.info("Nombre: ");
					var name = console.nextLine();
					logger.info("Apellido: ");
					var lastName = console.nextLine();
					logger.info("Membresia: ");
					var membership = Integer.parseInt(console.nextLine());
					cliente.setNombre(name);
					cliente.setApellido(lastName);
					cliente.setMembresia(membership);
					clienteServicio.guardarCliente(cliente);
					logger.info("Cliente modficado: {}", cliente );
				}else{
					logger.info("Cliente no encontrado: {} {}", cliente, nl);
				}
			}
			case 5 -> {
				logger.info(" --- Eliminar cliente --- ");
				logger.info("id cliente: ");
				var idCliente = Integer.parseInt(console.nextLine());
				var cliente = clienteServicio.buscarClientePorId(idCliente);
				if(cliente != null){
					clienteServicio.eliminarCliente(cliente);
					logger.info("Cliente eliminado: {} {}", cliente, nl);
				}else {
					logger.info("Cliente no encontrado: {} {}", cliente, nl);
				}

			}
			case 6 -> {
				logger.info("Hasta pronto! {}", nl);
				out = true;
			}
			default -> logger.info("Opcion invalida: {} {}", option, nl);
		}
		return out;
	}
}
