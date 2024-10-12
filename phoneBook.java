import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class phoneBook
{
    public static long strtoULL(String input)
    {
        long phoneNo = 0;
        int n = input.length();
        
        if ( n <= 6 )
        {
            System.out.println("Invalid phone number. This phonebook only accepts 7-15 digit phone numbers.");
            return 0;
        }

        int i = 0;
        while ( i <= n-1 )
        {
            if ( i >= 15 )
            {
                System.out.println("Invalid phone number. This phonebook only accepts 7-15 digit phone numbers.");

                return 0;
            }
            if ( (input.charAt(i)-'0' >= 10 ) || (input.charAt(i)-'0' <= -1 ) )
            {
                System.out.println("Invalid phone number. This phonebook only accepts numerical digits in the phone number field.");
                return 0;    
            }
            phoneNo *= 10;
            phoneNo += (input.charAt(i)-'0');
            i++;
        }

        return phoneNo;
    }
    
    public static String ULLtoStr(long num)
    {
        String ans = "";
        while ( num > 0 )
        {
            long digit = num%10;
            digit += '0';
            char c = (char)digit;

            ans = c + ans;
            num /= 10;            
        }

        return ans;
    }

    public static String convertToLowercase(String input)
    {
        String output = input.toLowerCase();
        return output;
    }

    public String folderName;
    public String contactFile;
    public String username;
    public String password;           

    public int numberContacts;
    public contactNode head;
    public contactNode tail;

    public int modification;
    public int addition;
    public int deletion;
    public int imports;     

    trie dictName;
    trie dictNum;

    Scanner scan;

    phoneBook(String username, String password, Scanner scan)
    {
        head = null;
        tail = null;
        this.username = username;
        this.password = password;
        folderName = "./contactInfo/";
        contactFile = folderName+username+"Contacts.txt";
        
        numberContacts = 0;
        modification = 0;
        addition = 0;
        deletion = 0;
        imports = 0;

        dictName = new trie(256);                         //names can contain any of the possible letter so I took 256 for all possible ASCII values
        dictNum = new trie(10);                           //numbers can contain 0-9

        this.scan = scan;
    }

    public contactNode purgeLL(contactNode head)
    {
        if ( head == null )
        {
            return null;
        }
        else
        {
            head.next = purgeLL(head.next);
            return null;
        }
    }

    public void display(contactNode temp)
    {
        int index = temp.index;
        String name = temp.name;
        long phoneNo = temp.phoneNo;
        String email = temp.email;

        System.out.println("___________________________________________________________________________________");

        System.out.println("Index: "+index);
        System.out.println("Name: "+name);
        System.out.println("Phone No.: "+phoneNo);


        if ( !email.equals("~") ) 
        {

            System.out.println("Email: "+email);
        }

        System.out.println("___________________________________________________________________________________");
    }

    public contactNode createContactFile()
    {
        contactNode head = null;
        contactNode tail = null;
        int index = 0;
        try
        {
            File contactFileRead = new File(contactFile);
            Scanner scanFile = new Scanner(contactFileRead);
            // System.out.println("test1");
            while (scanFile.hasNext())
            {
                String name = "";
                long phoneNo = 0;
                String email = "";

                // System.out.println("test2");
                name = scanFile.nextLine();
                //System.out.println(name);
                
                String tempNo;
                tempNo = scanFile.nextLine();
                phoneNo = strtoULL(tempNo);
                // phoneNo = scanFile.nextLong();
                //System.out.println(phoneNo);

                // System.out.println("test3");
                email = scanFile.next();
                //System.out.println(email);

                
                if ( phoneNo != 0 )
                {
                    contactNode newNode = new contactNode(index, name, phoneNo, email);
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

                    dictName.insertWord(name, newNode);
                    dictNum.insertWord(tempNo, newNode);
                    index++;
                }
                else
                {
                    System.out.println("unchanged input or phoneNo entered as 0...");
                }
                scanFile.nextLine();
            }
            this.head = head;
            this.tail = tail;
            numberContacts = index;
            scanFile.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("contacts.txt unable to be opened...");
        }
        return head;
    }
    
    public void saveContactFile()
    {
        try
        {
            FileWriter contactFileWrite = new FileWriter(contactFile);
            contactFileWrite.write("");
            contactFileWrite.flush();

            contactNode temp = head;
            while ( temp != null )
            {
                String name = temp.name;
                long phoneNo = temp.phoneNo;
                String email = temp.email;

                contactFileWrite.write(name+System.lineSeparator());
                contactFileWrite.write(phoneNo+System.lineSeparator());
                contactFileWrite.write(email+System.lineSeparator());

                temp = temp.next;
            }
            contactFileWrite.close();
        }
        catch (IOException e)
        {
            System.out.println("contacts.txt unable to be opened...");
        }
    }

    public void displayContactsAll()
    {
        if ( numberContacts == 0 )
        {
            System.out.println("\nNo contacts to display.");
            return;
        }
        System.out.println("Printing all contacts.");
        System.out.println("___________________________________________________________________________________\n");

        contactNode temp = head;
        while ( temp != null )
        {
            display(temp);
            temp = temp.next;
        }
    }

    public void deleteContactsAll()
    {
        head = purgeLL(head);
        tail = head;

        deletion += numberContacts;
        numberContacts = 0;
        System.out.println("All contacts deleted.");
    }

    public void indexReassign()
    {
        int i = 0;
        contactNode temp = head;
        while ( temp != null )
        {
            temp.index = i;
            i++;
            temp = temp.next;
        }
        System.out.println("Indices reassigned.");
    }

    public void addContact()
    {
        System.out.println();
        System.out.println("Enter new contact details (0 to go back).");
        String name = "";
        String tempNo = "";
        long phoneNo = 0;
        String email = "";

        
        System.out.print("Enter name (compulsory): ");
        name = scan.nextLine();
        if ( name.equals("0") )
        {
            return;
        }
        
        System.out.print("Enter phone number (compulsory): ");
        tempNo = scan.nextLine();
        if ( tempNo.equals("0") )
        {
            return;
        }
        phoneNo = strtoULL(tempNo);

        int num = 1;
        while ( phoneNo == 0 )
        {
            System.out.print("Enter phone number (compulsory): ");
            tempNo = scan.nextLine();
            if ( tempNo.equals("0") || num == 3)
            {
                if ( num == 3 )
                {
                    System.out.println("Invalid phone number entered 4 times, exiting.");
                }
                return;
            }
            phoneNo = strtoULL(tempNo);
            num++;
        }

        System.out.print("Enter email (~ to skip): ");
        email = scan.nextLine();
        if ( email.equals("0") )
        {
            return;
        }
        email = convertToLowercase(email);

        int newIndex = numberContacts;
        contactNode newNode = new contactNode(newIndex, name, phoneNo, email);
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
        numberContacts++;
        addition++;

        dictName.insertWord(name, newNode);
        dictNum.insertWord(tempNo, newNode);

        System.out.println("New contact created: ");
        display(newNode);
    }

    public void deleteContact(int index)
    {
        contactNode temp = head;
        if ( temp.index == index )
        {
            System.out.println("head deletion in progress...");
            contactNode next = temp.next;

            String tempNo = ULLtoStr(temp.phoneNo);
            dictName.removeWord(temp.name, temp);
            dictNum.removeWord(tempNo, temp);

            head = next;

            numberContacts--;
            deletion++;

            indexReassign();
            return;
        }
        while ( temp != null )
        {
            contactNode next = temp.next;
            if ( next != null && next.index == index )
            {
                System.out.println("node found, deletion in progress...");

                contactNode nextNext = next.next;
                temp.next = nextNext;
                if ( nextNext == null )
                {
                    tail = temp;
                }
                else
                {
                    indexReassign();
                }

                numberContacts--;
                deletion++;

                String tempNo = ULLtoStr(temp.phoneNo);
                dictName.removeWord(next.name, next);
                dictNum.removeWord(tempNo, next);
                break;
            }
            else if ( next == null )
            {
                System.out.println("Node not found.");
            }

            temp = temp.next;
        }
    }

    public void displayContact(int index)
    {
        contactNode temp = head;
        while ( temp != null )
        {
            if ( temp.index == index )
            {
                System.out.println("node found, displaying information...");
                display(temp);
                return;
            }
            temp = temp.next;
        }
        System.out.println("This index does not exist.");
    }

    public void modifyContact(int index)
    {
        System.out.println();
        contactNode temp = head;
        while ( temp != null )
        {
            // System.out.println(temp.index);
            if ( temp.index == index )
            {
                System.out.println("Current information: ");
                display(temp);

                System.out.println("Enter new information (~ to use previous information): ");
                String name = "";
                String tempNo = "";
                long phoneNo = 0;
                String email = "";

                boolean modified = false;

                System.out.print("Enter name (compulsory): ");
                
                name = scan.nextLine();
                if ( name.equals("~") )
                {
                    name = temp.name;
                }
                else
                {
                    modified = true;
                }
        
                System.out.print("Enter phone number (compulsory): ");
                tempNo = scan.nextLine();
                if ( tempNo.equals("~") )
                {
                    phoneNo = temp.phoneNo;
                }
                else
                {
                    phoneNo = strtoULL(tempNo);
                    int num = 1;

                    while ( phoneNo == 0 )
                    {
                        System.out.println("Invalid phone number entered, try again: ");
                        tempNo = scan.nextLine();
                        if ( tempNo.equals("~") || num == 3)
                        {
                            phoneNo = temp.phoneNo;
                            if ( num == 3 )
                            {
                                System.out.println("Invalid phone number entered 4 times, using previous details.");
                            }
                            break; 
                        }
                        phoneNo = strtoULL(tempNo);
                        num++;
                    }
                    if ( phoneNo != temp.phoneNo )
                    {
                        modified = true;
                    }
                }

                System.out.print("Enter email (~ to skip): ");
                email = scan.nextLine();
                if ( email.equals("~") )
                {
                    email = temp.email;
                }
                else
                {
                    email = convertToLowercase(email);
                    modified = true;
                }

                String tempNoRemove = ULLtoStr(temp.phoneNo);
                dictName.removeWord(temp.name, temp);
                dictNum.removeWord(tempNoRemove, temp);

                temp.name = name;
                temp.phoneNo = phoneNo;
                temp.email = email;
                tempNo = phoneBook.ULLtoStr(phoneNo);
                
                dictName.insertWord(name, temp);
                dictNum.insertWord(tempNo, temp);

                System.out.println("Updated details: ");
                display(temp);

                if (modified)
                {
                    modification++;
                }

                return;
            }

            temp = temp.next;
        }

        System.out.println("Contact with index not found.");
    }
}
