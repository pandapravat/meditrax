package com.pravat.meditrax.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import org.springframework.util.CollectionUtils;

import com.pravat.meditrax.bi.domain.SaleDetails;
import com.pravat.meditrax.util.UxUtils;

public class SaleTrasactionDetailsController implements Initializable{
	@FXML Label txIdplaceHolder;
	
	@FXML ListView<SaleDetails> transactionDetailsView;
	
	public void setSaleDetails(String transactionId, List<SaleDetails> saleDetails) {
		if(!CollectionUtils.isEmpty(saleDetails)) {
			
			txIdplaceHolder.setText(transactionId);
		}
		transactionDetailsView.getItems().addAll(saleDetails);
	}

	public void onOkClick(ActionEvent ae) {
		UxUtils.getStage(ae).close();
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*transactionDetailsView.setCellFactory(new Callback<ListView<SaleDetails>, ListCell<SaleDetails>>() {
			
			@Override
			public ListCell<SaleDetails> call(ListView<SaleDetails> param) {
				
				Callback<ListView<SaleDetails>, ListCell<SaleDetails>> forListView = TextFieldListCell.forListView(new StringConverter<SaleDetails>() {

					@Override
					public SaleDetails fromString(String arg0) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String toString(SaleDetails arg0) {
						
						arg0.toString()
						
					}
				});
				
			}
		});*/
		
	}
	

}
