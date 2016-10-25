package com.anthony;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CCValidator extends JFrame {
    private JTextField tbxCardNumberInput;
    private JButton btnValidate;
    private JButton btnQuit;
    private JPanel rootPanel;
    private JLabel lblValidMessage;
    private String ccNumber;

    CCValidator() {
        setContentPane(rootPanel);
        pack();
        setSize(500, 500);
        setTitle("Credit Validation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // btnValidate click
        btnValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ccNumber = tbxCardNumberInput.getText();
                boolean valid = isVisaCreditCardValid(ccNumber);
                if (valid) {
                    lblValidMessage.setText("Credit card number is valid");
                } else {
                    lblValidMessage.setText("Credit card numer is not valid");
                }
            }
        });
        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private boolean isVisaCreditCardValid(String cardNumber) {
        // Booleans to check validity
        boolean checkDigit = false;
        boolean checkLength = false;
        boolean checkStartDigit = false;
        // Convert the card number to an array of characters
        char[] cardNumArray = cardNumber.toCharArray();
        // Create boolean to check if the current number should be doubled
        boolean doubleThis = true;
        // Start digit check
        if (cardNumArray[0] == '4'){
            checkStartDigit = true;
        }

        // Length check
        if (cardNumArray.length == 16){
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

