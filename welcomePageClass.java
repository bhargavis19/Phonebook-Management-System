import java.util.Scanner;
import java.io.File;

public class welcomePageClass
{ 
    public static void pageOptions()
    {
        System.out.println("___________________________________________________________________________________");
        System.out.println();
        System.out.println("Welcome Page");
        System.out.println();
        System.out.println("Enter: ");
        System.out.println("0 to exit");
        System.out.println("1 to proceed to user login page");
        System.out.println();
    }

    public static int welcomePage(Scanner scan)
    {
        pageOptions();
        int opt = 1;
        //Scanner scan = new Scanner(System.in);
        String optString = scan.nextLine();
        opt = mainClass.choiceConverter(optString);
        
        int num = 1;
        while (opt < 0 || opt > 1)
        {
            System.out.println("Input mistmatch, try again");
            if (num == 5)
            {
                System.out.println();
                pageOptions();
                num = 0;
            }

            optString = scan.nextLine();
            opt = mainClass.choiceConverter(optString);
            num++;
        }
        // opt = scan.nextInt();
        // scan.nextLine();

        if ( opt == 0 )
        {
            return 0;

        }
        else
        {
            System.out.println("creating necessary directories...");
            boolean check;

            String dirname = "credentialInfo";
            File f1 = new File(dirname);
            check = f1.mkdir();
            if ( check )
            {
                System.out.println(dirname+" folder created...");
            }
            else
            {
                System.out.println(dirname+" folder already exists...");
            }

            dirname = "contactInfo";
            f1 = new File(dirname);
            check = f1.mkdir();            
            if ( check )
            {
                System.out.println(dirname+" folder created...");
            }
            else
            {
                System.out.println(dirname+" folder already exists...");
            }

            dirname = "accessInfo";
            f1 = new File(dirname);
            check = f1.mkdir();            
            if ( check )
            {
                System.out.println(dirname+" folder created...");
            }
            else
            {
                System.out.println(dirname+" folder already exists...");
            }
        }    
    
        opt = 1;
        return opt;
    }
}
