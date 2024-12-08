// okay let's start by using some packages of the Java API you may not have yet seen

import java.net.*;
import java.io.*;

// NOTE:: You probably can't answer these without reading ahead through the code and making some best guesses!
// Question 1: Why are we using the java.net package? What classes are used from that package? Which methods of those classes?
// Question 2: Why use the java.io package? Which classes are we using? Why? Which methods?

public class API_PracticeEPA { // Class begins

    // This first line states that the program may bail out, i.e., throw an Exception.
    // Q2: Using the Java API page for the class URL, find what Exception the constructor for the URL class can throw.

    public static void main(String[] args) throws Exception { // Main method begins

        // Example ZIP codes for Central Campus, West Campus, Albuquerque, NM
        String[] zipCodes = {"19422", "19464", "87101"};

        boolean isHeaderPrinted = false; // To print the header only once

        // Now we are going to use a Web Service from the EPA, the Environmental Protection Agency.
        //
        // We'll create a query
        // and have the EPA server talk to our program and send us the information we request. This is the main
        // mechanism for communication and transmission of information over the Internet, not printing to the screen!
        //
        //
        // Two popular formats that allow two computers to communicate are XML and JSON. As beginners, we'll
        // just use an easier format called CSV.
        // Q3. What is CSV?

        // Loop through the list of ZIP codes
        for (String zip : zipCodes) {
            try {
                System.out.println("\nFetching data for ZIP Code: " + zip);

                // Q4: Go to the documentation page for the EPA API.
                // https://www.epa.gov/enviro/web-services#hourlyzip
                // Run this program with the URL query as it is written below. What happens?
                // Copy the URL from the line below into a new tab of your browser. What does it return?
                URL epaServer = new URL("https://enviro.epa.gov/enviro/efservice/getEnvirofactsUVHOURLY/ZIP/" + zip + "/CSV");
                URLConnection ac = epaServer.openConnection();

                InputStreamReader inputStream = new InputStreamReader(ac.getInputStream(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStream);
                StringBuilder responseBuilder = new StringBuilder();

                String line;

                // Read and append each line of the response
                while ((line = bufferedReader.readLine()) != null) {
                    if (!isHeaderPrinted) {
                        System.out.println("Header: " + line); // Print header once
                        isHeaderPrinted = true;
                    } else {
                        responseBuilder.append(line).append("\n");
                    }
                }
                bufferedReader.close();

                // Print the result for the current ZIP code
                System.out.println("\n=============================");
                System.out.println("Response for ZIP Code " + zip + ":");
                System.out.println("=============================");
                System.out.println(responseBuilder.toString());

                // Save data to a file
                try (FileWriter writer = new FileWriter("UV_Data_" + zip + ".csv")) {
                    writer.write(responseBuilder.toString());
                    System.out.println("Data saved to UV_Data_" + zip + ".csv");
                } catch (IOException e) {
                    System.err.println("Error saving data to file for ZIP Code " + zip + ": " + e.getMessage());
                }

            } catch (Exception e) {
                System.err.println("Error fetching data for ZIP Code " + zip + ": " + e.getMessage());
            }
        }

        // Q5: Edit this program so that you query the EPA for UV levels at a list of locations (Central Campus, West Campus, and Albuquerque, NM)
        // Your program should automatically run through this list of locations and retrieve the data from the EPA for each.
    } // End of main

} // End of program