/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 *
 * @author vic88
 */
public class Main {

    public static void main(String[] args) throws Exception {
        
        ControlUsuariosJFrame controlUsuarios = new ControlUsuariosJFrame();
        RegistroVisitantes registro = new RegistroVisitantes(controlUsuarios);
        ParqueAcuatico parque = new ParqueAcuatico(registro);
        GeneradorVisitantes generadorVisitantes = new GeneradorVisitantes(parque);
        generadorVisitantes.start();
        
        try { //special exception handler for registry creation
        	RegistroVisitantesRMI stub = (RegistroVisitantesRMI) UnicastRemoteObject.exportObject(registro,0);
            Registry reg;
            try {
            	reg = LocateRegistry.createRegistry(1099);
                System.out.println("java RMI registry created.");

            } catch(Exception e) {
            	System.out.println("Using existing registry");
            	reg = LocateRegistry.getRegistry();
            }
        	reg.rebind("ServerRMI", stub);

        } catch (RemoteException e) {
        	e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 controlUsuarios.setVisible(true);
            }
        });
        
        
    }
}
