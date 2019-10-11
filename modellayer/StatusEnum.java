package modellayer;

public enum StatusEnum
{
    CANCELLED("Annulleret"), COMPLETED("Afsluttet"), ENROUTE("På vej"), DELIVERED("Afleveret"), PAID("Betalt");
    
    private String value;

    private StatusEnum(String value)
    {
        this.value = value;
    }
    
    public String toString()
    {
        return value;
    }
}
