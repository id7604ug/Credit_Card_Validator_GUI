package com.anthony;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class CCValidator extends JFrame {
    private JTextField tbxCardNumberInput;
    private JButton btnValidate;
    private JButton btnQuit;
    private JPanel rootPanel;
    private JLabel lblValidMessage;
    private JComboBox cbxCardType;
    private String ccNumber;

    private boolean resetMessageOnKeyPress = false;

    private final String VISA = "Visa";
    private final String MASTERCARD = "Mastercard";
    private final String AMEX = "American Express";

    CCValidator() {
        setContentPane(rootPanel);
        pack();
//        setSize(500, 500); // Sets window size
        setTitle("Credit Validation");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        cbxCardType.addItem(VISA);
        cbxCardType.addItem(MASTERCARD);
        cbxCardType.addItem(AMEX);

        // btnValidate click
        btnValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ccNumber = tbxCardNumberInput.getText();
                boolean valid = false;
                if (cbxCardType.getSelectedItem().equals(VISA)){
                    valid = isCreditCardValid(ccNumber, "Visa");
                } else if (cbxCardType.getSelectedItem().equals(MASTERCARD)){
                    valid = isCreditCardValid(ccNumber, "Mastercard");
                } else if (cbxCardType.getSelectedItem().equals(AMEX)){
                    valid = isCreditCardValid(ccNumber, "AmEx");
                } else {
                    valid = false;
                }
                if (valid) {
                    lblValidMessage.setText("Credit card number is valid");
                } else {
                    lblValidMessage.setText("Credit card number is not valid");
                }
                resetMessageOnKeyPress = false;
            }
        });
        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        tbxCardNumberInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (resetMessageOnKeyPress) {
                    lblValidMessage.setText("~ Valid or not valid will be displayed here ~");
                    resetMessageOnKeyPress = false;
                }
            }
        });
    }

    private boolean isCreditCardValid(String cardNumber, String type) {
        // Booleans to check validity
        boolean checkDigit = false;
        boolean checkLength = false;
        boolean checkStartDigit = false;
        // Convert the card number to an array of characters
        char[] cardNumArray = cardNumber.toCharArray();
        // Create boolean to check if the current number should be doubled
        boolean doubleThis = true;
        // Start digit check
        if (type.equalsIgnoreCase("Visa")) { // works
            if (cardNumArray[0] == '4') {
                checkStartDigit = true;
            }
        } else if (type.equalsIgnoreCase("Mastercard")) { // works
            if (cardNumArray[0] == '5') {
                checkStartDigit = true;
            }
        } else if (type.equalsIgnoreCase("AmEx")) { // Test implementation
            if (cardNumArray[0] == '3' && (cardNumArray[1] == '7')) {
                checkStartDigit = true;
            }
        } else {
            checkStartDigit = false;
        }
        // Length check
        if (cardNumArray.length == 16){
            checkLength = true;
        } else if (type.equalsIgnoreCase("AmEx") && cardNumArray.length == 15) {
            checkLength = true;
        }
        // Check digit check- Number addition
        int cardNumberAdd = 0;
        for (int currentNumber : cardNumArray) {
            // Create varaible to store the integer value of the current card number
            // Character.getNumericValue(char c) gets the value of the char
            // http://stackoverflow.com/questions/19388037/converting-characters-to-integers-in-java
            // http://docs.oracle.com/javase/7/docs/api/java/lang/Character.html#getNumericValue%28char%29
            int number = Character.getNumericValue(currentNumber);
            // Check if the current number should be doubled
            if (doubleThis){
                // Check if the doubled number is higher than 10
                if (number * 2 > 9) {
                    cardNumberAdd += 1;
                    cardNumberAdd += (number * 2) - 10;
                    doubleThis = false;
                } else {
                    cardNumberAdd += number * 2;
                    doubleThis = false;
                }
            } else {
                cardNumberAdd += number;
                doubleThis = true;
            }
        }
        if (cardNumberAdd % 10 == 0){
            checkDigit = true;
        }
        // Return the result of the calculations
        return (checkDigit && checkLength && checkStartDigit);
    }
}

