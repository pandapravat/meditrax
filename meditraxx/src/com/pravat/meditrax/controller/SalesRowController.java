///*
//* To change this license header, choose License Headers in Project Properties.
//* To change this template file, choose Tools | Templates
//* and open the template in the editor.
//*/
//
//package com.pravat.meditrax.controller;
//
//import com.pravat.meditrax.validator.Validator;
//import javafx.fxml.FXML;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyEvent;
//import np.com.ngopal.control.AutoFillTextBox;
//
///**
// *
// * @author pandpr02
// */
//public class SalesRowController {
//    @FXML AutoFillTextBox itemAutoFill;
//    @FXML TextField unitPrice;
//    @FXML TextField quantity;
//    @FXML TextField totalPrice;
//    
//    
////    @FXML public void hadleAutoFillKeyReleased(KeyEvent ke) {
////        
////        ObservableList data = itemAutoFill.getData();
////        String text = itemAutoFill.getText();
////        if(StringUtils.isNotBlank(text)) {
////            for(int i=0; i<5; i++) {
////                data.add(text + i);
////            }
////        }
//////        itemAutoFill.getListview().requestLayout();
//////        itemAutoFill.setData(data);
////    }
//    @FXML public void handleQuantityKeyEvent(KeyEvent ke) {
//        String qtyVal = quantity.getText();
//        if(Validator.checkForNumber(qtyVal)) {
//            calculateRowTotal();
//        } else {
//            ke.consume();
//        }
//    }
//    @FXML public void handleUnitPriceKeyEvent(KeyEvent ke) {
//        String unitPriceVal = unitPrice.getText();
//        if(Validator.checkForMoney(unitPriceVal)) {
//            calculateRowTotal();
//        } else {
//            ke.consume();
//        }
//    }
//    // calculate the total of one row
//    private void calculateRowTotal() {
//        String unitPriceVal = unitPrice.getText();
//        String qtyVal = quantity.getText();
//        if(Validator.checkForMoney(unitPriceVal) && Validator.checkForNumber(qtyVal)) {
//            double iUnitPriceVal = Double.valueOf(unitPriceVal);
//            int dQtyVal = Integer.valueOf(qtyVal);
//            totalPrice.setText(String.valueOf(dQtyVal*iUnitPriceVal));
//        }
//    }
//    
//    
//    
//}
