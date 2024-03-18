import java.sql.*;
import java.util.Scanner;

public class Main {


    private static Connection connection;

    public static void main(String[] args){



        try{
            //to get user input
            Scanner scanner = new Scanner(System.in);

            //get url user and password to connect to pgAdmin
            String url  = "jdbc:postgresql://localhost:5432/COMP3005_A3_database";
            String user = "postgres";
            String password = "comp3005"; //depending on user`


            //first connect to the database correctly
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,user,password);

            //checking if the connection went ok
            if (connection != null){
                System.out.println("Connected To Database");
            }
            else{
                System.out.println("Failed to connect");
            }

            //menu of what can be used
            String message = """
                    Type the number of what you would like to do.
                    1. getAllStudents():
                    2. addStudent(first_name, last_name, email, enrollment_date):
                    3. updateStudentEmail(student_id, new_email):
                    4. deleteStudent(student_id):
                    5. Quit
                    """;
            System.out.println(message);
            String input = scanner.nextLine();

            while(!input.equals("5")){
                switch(input){
                    //first function
                    case "1":
                        getAllStudents();
                        break;

                    //second function
                    case "2":
                        //get user input
                        System.out.println("Add student");
                        System.out.println("First name:");
                        String fname = scanner.nextLine();
                        System.out.println("Last name:");
                        String lname = scanner.nextLine();
                        System.out.println("Email:");
                        String email = scanner.nextLine();
                        System.out.println("Enrollment Date (YYYY-MM-DD):");
                        String enrollment = scanner.nextLine();

                        addStudent(fname,lname,email,enrollment);
                        break;

                    //third function
                    case "3":
                        //get user input
                        System.out.println("Update email");
                        System.out.println("Student ID:");
                        int studentID = scanner.nextInt();
                        scanner.nextLine(); //to get user input after getting int have to do this
                        System.out.println("New email:");
                        String newEmail = scanner.nextLine();

                        updateStudentEmail(studentID,newEmail);
                        break;


                    //fourth function
                    case "4":
                        //get user input
                        System.out.println("Delete student");
                        System.out.println("Student ID:");
                        int studentToDelete = scanner.nextInt();
                        scanner.nextLine(); //to get user input after getting int have to do this

                        deleteStudent(studentToDelete);
                        break;

                    //just error checking
                    default:
                        System.out.println("Not one of the options. Please try again.");
                }

                //keep looping to ask for user input and to keep choosing which function
                System.out.println("\n");
                System.out.println(message);
                input = scanner.nextLine();
            }

            System.out.println("Thank you!");


        }
        catch (Exception e){
            if(e.getMessage().contains("No results were returned by the query.")){
                //dont print it
            }
            else{
                System.out.println(e);
            }
        }
    }


    //get all students function - prints out all students in the table
    public static void getAllStudents(){
        try{
            Statement statement = connection.createStatement();
            statement.executeQuery("Select * from students");

            //now to print it out - done like the video
            ResultSet rs  = statement.getResultSet();
            //print them in a good table
            while(rs.next()){
                System.out.printf("%-5s%-10s%-10s%-30s%s%n",
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("enrollment_date")

                        );

            }


        }
        catch (Exception e){
            System.out.println(e);

        }

    }

    //add student function - adds a new row to the table with data from arguments
    public static void addStudent(String first_name,String last_name,String email,String enrollment_date){
        try{
            Statement statement = connection.createStatement();
            //creating the string with user input
            String query =
            "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES  ('"
                    +first_name+"', '"+last_name +"', '"+email+"', '"+enrollment_date+"')";


            statement.executeQuery(query);


        }
        catch (Exception e){
            if(e.getMessage().contains("No results were returned by the query.")){
                //dont print it
            }
            else{
                System.out.println(e);
            }
        }
    }

    //update student email function - updates the student email with the new_email of student_id (the student's id)
    public static void updateStudentEmail(int student_id,String new_email){

        try{
            Statement statement = connection.createStatement();
            //create the query with the user input
            String query  = "UPDATE students SET email = '"+new_email+"' WHERE student_id = "+ student_id +";";
            statement.executeQuery(query);


        }
        catch (Exception e){
            if(e.getMessage().contains("No results were returned by the query.")){
                //dont print it
            }
            else{
                System.out.println(e);
            }
        }
    }

    //delete student function - deletes a specifc row where stutent id is equal to the one given
    public static void deleteStudent(int student_id){

        try{
            Statement statement = connection.createStatement();
            //create query with user input
            String query  = "DELETE FROM students WHERE student_id = "+ student_id +";";
            statement.executeQuery(query);


        }
        catch (Exception e){
            if(e.getMessage().contains("No results were returned by the query.")){
                //dont print it
            }
            else{
                System.out.println(e);
            }
        }
    }



}
