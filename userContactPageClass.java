import java.util.Scanner;

class canAccessUser
{
    public boolean accessSuccess;
    public String accessUsername;

    canAccessUser()
    {
        accessSuccess = false;
        accessUsername = "";
    }
    canAccessUser(boolean aS, String aU)
    {
        accessSuccess = aS;
        accessUsername = aU;
    }
}

class userContactPageClass
{
    public static int commitChangesPage(phoneBook userHost, Scanner scan)
    {
        System.out.println("___________________________________________________________________________________\n");
        System.out.println("You have made the following changes: ");
        System.out.println("Number of contacts added: "+ userHost.addition);
        System.out.println("Number of contacts modified: "+ userHost.modification);
        System.out.println("Number of contacts deleted: "+ userHost.deletion);
        System.out.println("Number of contacts imported: "+ userHost.imports +"\n");

        System.out.println("Commit the following phonebook changes? (1 = yes; 0 = no) ");
        int opt = 1;
        int num = 1;
        String optString = scan.nextLine();
        opt = mainClass.choiceConverter(optString);

        while (opt < 0 || opt > 1)
        {
            System.out.println("Input mistmatch, try again.");
            if (num == 3)
            {
                System.out.println("Input mismatch 3 times, saving and exiting.");
                opt = 1;
                break;
            }

            optString = scan.nextLine();
            opt = mainClass.choiceConverter(optString);
            num++;
        }

        return opt;
    }

    public static void pageOptions()
    {
        System.out.println("___________________________________________________________________________________");
        System.out.println();
        System.out.println("Contact and Phonebook Management Page");
        System.out.println();
        System.out.println("Enter: ");
        System.out.println("0 to log out.");
        System.out.println("1 to display all contacts.");
        System.out.println("2 to search for a contact.");
        System.out.println("3 to add a new contact.");
        System.out.println("4 to modify a contact.");
        System.out.println("5 to delete a contact.");
        System.out.println("6 to delete all contacts.");
        System.out.println("7 to find out the number of contacts saved.");
        System.out.println("8 to modify access settings of your phonebook.");
        System.out.println("9 to import contacts from other users.");
        System.out.println();
    }

    public static int pageSubOptions(Scanner scan)
    {
        System.out.println("\nEnter: ");
        System.out.println("0 to go back.");
        System.out.println("1 to search by index.");
        System.out.println("2 to search by name.");
        System.out.println("3 to search by phone number.");
        System.out.println();

        int subOpt = 0;
        String subOptString = scan.nextLine();
        subOpt = mainClass.choiceConverter(subOptString);
        int subNum = 1;


        while (subOpt < 0 || subOpt > 3)
        {
            System.out.println("Input mistmatch, try again.");
            if (subNum == 5)
            {
                System.out.println("Inocrrect option chosen 5 times, exiting.");
                subOpt = 0;
                break;
            }

            subOptString = scan.nextLine();
            subOpt = mainClass.choiceConverter(subOptString);
            subNum++;
        }

        return subOpt;
    }

    public static int userContactPage(String username, String password, Scanner scan)
    {
        System.out.println("___________________________________________________________________________________\n");
        System.out.println("Welcome "+username);

        phoneBook userHost = new phoneBook(username, password, scan);
        contactNode head = userHost.createContactFile();     

        System.out.println("You currently have "+userHost.numberContacts+" contacts saved.");

        int opt = 1;

        while ( opt != 0 )
        {
            pageOptions();
        
            String optString = scan.nextLine();
            opt = mainClass.choiceConverter(optString);
            int num = 1;
            while (opt < 0 || opt > 9)
            {
                System.out.println("Input mistmatch, try again");
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
                userHost.displayContactsAll();
            }
            else if ( opt == 2 )
            {
                System.out.println("\n___________________________________________________________________________________");
                System.out.println("Contact Search Page");
                System.out.println();

                int subOpt = pageSubOptions(scan);

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
                        userHost.displayContact(index);
                    }
                    else
                    {
                        long temp = importedContactsPageClass.strtoULL(indexString);
                        index = (int)temp;
                        if (index == 0)
                        {
                            System.out.println("Invalid index entered, exiting.");
                        }
                        else
                        {
                            userHost.displayContact(index);
                        }
                    }                    
                }
                else if ( subOpt == 2 )
                {
                    int index = importedContactsPageClass.searchByName(userHost, scan);
                    if ( index != -1 )
                    {
                        userHost.displayContact(index);
                    }
                }
                else if ( subOpt == 3 )
                {
                    int index = importedContactsPageClass.searchByNumber(userHost, scan);
                    if ( index != -1 )
                    {
                        userHost.displayContact(index);
                    }
                }
            }
            else if ( opt == 3 )
            {
                System.out.println("\n___________________________________________________________________________________");
                System.out.println("Contact Addition Page");
                System.out.println();

                userHost.addContact();
            }
            else if ( opt == 4 )
            {
                System.out.println("\n___________________________________________________________________________________");
                System.out.println("Contact Modification Page");
                System.out.println();

                int subOpt = pageSubOptions(scan);
                
                if ( subOpt == 0 )
                {
                    //do nothing
                }
                else if ( subOpt == 1 )
                {
                    System.out.println("Enter index to modify: ");
                    int index;
                    String indexString = scan.nextLine();

                    if (indexString.equals("0"))
                    {
                        index = 0;
                        userHost.displayContact(index);
                    }
                    else
                    {
                        long temp = importedContactsPageClass.strtoULL(indexString);
                        index = (int)temp;
                        if (index == 0)
                        {
                            System.out.println("Invalid index entered, exiting.");
                        }
                        else
                        {
                            userHost.displayContact(index);
                        }
                    }                    
                }
                else if ( subOpt == 2 )
                {
                    int index = importedContactsPageClass.searchByName(userHost, scan);
                    if ( index != -1 )
                    {
                        userHost.modifyContact(index);
                    }
                }
                else if ( subOpt == 3 )
                {
                    int index = importedContactsPageClass.searchByNumber(userHost, scan);
                    if ( index != -1 )
                    {
                        userHost.modifyContact(index);
                    }
                }
            }
            else if (opt == 5 )
            {
                System.out.println("\n___________________________________________________________________________________");
                System.out.println("Contact Deletion Page");
                System.out.println();

                int subOpt = pageSubOptions(scan);

                if ( subOpt == 0 )
                {
                    //do nothing
                }
                else if ( subOpt == 1 )
                {
                    System.out.println("Enter index to delete: ");
                    int index;
                    String indexString = scan.nextLine();

                    if (indexString.equals("0"))
                    {
                        index = 0;
                        userHost.displayContact(index);
                    }
                    else
                    {
                        long temp = importedContactsPageClass.strtoULL(indexString);
                        index = (int)temp;
                        if (index == 0)
                        {
                            System.out.println("Invalid index entered, exiting.");
                        }
                        else
                        {
                            userHost.displayContact(index);
                        }
                    }                    
                }
                else if ( subOpt == 2 )
                {
                    int index = importedContactsPageClass.searchByName(userHost, scan);
                    if ( index != -1 )
                    {
                        userHost.deleteContact(index);
                    }
                }
                else if ( subOpt == 3 )
                {
                    int index = importedContactsPageClass.searchByNumber(userHost, scan);
                    if ( index != -1 )
                    {
                        userHost.deleteContact(index);
                    }
                }
            }
            else if ( opt == 6 )
            {
                System.out.println("\nEnter password to confirm deletion of all contacts: ");
                String input;
                input = scan.nextLine();

                if ( input.equals(password))
                {
                    userHost.deleteContactsAll();
                    System.out.println("Credentials verified, contacts cleared");
                }
                else
                {
                    System.out.println("Credentials mismatched, failed to delete all contacts");
                }
            }
            else if ( opt == 7 )
            {
                System.out.println(userHost.numberContacts+" contacts currently saved.");
            }
            else if ( opt == 8 )
            {
                opt = userAccessProvidePageClass.userAccessProvidePage(username, scan);
            }
            else if ( opt == 9 )
            {
                canAccessUser cAU = contactImportPageClass.contactImportPage(username, scan);
                if ( cAU.accessSuccess )
                {
                    System.out.println("opening "+cAU.accessUsername+"'s contact list...");
                    opt = importedContactsPageClass.importedContactsPage(username, userHost, cAU.accessUsername, scan);
                }
                else
                {
                    System.out.println("access failed, staying on this page...");
                }
            }
            else
            {
                //
            }

        }
        System.out.println("presave...");
        opt = commitChangesPage(userHost, scan);

        if ( opt != 0 )
        {
            userHost.saveContactFile();
            System.out.println("saved...");
        }
        else
        {
            System.out.println("changes not saved...");
        }

        System.out.println("___________________________________________________________________________________\n");
        return 0;
    }

}
