/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.exception;

/**
 *
 * @author pandpr02
 */
public class MeditraxException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MeditraxException(String message) {
        super(message);
    }
    public MeditraxException(String message, Exception e) {
        super(message, e);
    }
    
}
