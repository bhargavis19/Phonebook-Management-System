import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class contactImportPageClass
{
    public static canAccessUser accessUser(String username, userPassList userList, Scanner scan)
    {
        System.out.println();
        System.out.print("Enter username to access (0 to go back): ");

        String accessUsername;
        accessUsername = scan.nextLine();
        if ( accessUsername.equals("0") )
        {
            canAccessUser failed = new canAccessUser();
            return failed;
        }

        int num = 1;
        while ( num <= 5 )
        {
            boolean canAccess = false;
            if ( accessUsername.equals(username) )
            {
                System.out.println("You can already access your own contacts!!!");
                // canAccessUser failed = new canAccessUser();
                // return failed; //create a loop here??
            }
            else
            {
                boolean userExists = false;
                userPassNode temp = userList.head;
                while ( temp != null )
                {
                    if ( accessUsername.equals(temp.userPass) )
                    {
                        userExists = true;
                        break;
                    }
                    temp = temp.next;
                }

                if ( !userExists )
                {
                    System.out.println("This username does not exist.");
                    // canAccessUser failed = new canAccessUser();
                    // return failed; //create a loop here??
                }
                else
                {
                    String accessFile = "./accessInfo/"+accessUsername+"Access.txt";
                    userPassList userAccessList = new userPassList();
                    try
                    {
                        userPassNode head = null;
                        userPassNode tail = null;

                        File userAccessFile = new File(accessFile);
                        Scanner scanFile = new Scanner(userAccessFile);

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

                        System.out.println("access list loaded for "+accessUsername+"...");
                       
                        userPassNode tempA = userAccessList.head;
                        boolean publicPrivate = false;
                        while ( tempA != null && publicPrivate == false )
                        {
                            if ( tempA.userPass.equals("0") && tempA.hashCode == 1 )
                            {
                                System.out.println(accessUsername+"'s access status is set to private, can not access.");
                                canAccess = false;
                                publicPrivate = true;
                            }
                            if ( tempA.userPass.equals("1") && tempA.hashCode == 1 )
                            {
                                System.out.println(accessUsername+"'s access status is set to public, can access.");
                                canAccess = true;
                                publicPrivate = true;
                            }
                            tempA = tempA.next;
                        }

                        tempA = userAccessList.head;
                        boolean userAccessExists = false;
                        while ( tempA != null && publicPrivate == false )
                        {
                            if ( tempA.userPass.equals(username) && tempA.hashCode == 0 )
                            {
                                System.out.println("Your access condition to this account has been set to false, can not access.");
                                canAccess = false;
                                userAccessExists = true;
                            }
                            if ( tempA.userPass.equals(username) && tempA.hashCode == 1 )
                            {
                                System.out.println("Your access condition to this account has been set to true, can access.");
                                canAccess = true;
                                userAccessExists = true;
                            }

                            tempA = tempA.next;
                        }

                        if ( userAccessExists == false && publicPrivate == false )
                        {
                            System.out.println("You are not on the access list, can not access.");
                            canAccess = false;
                        }
                    }
                    catch (FileNotFoundException e)
                    {
                        System.out.println("username access file cannot be opened...");
                    }

                    if (canAccess == true)
                    {
                        canAccessUser passed = new canAccessUser(canAccess, accessUsername);
                        return passed;
                    }
                }
            }

            System.out.print("Re-ener username to access (0 to go back): ");
            accessUsername = scan.nextLine();
            if ( accessUsername.equals("0") )
            {
                canAccessUser failed = new canAccessUser();
                return failed;
            }
            num++;
        }
        System.out.println("Invalid username entered 5 times, going back.");
        canAccessUser failed = new canAccessUser();
        return failed;
    }

    public static void pageOptions()
    {
        System.out.println("___________________________________________________________________________________");
        System.out.println();
        System.out.println("Phonebook Access Page");
        System.out.println();
        System.out.println("Enter: ");
        System.out.println("0 to go back.");
        System.out.println("1 to view other available users.");
        System.out.println("2 to access other user's phonebook.");
        System.out.println();
    }

    public static canAccessUser contactImportPage(String username, Scanner scan)
    {
        userPassList userList = new userPassList();
        userPassList passList = new userPassList();

        userLoginPageClass.loadUsers(userList, passList);
        System.out.println("other users loaded...");
        
        System.out.println("___________________________________________________________________________________");
        System.out.println();
        System.out.println("Current User: "+username);
        int opt= 1;
        System.out.println();

        while (true)
        {
            pageOptions();

            String optString = scan.nextLine();
            opt = mainClass.choiceConverter(optString);
            int num = 1;

            while ( opt < 0 || opt > 2 )
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
                userAccessProvidePageClass.viewUsersAccessPage(username, userList);
            }
            else if ( opt == 2 )
            {
                canAccessUser canAccess = accessUser(username, userList, scan);

                if ( canAccess.accessSuccess )
                {
                    System.out.println("Access succeeded.");
                    return canAccess;
                }
                else
                {
                    System.out.println("Access failed.");
                }
            }
            else
            {
                //
            }
        }

        canAccessUser noAccess = new canAccessUser();
        return noAccess;
    }
}
