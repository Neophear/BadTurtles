package modellayer;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Status
{
    private Date changedDate;
    private StatusEnum statusEnum;
    private SimpleDateFormat dateFormat;
    
    public Status(StatusEnum statusEnum)
    {
        this.changedDate = Calendar.getInstance().getTime();
        this.statusEnum = statusEnum;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    public Date getChangedDate() { return changedDate; }
    public StatusEnum getStatusEnum() { return statusEnum; }
    
    public String toString()
    {
        return dateFormat.format(getChangedDate()) + " - " + getStatusEnum().toString();
    }
}