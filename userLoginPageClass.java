import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class loginData
{
    public boolean loginSuccess;
    public String username;
    public String password;

    loginData(boolean lS)
    {
        loginSuccess = lS;
        username = "";
        password = "";
    }
    loginData(boolean lS, String u, String p)
    {
        loginSuccess = lS;
        username = u;
        password = p;
    }
}

class userPassNode
{
    public String userPass;
    public long hashCode;
    public userPassNode next;

    userPassNode(String userPass, long hashCode)
    {
        this.userPass = userPass;
        this.hashCode = hashCode;
        next = null;
    }
}

class userPassList
{
    public userPassNode head;
    public userPassNode tail;

    userPassList()
    {
        head = null;
        tail = null;
    }

    public userPassNode purgeLL(userPassNode head)
    {
        if (head == null)
        {
            return null;
        }
        else
        {
            head.next = purgeLL(head.next);
            return null;
        }
    }

    public boolean containsUser(String userPass)
    {
        userPassNode temp = head;
        while (temp != null)
        {
            if (temp.userPass.equals(userPass))
            {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public boolean containsHashCode(long hashCode)
    {
        userPassNode temp = head;
        while (temp != null)
        {
            if (temp.hashCode == hashCode)
            {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public long getCode(String userPass)
    {
        userPassNode temp = head;
        while (temp != null)
        {
            if (temp.userPass.equals(userPass))
            {
                return temp.hashCode;
            }
            temp = temp.next;
        }

        return 0;
    }

    public String getPass(long hashCode)
    {
        userPassNode temp = head;
        while (temp != null)
        {
            if (temp.hashCode == hashCode)
            {
                return temp.userPass;
            }
            temp = temp.next;
        }

        return "0";
    }

    public void add(String userPass, long hashCode)
    {
        userPassNode newNode = new userPassNode(userPass, hashCode);
        if ( tail == null )
        {
            head = newNode;
            tail = head;
        }
        else
        {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void delete(String userPass)
    {
        userPassNode temp = head;
        if ( temp.userPass.equals(userPass) )
        {
            System.out.println("head deletion in progress...");
            userPassNode next = temp.next;
            head = next;
            return;
        }

        while ( temp != null )
        {
            userPassNode next = temp.next;
            if ( next != null && next.userPass.equals(userPass) )
            {
                System.out.println("node found, deletion in progress...");

                userPassNode nextNext = next.next;
                temp.next = nextNext;
                if ( nextNext == null )
                {
                    tail = temp;
                }
            }
            else if ( next == null )
            {
                System.out.println("Node not found.");
            }

            temp = temp.next;
        }
    }
}

class userLoginPageClass
{
    public final static long LIMITER = 36028797018963968L;

    public static long getHashCode(String key)
    {
        long hashCode = 0L;
        long currCoeff = 1L;
        int n = key.length();
        int i = n - 1;

        while ( i >= 0 )
        {
            hashCode += (key.charAt(i))*currCoeff;
            hashCode %= LIMITER;
            currCoeff *= 37;
            currCoeff %= LIMITER;

            i--;
        }
        return hashCode%LIMITER;
    }

    public static void loadUsers(userPassList userList, userPassList passList)
    {
        String folderName = "./credentialInfo/";
        String userHash = "userHash.txt";
        String passHash = "passHash.txt";

        try
        {
            userPassNode head = null;
            userPassNode tail = null;

            File userInputFile = new File(folderName+userHash);
            //Scanner scan = new Scanner(userInputFile);
            //System.out.println("test2");
            Scanner scanFile = new Scanner(userInputFile);
            while (scanFile.hasNext())
            {
                String user = "";
                long hash = 0L;


                user = scanFile.next();
                hash = scanFile.nextLong();
                userPassNode newNode = new userPassNode(user, hash);
                // System.out.println("test2");
                
                //System.out.println(user+" "+hash);

                if (tail == null)
                {
                    head = newNode;
                    tail = head;
                }
                else
                {
                    tail.next = newNode;
                    tail = newNode;
                }                
            }
            userList.head = head;
            userList.tail = tail;
            //scan.close();
            scanFile.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("username:hashcode file cannot be opened...");
        }
        
        try
        {
            userPassNode head = null;
            userPassNode tail = null;

            File userInputFile = new File(folderName+passHash);
            //Scanner scan = new Scanner(userInputFile);
            // System.out.println("test2");
            Scanner scanFile = new Scanner(userInputFile);
            
            while (scanFile.hasNext())
            {
                long hash = 0L;
                String pass = "";

                hash = scanFile.nextLong();
                pass = scanFile.next();
                
                userPassNode newNode = new userPassNode(pass, hash);
                // System.out.println("test2");
                
                //System.out.println(user+" "+hash);


                if (tail == null)
                {
                    head = newNode;
                    tail = head;
                }
                else
                {
                    tail.next = newNode;
                    tail = newNode;
                }
                
            }
            passList.head = head;
            passList.tail = tail;
            //scan.close();
            scanFile.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("username:hashcode file cannot be opened...");
        }
    }
    
    public static void unloadUsers(userPassList userList, userPassList passList)
    {
        String folderName = "./credentialInfo/";
        String userHash = "userHash.txt";
        String passHash = "passHash.txt";

        try
        {
            FileWriter userOutputFile = new FileWriter(folderName+userHash, false);
            userOutputFile.write("");
            userOutputFile.flush();

            userPassNode temp = userList.head;
            while (temp != null)
            {
                String user = temp.userPass;
                long hash = temp.hashCode;

                userOutputFile.write(user+" "+hash);
                userOutputFile.write(System.lineSeparator());
                
                temp = temp.next;
            }

            userOutputFile.close();
        }
        catch (IOException e)
        {
            System.out.println("username:hashcode file cannot be opened...");
        }

        try
        {
            FileWriter passOutputFile = new FileWriter(folderName+passHash, false);
            passOutputFile.write("");
            passOutputFile.flush();
            
            userPassNode temp = userList.head;
            while (temp != null)
            {
                long hash = temp.hashCode;
                String pass = temp.userPass;

                passOutputFile.write(hash+" "+pass);
                passOutputFile.write(System.lineSeparator());
                
                temp = temp.next;
            }

            passOutputFile.close();
        }
        catch (IOException e)
        {
            System.out.println("username:hashcode file cannot be opened...");
        }
    }

    public static void viewUsers(userPassList userList)
    {
        System.out.println("Available users: ");
        userPassNode head = userList.head;
        userPassNode temp = head;
        while (temp != null)
        {
            String user;
            user = temp.userPass;

            System.out.print(user+" ");
            temp = temp.next;
        }
        System.out.println();
    }

    public static loginData attemptLogin(userPassList userList, userPassList passList, Scanner scan)
    {
        System.out.println();
        System.out.println("Login Page");
        System.out.println("___________________________________________________________________________________\n");
        String username;
        System.out.print("Enter username: ");
        username = scan.nextLine();

        if (!userList.containsUser(username))
        {
            System.out.println("This username does not exist.");
        }
        else
        {
            long hashCode = userList.getCode(username);
            String password;
            System.out.println("Entered username exists.");
            System.out.print("Enter password for this username: ");
            password = scan.nextLine();
            int num = 1;

            while(true)
            {
                if ( !password.equals(passList.getPass(hashCode)) ) 
                {
                    System.out.println("The entered username and password do not match.");

                }
                else
                {
                    System.out.println("Successfully logged in!");
                    loginData couldLogin = new loginData(true, username, password);
                    return couldLogin;
                }

                System.out.print("Re-enter password (0 to go back): ");
                password = scan.nextLine();
                if ( password.equals("0") || num == 3)
                {
                    //System.out.println(password);
                    if (num == 3)
                    {
                        System.out.println("Password entered incorrectly 4 times.");
                    }
                    break;
                }
                num++;
            }
        }

        loginData couldLogin = new loginData(false);
        //scan.close();
        return couldLogin;
    }

    public static void accountCreate(userPassList userList, userPassList passList, Scanner scan)
    {
        System.out.println();
        System.out.println("Account Creation Page");
        System.out.println("___________________________________________________________________________________\n");
        String username;
        System.out.print("Enter username (0 to go back): ");
        username = scan.nextLine();

        if ( !username.equals("0" ) )
        {
            long hashCode = getHashCode(username);

            if ( passList.containsHashCode(hashCode) || username.length() <= 5)
            {
                if ( username.length() <= 5 )
                {
                    System.out.println("Username length cannot be less than 6 characters");
                }
                
                System.out.println("This username cannot be used.");
            }
            else
            {
                String password;
                System.out.print("Enter password (0 to go back): ");
                password = scan.nextLine();

                if ( password.length() <= 5 )
                {
                    System.out.println("Password length cannot be less than 6 characters");
                }

                if ( !password.equals("0")  && password.length() > 5)
                {
                    userList.add(username, hashCode);
                    passList.add(password, hashCode);

                    System.out.println("Account successfully created.");

                    String folderName = "./accessInfo/";
                    String userAccess = username + "Access.txt";

                    try
                    {
                        FileWriter userAccessFile = new FileWriter(folderName+userAccess);
                        userAccessFile.write("");
                        userAccessFile.flush();

                        userAccessFile.write("0"+" "+1);
                        userAccessFile.write(System.lineSeparator());
                        
                        userAccessFile.write("1"+" "+0);
                        userAccessFile.write(System.lineSeparator());

                        System.out.println("Access status for new accounts is set to private by default");
                        userAccessFile.close();
                    }
                    catch(IOException e)
                    {
                        System.out.println("Unable to open the user access file.");
                    }
                }
                else
                {
                    System.out.println("cancelling account creation...");
                }
            }
        }
        else
        {
            System.out.println("cancelling account creation...");
        }
    }

    public static void accountDelete(userPassList userList, userPassList passList, Scanner scan)
    {
        System.out.println();
        System.out.println("Account Deletion Page");
        System.out.println("___________________________________________________________________________________\n");
        String username;
        System.out.print("Enter username (0 to go back): ");
        username = scan.nextLine();

        if ( !username.equals("0") )
        {
            long hashCode = getHashCode(username);

            if ( !userList.containsUser(username) )
            {
                System.out.println("This username does not exist, it cannot be deleted.");
            }
            else
            {
                String password;
                System.out.print("Enter password (0 to go back): ");
                password = scan.nextLine();

                if ( !password.equals("0") )
                {
                    if ( password.equals(passList.getPass(hashCode)) )
                    {
                        userList.delete(username);
                        passList.delete(password);
                        System.out.println("Credentials matched, deleting account data.");

                        String contactFile = "./contactInfo/"+username+"Contacts.txt";

                        try
                        {
                            FileWriter contactFileWrite = new FileWriter(contactFile);
                            contactFileWrite.write("");
                            contactFileWrite.flush();
                            contactFileWrite.close();
                            
                            System.out.println("Contact info successfully deleted.");
                        }
                        catch (IOException e)
                        {
                            System.out.println("contacts.txt unable to be opened...");
                        }

                        String accessFile = "./accessInfo/"+username+"Access.txt";

                        try
                        {
                            FileWriter accessFileWrite = new FileWriter(accessFile);
                            accessFileWrite.write("");
                            accessFileWrite.flush();
                            accessFileWrite.close();

                            System.out.println("Access info successfully deleted.");
                        }
                        catch (IOException e)
                        {
                            System.out.println("access.txt unable to be opened...");
                        }
                        
                        System.out.println("Account deleted.");
                    }
                    else
                    {
                        System.out.println("Password does not match, cancelling account deletion.");
                    }
                }
                else
                {
                    System.out.println("cancelling account creation...");
                }
            }
        }
        else
        {
            System.out.println("cancelling account creation...");
        }
    }

    public static void pageOptions()
    {
        System.out.println("___________________________________________________________________________________");
        System.out.println();
        System.out.println("Account Management Page");
        System.out.println();
        System.out.println("Enter: ");
        System.out.println("0 to exit.");
        System.out.println("1 to view available users.");
        System.out.println("2 to login.");
        System.out.println("3 to create an account.");
        System.out.println("4 to delete an account.");
        System.out.println();

    }

    public static int userLoginPage(Scanner scan)
    {
        userPassList userList = new userPassList();
        userPassList passList = new userPassList();

        loadUsers(userList, passList);
        System.out.println();
        System.out.println("users loaded...");

        int opt = 1;

        while (true)
        {
            System.out.println();
            pageOptions();
            
            String optString = scan.nextLine();
            opt = mainClass.choiceConverter(optString);
            int num = 1;
            while (opt < 0 || opt > 4)
            {
                System.out.println("Input mistmatch, try again.");
                if (num == 5)
                {
                    System.out.println("");
                    pageOptions();
                    num = 0;
                }
    
                optString = scan.nextLine();
                opt = mainClass.choiceConverter(optString);
                num++;
            }

            if ( opt == 0 )
            {
                break;
            }
            else if ( opt == 1 )
            {
                viewUsers(userList);
            }
            else if ( opt == 2 )
            {
                loginData loginCheck = attemptLogin(userList, passList, scan);

                if ( loginCheck.loginSuccess )
                {
                    System.out.println("Login successful.");
                    unloadUsers(userList, passList);
                    System.out.println("users unloaded...");

                    System.out.println("entering contact page...");
                    opt = userContactPageClass.userContactPage(loginCheck.username, loginCheck.password, scan);

                    loadUsers(userList, passList);
                    System.out.println("users loaded...");
                }
                else
                {
                    System.out.println("Login failed.");
                }
            }
            else if ( opt == 3 )
            {
                accountCreate(userList, passList, scan);
            }
            else if ( opt == 4 )
            {
                accountDelete(userList, passList, scan);
            }
            else
            {
                System.out.println("work in progress...");
            }
        }
        unloadUsers(userList, passList);
        System.out.println("users unloaded...");


        return opt;
    }

}
