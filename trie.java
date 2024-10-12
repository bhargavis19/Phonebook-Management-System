class trieNode
{
    public char data;
    public trieNode[] children;
    public int numChildren;
    public boolean isTerminal;
    public contactNode[] belongsTo;

    trieNode(char c, int n)
    {
        data = c;
        numChildren = n;
        children = new trieNode[numChildren];

        int i = 0;
        while ( i <= numChildren-1 )
        {
            children[i] = null;
            i++;
        }
        isTerminal = false;
        belongsTo = new contactNode[10];

        i = 0;
        while ( i <= 9 )
        {
            belongsTo[i] = null;
            i++;
        }
    }
}

public class trie
{
    trieNode root;
    int numChildren;

    trie(int n)
    {
        numChildren = n;
        if ( numChildren == 256 )
        {
            //System.out.println("ascii trie created...");
        }
        else if ( numChildren == 10 )
        {
            //System.out.println("number trie created...");
        }
        else
        {
            System.out.println("something wrong happened...");
        }

        root = new trieNode('\0', numChildren);
    }

    public void insertWord(trieNode root, String word, contactNode node)
    {
        if ( word.length() == 0 )
        {
            root.isTerminal = true;
            int i = 0;
            while ( root.belongsTo[i] != null )
            {
                i++;
            }

            root.belongsTo[i] = node;
            return;
        }

        trieNode child;
        int index = word.charAt(0);
        if ( numChildren == 10 )
        {
            index -= '0';
        }
        
        if ( root.children[index] != null )
        {
            child = root.children[index];
        }
        else
        {
            child = new trieNode(word.charAt(0), numChildren);
            root.children[index] = child;
        }

        insertWord(child, word.substring(1), node);
        //work on this after other features are done
    }

    public void insertWord(String word, contactNode node)
    {
        insertWord(root, word, node);
    }

    public void removeWord(trieNode root, String word, contactNode node)
    {
        if ( word.length() == 0 )
        {
            int i = 0;

            while ( i <= 9 )
            {
                if ( root.belongsTo[i] == node )
                {
                    root.belongsTo[i] = null;
                }
                i++;
            }

            i = 0;
            while ( i <= 9 )
            {
                if ( root.belongsTo[i] != null )
                {
                    return;
                }
                i++;
            }
            
            root.isTerminal = false;
            return;
        }

        trieNode child;
        int index = word.charAt(0);
        if ( numChildren == 10 )
        {
            index -= '0';
        }
        if ( root.children[index] != null )
        {
            child = root.children[index];
        }
        else
        {
            System.out.println("word not matching, can't remove...");
            return;
        }

        removeWord(child, word.substring(1), node);

        if ( child.isTerminal == false )
        {
            int i = 0;
            while ( i <= numChildren-1 )
            {
                if ( child.children[i] != null )
                {
                    return;
                }
                i++;
            }
            child = null;
            root.children[index] = null;
        }
    }

    public void removeWord(String word, contactNode node)
    {
        removeWord(root, word, node);
    }

    public void allPrinter(trieNode root)
    {
        if ( root == null )
        {
            return;
        }

        if ( root.isTerminal )
        {
            int i = 0;

            while ( i <= 9 )
            {
                contactNode node = root.belongsTo[i];
                if ( node == null )
                {
                    i++;
                    continue;
                }

                System.out.println("Index: "+node.index);
                System.out.println("Name: "+node.name);
                System.out.println("Number: "+node.phoneNo);
                
                String email = node.email;
                if ( !email.equals("~") )
                {
                    System.out.println("Email: "+node.email);
                }
                System.out.println("___________________________________________________________________________________");
                
                i++;
            }
        }

        int i = 0;
        while ( i <= numChildren-1 )
        {
            trieNode child = root.children[i];
            if ( child != null )
            {
                //char c = child.data;
                allPrinter(child);
            }
            i++;
        }
        return;
    }

    public void autoCompleter(trieNode root, String input)
    {
        if ( input.length() == 0 )
        {
            allPrinter(root);
            return;
        }
        else
        {
            int index = input.charAt(0);
            if ( numChildren == 10 )
            {
                index -= '0';
            }
            trieNode child = root.children[index];

            if ( child != null )
            {
                autoCompleter(child, input.substring(1));
            }
            return;
        }
    }

    public void autoCompleter(String input)
    {
        autoCompleter(root, input);
    }
}