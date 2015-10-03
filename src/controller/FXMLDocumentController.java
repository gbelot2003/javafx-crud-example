/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Personas;

/**
 *
 * @author ajax
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TableView TableId;
       
    @FXML
    private TextField txtAplellido;
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private Button btn;
    
    @FXML
    private Button btnDel;
    
    @FXML
    private Label lblMessage;
    
    @FXML
    private Label lblId;
   
    /**
     * public getId TableColumn
     * configuramos las propiedades de las columnas desde el inicio
     * de esta forma nos evitamos codigo extra mas adelante
     * 
     * 
     * @return 
     */
    public static TableColumn<Personas, Integer> getId(){
        TableColumn<Personas, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setMinWidth(50);
        id.setText("id");
        return id;
    }

    public static TableColumn<Personas, String> getNombre(){
        TableColumn<Personas, String> nombre = new TableColumn<>("name");
        nombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        nombre.setMinWidth(100);
        nombre.setText("Nombre");
        return nombre;
    }
    
    public static TableColumn<Personas, String> getApellido(){
        TableColumn<Personas, String> apellido = new TableColumn<>("lastName");
        apellido.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        apellido.setMinWidth(100);
        apellido.setText("Apellido");
        return apellido;
    }
    
    /**
     * privatte handleDeleteAction
     * No usamos las funciones lamda en este ejemplo que es 
     * mas propio del uso del archivo FXML.
     * 
     * @param event 
     */
    @FXML
    private void handleDeleteAction(ActionEvent event){
       
        /**
         * Instanciamos el objeto de conexion (Hay que comprobar las conexiones)
         */
        Operaciones oper = new Operaciones();
    
        /**
         * Hacemos casting al id recuperado desde la etiqueta lblId
         */
        int foo = Integer.parseInt(lblId.getText());
        
        /**
         * Pasamos la funcion del objeto de conexion
         */
        oper.getDeletePersona(foo);
        
        /**
         * Reiniciamos la tabla
         */
        tableInit();
        
        /**
         * Limpiamos tabla y campos
         */
        clearTxt();
        
        lblMessage.setText("Registro eliminado");
    
    }
    
    /**
     * handleButtonAction
     * esta funcion la convertimos en multipropositos, ahora podremos
     * crear o actualizar con el mismo boton-
     * ademas, activamos el boton de borrar si este esta desactivado
     * y lo desactivamos una vez limpios los campos y ejecutada la acción
     * 
     * @param event 
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        /**
         * Definimos si es edición/borrado o creación de registro
         */
        if("".equals(lblId.getText())){
            
            /**
             * Las casillas de texto no deben estar vacias
             */
            if("".equals(txtNombre.getText()) || "".equals(txtAplellido.getText())){
                clearTxt();
                lblMessage.setText("Nombre y apellido deben tener contenido");
                
            } else {
                /**
                 * Ejecutamos operacion de nuevo registro
                 */
                Operaciones oper = new Operaciones();
                Personas persona = new Personas(txtNombre.getText(), txtAplellido.getText());
                oper.crearPersonas(persona);
                tableInit();      
                clearTxt();
                btnDel.setDisable(true);
                
            }
            
        } else {
            
            /**
             * Aunque el nombre no es amigable en este caso, getPersona() es la funcion de actualización
             * recive 3 parametros, String nombre, String apellido y Id
             */
            Operaciones oper = new Operaciones();
            int foo = Integer.parseInt(lblId.getText());
            oper.getPersona(txtNombre.getText(), txtAplellido.getText(), foo);
            tableInit();
            clearTxt();
            lblMessage.setText("Estas por actualizar o eliminar");
        
        }
    }
       
    /**
     * Funcion de limpieza de formulario
     * a lo Visual Studio :P
     */
    private void clearTxt(){
        lblId.setText("");
        txtNombre.setText("");
        txtAplellido.setText("");
        btnDel.setDisable(true);
    }
       
    /**
     * Funcion de inicializacion y actualización de tabla
     */
    public void tableInit(){
        
        /**
         * Desabilitamos el boton de borrado de registros
         * este solo debe estar activo cuando hay registros seleccionados.
         */
        btnDel.setDisable(true);
        
        /**
         * Cambiamos el nombre del boton de acción principal
         */
        btn.setText("Crear Registro");
        
        /**
         * Instanciamos el Objeto Operaciones 
         */
        Operaciones oper = new Operaciones();
        
        // getPersonas2() debuelve una lista observable
        ObservableList<Personas> listados = oper.getPersonas2();
        
        /**
         * Limpiamos los registros de la
         * tabla si existe, y actualizamos la información.
         */
        TableId.getColumns().clear();
        TableId.getItems().clear();
       
        /**
         * Solo llamamos al listado anteriormente instaciado desde getPersonas2().
         */ 
        TableId.setItems(listados);
        
        /**
         * Aquí usamos el codigo de las lineas de la 58 a la 79
         * las columnas preconfiguradas.
         */
        TableId.getColumns().addAll(FXMLDocumentController.getId(), 
                                    FXMLDocumentController.getNombre(), 
                                    FXMLDocumentController.getApellido());
        
        /**
         *  Entender bien esto me saco los pelos
         *  La magia esta en la instancia de del nuevo listChangeListener.
         */
        TableId.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Personas>() {
            
            /**
             * Aquí en onChanged vigilamos los cambios de selección en la tabla,
             * esta funciona con las teclas o con el mouse (increiblemente facil de implementar).
             * 
             * @param c 
             */
           @Override
            public void onChanged(ListChangeListener.Change<? extends Personas> c) {
               
                /**
                 * Desabilitamos el boton de borrado y cmbiamos
                 * el texto del boton de acción principal.
                 */
                btnDel.setDisable(false);
                btn.setText("Editar registro");
                
                /**
                 * Este es el loop que se encarga de vigilar propiamente
                 * las selecciones en la tabla; recibe c desde arriba en onChanged 
                 * y cada ves que cambia el foco en cada fila debido a un click del 
                 * raton o a las teclas de arriba abajo, nos debuelve el
                 * valor de la fila con foco !!!.
                 * 
                 */
                for (Personas p : c.getList()) {
                    lblId.setText(p.getId().toString());
                    txtNombre.setText(p.getName());
                    txtAplellido.setText(p.getLastName());
                }
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableInit();
    }
           
}
