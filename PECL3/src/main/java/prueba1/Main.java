/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba1;

/**
 *
 * @author vic88
 */
public class Main {

    public static void main(String[] args) {
        
        ParqueAcuatico parque = new ParqueAcuatico();
        GeneradorVisitantes generadorVisitantes = new GeneradorVisitantes(parque);
        generadorVisitantes.start();
        ControlUsuariosJFrame programa = new ControlUsuariosJFrame();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 programa.setVisible(true);
            }
        });
        
        
    }
}
