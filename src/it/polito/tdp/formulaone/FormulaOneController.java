package it.polito.tdp.formulaone;

import java.net.URL;
import java.sql.Driver;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Drivers;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Season> boxAnno;

    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	Season s = boxAnno.getValue();
    	if(s==null)
    		txtResult.setText("Selezionare una stagione");
    	else{
    		txtResult.clear() ;
    		Drivers best = model.getMigliore(s) ;
    		txtResult.setText("il pilota migliore è "+best);
//    		+best.getVittorie().size());
    	}

    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
    	String input = textInputK.getText() ;
    	
    	if(input == null)
    		txtResult.setText("Inserire un numero ");
    	
    	try{
    		int k = Integer.parseInt(input) ;
    		List<Drivers> dreamTeam = model.getDreamTeam(k) ;
    		if(dreamTeam == null)
    			txtResult.setText("Selezionare prima il bottone per creare il grafo");
    		else{
    			txtResult.setText("Il DREAM TEAM E' FORMATO DA : " +dreamTeam);
    		}
    	}
    	catch(NumberFormatException e ){
    		txtResult.setText("Inserire un numero ");
    	}
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    	boxAnno.getItems().addAll(model.getSeasons()) ;
    }
}
