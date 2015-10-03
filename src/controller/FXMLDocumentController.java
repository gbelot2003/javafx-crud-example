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
import javafx.scene.control.SelectionMode;
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
    
    @FXML
    private void handleDeleteAction(ActionEvent event){
        Operaciones oper = new Operaciones();
        int foo = Integer.parseInt(lblId.getText());
        oper.getDeletePersona(foo);
        tableInit();
        clearTxt();
        lblMessage.setText("Registro eliminado");
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        if("".equals(lblId.getText())){
            
            if("".equals(txtNombre.getText()) || "".equals(txtAplellido.getText())){
                clearTxt();
                lblMessage.setText("Nombre y apellido deben tener contenido");
                
            } else {
                
                Operaciones oper = new Operaciones();
                Personas persona = new Personas(txtNombre.getText(), txtAplellido.getText());
                oper.crearPersonas(persona);
                tableInit();      
                clearTxt();
                btnDel.setDisable(true);
                
            }
            
        } else {
            
            Operaciones oper = new Operaciones();
            int foo = Integer.parseInt(lblId.getText());
            oper.getPersona(txtNombre.getText(), txtAplellido.getText(), foo);
            tableInit();
            clearTxt();
            lblMessage.setText("Estas por actualizar o eliminar");
        
        }
    }
       
    private void clearTxt(){
        lblId.setText("");
        txtNombre.setText("");
        txtAplellido.setText("");
        btnDel.setDisable(true);
    }
       
    public void tableInit(){
        
        btnDel.setDisable(true);
        
        btn.setText("Crear Registro");
        
        Operaciones oper = new Operaciones();
        ObservableList<Personas> listados = oper.getPersonas2();
        
        TableId.getColumns().clear();
        TableId.getItems().clear();
        
        TableId.setItems(listados);
        TableId.getColumns().addAll(FXMLDocumentController.getId(), 
                                    FXMLDocumentController.getNombre(), 
                                    FXMLDocumentController.getApellido());
        
        /**
         * 
         */
        //TableId.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        /**
         * 
         */
        TableId.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Personas>() {
            
           @Override
            public void onChanged(ListChangeListener.Change<? extends Personas> c) {
                
                btnDel.setDisable(false);
                btn.setText("Editar registro");
                
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
