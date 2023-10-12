//******************************************************************************************
// Customer.java
//
// Josh Radford T00745233, Oct 11, 2023
//
// COMP 1231 Assignment 3 Question 1
// 
// This class initializes a Customer object with two attributes: age and rating. 
//
//******************************************************************************************


public class Customer 
{
    private int age;
    private double rating;

    // Default constructor for Customer with no parameters
    public Customer() {}

    // Constructor that initializes a Customer with their age and customer rating
    public Customer(int age, double rating)
    {
        this.age = age;
        this.rating = rating;
    }

    // Getter method for Customer's age
    public int getAge() 
    {
        return age;
    }

    // Setter method for Customer's age
    public void setAge(int age) 
    {
        this.age = age;
    }

    // Getter method for Customer's rating
    public double getRating() 
    {
        return rating;
    }

    // Setter method for Customer's rating
    public void setRating(double rating) 
    {
        this.rating = rating;
    }

    // Returns a readable description of the Customer instance
    public String toString() 
    {
        return "Age = " + age + " | Rating = " + rating;
    }


}