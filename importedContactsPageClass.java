import java.util.Scanner;

public class importedContactsPageClass
{
    public static long strtoULL(String input)
    {
        long phoneNo = 0;
        int n = input.length();
        
        int i = 0;
        while ( i <= n-1 )
        {
            if ( (input.charAt(i)-'0' >= 10 ) || (input.charAt(i)-'0' <= -1 ) )
            {
                System.out.println("Indices can only be numeric.");
                return 0;    
            }
            phoneNo *= 10;
            phoneNo += (input.charAt(i)-'0');
            i++;
        }

        return phoneNo;
    }

    public static int searchByName(phoneBook userHost, Scanner scan)
    {
        System.out.println("\nEnter name to search: ");
        String name = "";
        name = scan.nextLine();

        userHost.dictName.autoCompleter(name);

        System.out.println("Choose an index from the following (if no contacts are shown, enter to continue): ");
        int index;
        String indexString = scan.nextLine();

        if (indexString.equals("0"))
        {
            index = 0;
        }
        else
        {
            long temp = strtoULL(indexString);
            index = (int)temp;
            if (index == 0)
            {
                System.out.println("Invalid index entered, exiting.");
                return -1;
            }
        }
        return index;
    }

    public static int searchByNumber(phoneBook userHost, Scanner scan)
    {
        System.out.println("\nEnter number to search: ");
        String tempNo = "";
        tempNo = scan.nextLine();

        int num = 1;
        long phoneNo = strtoULL(tempNo);
        while ( phoneNo == 0 )
        {
            System.out.println("Invalid phone number entered, try again: ");
            tempNo = scan.nextLine();
            phoneNo = strtoULL(tempNo);
            if ( num == 3 )
            {
                System.out.println("Invalid phone number entered 4 times, exiting.");
                return -1;
            }
            num++;
        }

        userHost.dictNum.autoCompleter(tempNo);

        System.out.println("Choose an index from the following (if no contacts are shown, enter to continue): ");
        int index;
        String indexString = scan.nextLine();

        if (indexString.equals("0"))
        {
            index = 0;
        }
        else
        {
            long temp = strtoULL(indexString);
            index = (int)temp;
            if (index == 0)
            {
                System.out.println("Invalid index entered, exiting.");
                return -1;
            }
        }
        return index;
    }

    public static void importContact(phoneBook userHost, phoneBook userAccess, int index)
    {
        contactNode temp = userAccess.head;
        while ( temp != null )
        {
            if ( temp.index == index )
            {
                System.out.println("node found, importing...");
                String name = temp.name;
                long phoneNo = temp.phoneNo;
                String email = temp.email;

                int newIndex = userHost.numberContacts;
                contactNode newNode = new contactNode(newIndex, name, phoneNo, email);
                if ( userHost.tail == null )
                {
                    userHost.head = newNode;
                    userHost.tail = userHost.head;
                }
                else
                {
                    userHost.tail.next = newNode;
                    userHost.tail = newNode;
                }
                userHost.numberContacts++;
                userHost.imports++;

                System.out.println("New contact imported: ");
                userHost.display(newNode);

                String tempNo = phoneBook.ULLtoStr(phoneNo);
                userHost.dictName.insertWord(name, newNode);
                userHost.dictNum.insertWord(tempNo, newNode);

                return;
            }
            temp = temp.next;
        }
        System.out.println("this index does not exist...");
    }

    public static void importContactsAll(phoneBook userHost, phoneBook userAccess)
    {
        System.out.println("importing all contacts...");
        contactNode temp = userAccess.head;

        while ( temp != null )
        {
            String name = temp.name;
            long phoneNo = temp.phoneNo;
            String email = temp.email;

            int newIndex = userHost.numberContacts;

            contactNode newNode = new contactNode(newIndex, name, phoneNo, email);
            if ( userHost.tail == null )
            {
                userHost.head = newNode;
                userHost.tail = userHost.head;
            }
            else
            {
                userHost.tail.next = newNode;
                userHost.tail = newNode;
            }

            userHost.numberContacts++;
            userHost.imports++;

            String tempNo = phoneBook.ULLtoStr(phoneNo);
            userHost.dictName.insertWord(name, newNode);
            userHost.dictNum.insertWord(tempNo, newNode);
            
            temp = temp.next;
        }
        System.out.println("All contacts imported.");
    }

    public static void pageOptions()
    {
        System.out.println("___________________________________________________________________________________");
        System.out.println();
        System.out.println("Contact Import Page");
        System.out.println();
        System.out.println("Enter: ");
        System.out.println("0 to exit import page.");
        System.out.println("1 to search for a contact.");
        System.out.println("2 to display all contacts of this user's phonebook.");
        System.out.println("3 to import a contact to your phonebook.");
        System.out.println("4 to import all contacts from this user's phonebook to your phonebook.");
        System.out.println();
    }

    public static int importedContactsPage(String usernameHost, phoneBook userHost, String usernameAccess, Scanner scan)
    {
        System.out.println("___________________________________________________________________________________");
        System.out.println("Importing contacts from "+usernameAccess+"'s phonebook. ");

        phoneBook userAccess = new phoneBook(usernameAccess, "", scan);
        contactNode headAccess = userAccess.createContactFile();     

        System.out.println(usernameAccess+" currently has "+userAccess.numberContacts+" contact(s) saved.");

        int opt = 1;
        System.out.println();
        while (true)
        {
            pageOptions();

            String optString = scan.nextLine();
            opt = mainClass.choiceConverter(optString);
            int num = 1;

            while (opt < 0 || opt > 4)
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
                System.out.println("\n___________________________________________________________________________________");
                System.out.println("Contact Search:");
                int subOpt = userContactPageClass.pageSubOptions(scan);

                if ( subOpt == 0 )
                {
                    //do nothing
                }
                else if ( subOpt == 1 )
                {
                    System.out.println("Enter index to display: ");
                    
                    int index;
                    String indexString = scan.nextLine();

                    if (indexString.equals("0"))
                    {
                        index = 0;
                        userAccess.displayContact(index);
                    }
                    else
                    {
                        long temp = strtoULL(indexString);
                        index = (int)temp;
                        if (index == 0)
                        {
                            System.out.println("Invalid index entered, exiting.");
                        }
                        else
                        {
                            userAccess.displayContact(index);
                        }
                    }                    
                }
                else if ( subOpt == 2 )
                {
                    int index = importedContactsPageClass.searchByName(userAccess, scan);
                    if ( index != -1 )
                    {
                        userAccess.displayContact(index);
                    }
                }
                else if ( subOpt == 3 )
                {
                    int index = importedContactsPageClass.searchByNumber(userAccess, scan);
                    if ( index != -1 )
                    {
                        userAccess.displayContact(index);
                    }
                }
            }
            else if ( opt == 2 )
            {
                userAccess.displayContactsAll();
            }
            else if ( opt == 3 )
            {
                System.out.println("\n___________________________________________________________________________________");
                System.out.println("Contact Import:");
                int subOpt = userContactPageClass.pageSubOptions(scan);

                if ( subOpt == 0 )
                {
                    //do nothing
                }
                else if ( subOpt == 1 )
                {
                    System.out.println("Enter index to import: ");
                    
                    int index;
                    String indexString = scan.nextLine();

                    if (indexString.equals("0"))
                    {
                        index = 0;
                        importContact(userHost, userAccess, index);
                    }
                    else
                    {
                        long temp = strtoULL(indexString);
                        index = (int)temp;
                        if (index == 0)
                        {
                            System.out.println("Invalid index entered, exiting.");
                        }
                        else
                        {
                            importContact(userHost, userAccess, index);
                        }
                    }                    
                }
                else if ( subOpt == 2 )
                {
                    int index = importedContactsPageClass.searchByName(userAccess, scan);
                    if ( index != -1 )
                    {
                        importContact(userHost, userAccess, index);
                    }
                }
                else if ( subOpt == 3 )
                {
                    int index = importedContactsPageClass.searchByNumber(userAccess, scan);
                    if ( index != -1 )
                    {
                        importContact(userHost, userAccess, index);
                    }
                }
            }
            else if ( opt== 4 )
            {
                importContactsAll(userHost, userAccess);
            }
            else
            {
                //
            }
        
        }
        
        System.out.println("returning to contacts page...");
        return 1;
    }

}
