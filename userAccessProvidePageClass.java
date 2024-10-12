import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class userAccessProvidePageClass 
{
    public static void loadAccess(String username, userPassList userAccessList)
    {
        String folderName = "./accessInfo/";
        String userAccess = username + "Access.txt";

        try
        {
            userPassNode head = null;
            userPassNode tail = null;

            File userInputFile = new File(folderName+userAccess);
            Scanner scanFile = new Scanner(userInputFile);

            while (scanFile.hasNext())
            {
                String user = "";
                long access = 0L;

                user = scanFile.next();
                access = scanFile.nextLong();
                userPassNode newNode = new userPassNode(user, access);

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
            userAccessList.head = head;
            userAccessList.tail = tail;
            scanFile.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("username access file cannot be opened...");
        }
    }

    public static void unloadAccess(String username, userPassList userAccessList)
    {
        String folderName = "./accessInfo/";
        String userAccess = username + "Access.txt";

        try
        {
            FileWriter userOutputFile = new FileWriter(folderName+userAccess, false);
            userOutputFile.write("");
            userOutputFile.flush();

            userPassNode temp = userAccessList.head;
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
            System.out.println("username access file cannot be opened...");
        }
    }

    public static void viewUsersAccessPage(String username, userPassList userList)
    {
        System.out.println("Available users: ");
        userPassNode head = userList.head;
        userPassNode temp = head;
        while (temp != null)
        {
            String user;
            user = temp.userPass;

            if ( user.equals(username) )
            {
                temp = temp.next;
                continue;
            }
            
            System.out.print(user+" ");
            temp = temp.next;
        }
        System.out.println();
    }

    public static void viewAccessStatus(userPassList userAccessList)
    {
        System.out.println("Current access condition: ");

        userPassNode temp = userAccessList.head;
        while ( temp != null )
        {
            if ( (temp.userPass.equals("0")) && (temp.hashCode == 1) )
            {
                System.out.println("Private.");
                return;
            }

            if ( (temp.userPass.equals("1")) && (temp.hashCode == 1) )
            {
                System.out.println("Public.");
                return;
            }

            temp = temp.next;
        }

        System.out.println("With users:");
        
        boolean withAny = false;
        temp = userAccessList.head;
        while ( temp != null )
        {
            if ( temp.hashCode == 1 )
            {
                System.out.print(temp.userPass+" ");
                withAny = true;
            }
            temp = temp.next;
        }
        if ( !withAny )
        {
            System.out.println("Specific access is provided to no user. Switch to a private account for better use.");
        }
        System.out.println();
    }

    public static void provideAccess(userPassList userList, userPassList userAccessList, String usernameHost, Scanner scan)
    {
        String username;
        System.out.print("Enter username to give access to (0 to go back): ");
        username = scan.nextLine();

        if ( username.equals("0") )
        {
            return;
        }
        
        int num = 1;
        while ( num <= 5 )
        {
            if ( username.equals(usernameHost) )
            {
                System.out.println("Cannot give access to yourself!!!");
            }
            else    
            {
                userPassNode temp = userList.head;
                boolean userFound = false;
                while ( temp != null )
                {                    
                    if ( username.equals(temp.userPass) )
                    {
                        userFound = true;
                        break;
                    }
                    temp = temp.next;
                }

                if ( !userFound )
                {
                    System.out.println("This username does not exist.");
                }
                else
                {
                    userPassNode tempA = userAccessList.head;
                    // System.out.println("test1");//////////////////////////
                    while ( tempA != null )
                    {
                        // System.out.println("test2");//////////////////////////
                        // System.out.println(tempA.userPass);

                        if ( username.equals(tempA.userPass) )
                        {
                            // System.out.println("test3");//////////////////////////

                            if ( tempA.hashCode == 1 )
                            {
                                System.out.println("You have already provided this user with access");
                            }
                            else
                            {
                                System.out.println("Access provided to "+username);
                                tempA.hashCode = 1;
                            }

                            userPassNode tempB = userAccessList.head;
                            while ( tempB != null )
                            {
                                if ( tempB.userPass.equals("0") || tempB.userPass.equals("1") )
                                {
                                    tempB.hashCode = 0;
                                }

                                tempB = tempB.next;
                            }
                            return;
                        }

                        tempA = tempA.next;
                    }
                    
                    userAccessList.add(username, 1);
                    userPassNode tempB = userAccessList.head;
                    while ( tempB != null )
                    {
                        if ( tempB.userPass.equals("0") || tempB.userPass.equals("1") )
                        {
                            tempB.hashCode = 0;
                        }

                        tempB = tempB.next;
                    }
                    System.out.println("Access provided to "+username);
                    return;
                }
            }

            System.out.print("Re-enter username to give access to (0 to back): ");
            username = scan.nextLine();
        
            if ( username.equals("0") )
            {
                return;
            }
            num++;
        }
        System.out.println("Invalid username entered 5 times, going back.");
    }

    public static void revokeAccess(userPassList userList, userPassList userAccessList, String usernameHost, Scanner scan)
    {
        String username;
        System.out.print("Enter username to revoke access from (0 to go back): ");
        username = scan.nextLine();

        if ( username.equals("0") )
        {
            return;
        }
        
        int num = 1;
        while ( num <= 5 )
        {
            if ( username.equals(usernameHost) )
            {
                System.out.println("Cannot revoke access from yourself!!!");
            }
            else    
            {
                userPassNode temp = userList.head;
                boolean userFound = false;
                while ( temp != null )
                {                    
                    if ( username.equals(temp.userPass) )
                    {
                        userFound = true;
                        break;
                    }
                    temp = temp.next;
                }

                if ( !userFound )
                {
                    System.out.println("This username does not exist.");
                }
                else
                {
                    userPassNode tempA = userAccessList.head;
                    // System.out.println("test1");//////////////////////////
                    while ( tempA != null )
                    {
                        // System.out.println("test2");//////////////////////////
                        // System.out.println(tempA.userPass);

                        if ( username.equals(tempA.userPass) )
                        {
                            // System.out.println("test3");//////////////////////////

                            if ( tempA.hashCode == 0 )
                            {
                                System.out.println("This user does not have access.");
                            }
                            else
                            {
                                System.out.println("Access revoked from "+username);
                                tempA.hashCode = 0;
                            }

                            userPassNode tempB = userAccessList.head;
                            while ( tempB != null )
                            {
                                if ( tempB.userPass.equals("0") || tempB.userPass.equals("0") )
                                {
                                    tempB.hashCode = 0;
                                }

                                tempB = tempB.next;
                            }
                            return;
                        }

                        tempA = tempA.next;
                    }
                    
                    userAccessList.add(username, 0);
                    userPassNode tempB = userAccessList.head;
                    while ( tempB != null )
                    {
                        if ( tempB.userPass.equals("1") )
                        {
                            tempB.hashCode = 0;
                        }

                        tempB = tempB.next;
                    }
                    System.out.println("Access revoked from "+username);
                    return;
                }
            }

            System.out.print("Re-enter username to revoke access from (0 to go back): ");
            username = scan.nextLine();
            if ( username.equals("0") )
            {
                return;
            }
            num++;
        }
        System.out.println("Invalid username entered 5 times, going back.");
    }

    public static void makePublic(userPassList userAccessList)
    {
        userPassNode temp = userAccessList.head;
        while ( temp != null )
        {
            if ( temp.userPass.equals("1") )
            {
                if ( temp.hashCode == 1 )
                {
                    System.out.println("Account is already public.");
                }
                else
                {
                    temp.hashCode = 1;

                    userPassNode tempA = userAccessList.head;
                    while ( tempA != null )
                    {
                        if ( tempA.userPass.equals("0") )
                        {
                            tempA.hashCode = 0;
                        }
                        tempA = tempA.next;
                    }
                    System.out.println("Account made public.");
                }
            }
            temp = temp.next;
        }
    }

    public static void makePrivate(userPassList userAccessList)
    {
        userPassNode temp = userAccessList.head;
        while ( temp != null )
        {
            if ( temp.userPass.equals("0") )
            {
                if ( temp.hashCode == 1 )
                {
                    System.out.println("Account is already private.");
                }
                else
                {
                    temp.hashCode = 1;

                    userPassNode tempA = userAccessList.head;
                    while ( tempA != null )
                    {
                        if ( tempA.userPass.equals("1") )
                        {
                            tempA.hashCode = 0;
                        }
                        tempA = tempA.next;
                    }
                    System.out.println("Account made public.");
                }
            }
            temp = temp.next;
        }
    }

    public static void pageOptions()
    {
        System.out.println("___________________________________________________________________________________");
        System.out.println();
        System.out.println("Phonebook Access Management Page");
        System.out.println();
        System.out.println("Enter: ");
        System.out.println("0 to go back.");
        System.out.println("1 to view other users.");
        System.out.println("2 to provide access to a user.");
        System.out.println("3 to revoke access from a user.");
        System.out.println("4 to make account public (accessible to all).");
        System.out.println("5 to make account private (accessible to none).");
        System.out.println("6 to view current access status.");
        System.out.println();
    }

    public static int userAccessProvidePage(String username, Scanner scan)
    {
        userPassList userList = new userPassList();
        userPassList passList = new userPassList();
        userLoginPageClass.loadUsers(userList, passList);
            

        userPassList userAccessList = new userPassList();
        loadAccess(username, userAccessList);
        System.out.println("access file for user loaded...");

        System.out.println("___________________________________________________________________________________");
        System.out.println();
        System.out.println("Access control page for user: " + username);
        int opt = 1;
        System.out.println();
        while (true)
        {
            pageOptions();

            String optString = scan.nextLine();
            opt = mainClass.choiceConverter(optString);
            int num = 1;

            while (opt < 0 || opt > 6)
            {
                System.out.println("Input mistmatch, try again.");
                if (num == 5)
                {
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
                viewUsersAccessPage(username, userList);
            }
            else if ( opt == 2 )
            {
                provideAccess(userList, userAccessList, username, scan);
            }
            else if ( opt == 3 )
            {
                revokeAccess(userList, userAccessList, username, scan);
            }
            else if ( opt == 4 )
            {
                makePublic(userAccessList);
            }
            else if ( opt == 5 )
            {
                makePrivate(userAccessList);
            }
            else if ( opt == 6 )
            {
                viewAccessStatus(userAccessList);
            }
            else
            {
                //in prog
            }
        }

        unloadAccess(username, userAccessList);
        System.out.println("access control page unloaded...");

        return 1;
    }
}
