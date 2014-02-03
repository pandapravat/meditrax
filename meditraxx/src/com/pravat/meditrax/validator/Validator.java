/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 *
 * @author pandpr02
 */
public class Validator {
    
    public static boolean isNumber(String value) {
        return StringUtils.isNotBlank(value) && NumberUtils.isDigits(value);
        
    }
    public static boolean isMoney(String value) {
        return StringUtils.isNotBlank(value) && NumberUtils.isNumber(value);
    }

    public static boolean isBlank(String value) {
        return StringUtils.isBlank(value);
    }
}
