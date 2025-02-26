package gm.zona_fit.gui;

import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Component
public class ZonaFitForma extends JFrame{
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    IClienteServicio clienteServicio;
    private DefaultTableModel tablaModeloClientes;

    @Autowired // Esta inyeccion de dependencia es para que la inyeccion de datos se de rapido o manera instantanea
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio =  clienteServicio;
        iniciarForma();
    }

    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null); //centrar ventana
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloClientes = new DefaultTableModel(0, 4);
        String[] cabeceros = {"Id", "Nombre", "Apellido", "Memebrensia"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(tablaModeloClientes);

        //Cargar listado de clientes
        listarClientes();
    }

    private void listarClientes(){
        this.tablaModeloClientes.setRowCount(0);
        var clientes = this.clienteServicio.listarClientes();
        clientes.forEach(cliente -> {
           Object[] renglonCliente = {
                   cliente.getId(),
                   cliente.getNombre(),
                   cliente.getApellido(),
                   cliente.getMembresia()
           };
           this.tablaModeloClientes.addRow(renglonCliente);
        });
    }
}
