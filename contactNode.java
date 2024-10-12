public class contactNode 
{
    public int index;
    public String name;
    public long phoneNo;
    public String email;
    public contactNode next;
    
    contactNode(int index, String name, long phoneNo, String email)
    {
        this.index = index;
        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;

        next = null;
    }
}
