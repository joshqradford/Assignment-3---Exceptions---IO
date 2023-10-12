//******************************************************************************************
// CustomerRating.java
//
// Josh Radford T00745233, Oct 11, 2023
//
// COMP 1231 Assignment 3 Question 1
// 
// This application reads and stores the results of a satisfaction survey for a company from
// a .txt file. Allows a user to add data for up to a maximum of 5 customers.
//
//******************************************************************************************

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class CustomerRating 
{
    // Create a DecimalFormat object for formatting doubles to 2 decimal places
    private static final DecimalFormat formatDouble = new DecimalFormat("0.00");  

    // Create an array of Customers to hold their data from the .txt file
    private static Customer[] surveyRecords = new Customer[5];

    // Keeps track of the number of records in the surveyRecords array
    private static int numRecords = 0;

    public static void main(String[] args) 
    {
        processDataFile();
        displayRecords();
        addRecords();
    }

    // Function to process all of the data in a given file and create new Customer objects with the data
    private static void processDataFile()
    {
        int recordIndex = 0;
        int invalidRecords = 0;

        try
        {
            // Creates a new reader and opens rating.txt and reads the first line
            BufferedReader reader = new BufferedReader(new FileReader("rating.txt"));
            String line = reader.readLine();

            // Parses through the rating.txt file and creates customer records, storing them in an array
            while (line != null)
            {
                // Splits the line at the Tab, converts the attributes to int and double respectively, and stores them in a new Customer instance
                String[] attributes = line.split("\\s+");

                // Exception handling for if data in .txt file contains data other than int or double
                try
                {
                    int age = Integer.valueOf(attributes[0]);
                    double rating = Double.valueOf(attributes[1]);
                    surveyRecords[recordIndex] = new Customer(age, rating);
                    numRecords++;
                }
                catch (NumberFormatException nfe)
                {
                    System.out.println("ERROR: Invalid data found in input file (line " + (numRecords+1) + "). Data attributes for this customer could not be recorded.");
                    invalidRecords++;
                }

                // Updates the record number and proceeds to a new line, until rating.txt is finished (null)
                recordIndex++;
                line = reader.readLine();
            }
            // Prints out summary of processed files
            System.out.println("File processing complete. " + numRecords + " out of " + (numRecords + invalidRecords) + " records processed.\n");
            reader.close();
        }
        catch (IOException e)
        {
            // Prints the stack trace exception to the console as directed in assignment instructions
            e.printStackTrace();
        }
        
    }

    // Function that displays survey records in an organized format to the console
    private static void displayRecords()
    {
        try
        {
            System.out.println("Most updated list of customer ratings:");
            System.out.println("--------------------------------------");
            System.out.println("     Age                   Rating     ");
            System.out.println("--------------------------------------");
            for (Customer customer : surveyRecords) {
                System.out.println("     " + customer.getAge() + "                     " + formatDouble.format(customer.getRating()));
            }
            System.out.println("--------------------------------------");
        }
        catch (NullPointerException npe)
        {
            // Do nothing
        }
    }

    // Function for adding Customer records manually via console
    private static void addRecords()
    {
        boolean addingRecords = true;
        Scanner userInput = new Scanner(System.in);

        // Continually ask the user for new records until they exit with the '!' command
        while (addingRecords)
        {
            System.out.print("Enter age[integer], followed by ONE [TAB] key, than a rating[decimal number]. Type '!' to exit: ");
            String answer = userInput.nextLine();

            // Leave the loop if the exit command is met
            if (answer.equals("!"))
            {
                addingRecords = false;
                userInput.close();
                break;
            }

            // Splitting the user's input into an array of customer attributes
            String[] custAttributes = answer.split("\\s+");

            // Convert the attributes to their appropriate types and catch exceptions for improper entries
            try
            {
                // Exception for any user input's over 2 attributes
                if (custAttributes.length != 2) 
                {
                    throw new IllegalArgumentException();
                }

                int age = Integer.valueOf(custAttributes[0]);
                double rating = Double.valueOf(custAttributes[1]);
                surveyRecords[numRecords] = new Customer(age, rating);

                System.out.println("Customer added: " + surveyRecords[numRecords].toString());
                numRecords++;
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("ERROR: Invalid number format. Record skipped.");
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("ERROR: The array is full. Record skipped.");
            }
            catch (IllegalArgumentException e)
            {
                if (custAttributes.length < 2)
                {
                    System.out.println("ERROR: Missing a parameter. Record skipped.");
                }
                else
                {
                    System.out.println("ERROR: Too many parameters. Record skipped.");
                }
            }
        }
        // Display the most updated list of customer ratings by calling appropriate function
        displayRecords();
        // Write all the current and new data to the .txt file
        writeToFile();
        // Calculate and print the age and rating average
        calculateAverages();
    }

    // Function for calculating the average age and rating of the Customer survey records
    private static void calculateAverages()
    {
        double ageTotal = 0;
        double ratingTotal = 0.0;

        // Gathers all of the information in the Customer records array and totals it to calculate average at the end
        for (Customer customer : surveyRecords) 
        {
            if (customer != null)
            {
                ageTotal += customer.getAge();
                ratingTotal += customer.getRating();
            }
        }

        System.out.println("Average age = " + formatDouble.format(ageTotal / numRecords));
        System.out.println("Average rating = " + formatDouble.format(ratingTotal / numRecords));
    }

    // Function for updating and writing all new Customer records to the .txt file
    private static void writeToFile()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rating.txt"))) 
        {
            for (Customer customer : surveyRecords) 
            {
                if (customer != null)
                {
                    writer.write(customer.getAge() + "  " + customer.getRating() + "\n");
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

}
