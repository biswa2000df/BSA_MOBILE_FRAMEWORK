package com.mahindra.mobile;

import java.util.Random;
import java.util.Scanner;

public class demoTesting {
	
	  public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Enter the number of digits: ");
	        int numDigits = scanner.nextInt();

	        // Calculate the minimum and maximum values for the given number of digits
	        int min = (int) Math.pow(10, numDigits - 1);
	        int max = (int) Math.pow(10, numDigits) - 1;

	        // Generate the random number in the given range
	        Random random = new Random();
	        int randomNumber = min + random.nextInt(max - min + 1);

	        System.out.println("Random " + numDigits + "-digit number: " + randomNumber);
	    }

}
