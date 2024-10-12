import java.util.Scanner;

public class mainClass
{
    public static int choiceConverter(String input)
    {
        if (input.length() == 0)
        {
            return 100;
        }

        int num = 0;
        int n = input.length();
        int i = 0;

        while ( i <= n-1 )
        {
            num *= 10;
            num += input.charAt(i) - '0';
            i++;
        }

        return num;
    }
    public static void main(String args[])
    {
        Scanner scan = new Scanner(System.in);
        int opt = 2;
        while ( opt != 0 )
        {
            if ( opt == 2 )
            {
                System.out.println("entering welcome page...");
                System.out.println("___________________________________________________________________________________");
                System.out.println("\nWelcome to the Contact Management System ");
                opt = welcomePageClass.welcomePage(scan);
            }
            else if ( opt == 1 )
            {
                System.out.println("entering login page...");
                System.out.println("___________________________________________________________________________________");
                opt = userLoginPageClass.userLoginPage(scan);
            }
            else
            {
                //nothing
            }
        }

        if ( opt != 0 )
        {
            System.out.println("premature exit, goodbyepage not called, something went wrong...");            
        }
        else
        {
            System.out.println("entering goodbye page...");
            System.out.println("___________________________________________________________________________________");
            opt = goodbyePageClass.goodbyePage();
        }

        scan.close();
    }
}