package Project;

import java.time.LocalDate;

/**
 * Created by Ben on 03.03.2017.
 */
public class Milestones {
    private Integer ID;
    private String MilestoneName;
    private String MilestoneInfo;
    private LocalDate DateOfMilestone;
    private Integer ProjectID;
    //Construtor

    public Milestones()
    {};

    public Milestones(Integer _ID, String _Name, String _Info, LocalDate _Date  )
    {
        ID = _ID;
        MilestoneName = _Name;
        MilestoneInfo = _Info;
        DateOfMilestone = _Date;
    }



    // Getter
    public Integer getMilestoneID() {
        return this.ID;
    }
    public String getMilestoneInfo() {
        return this.MilestoneInfo;
    }
    public String getMilestoneName() {
        return this.MilestoneName;
    }
    public LocalDate getMilestoneDate() {
        return this.DateOfMilestone;
    }
    // Setter
    public boolean setMilestoneName(String _MilestoneName) {
        MilestoneName = _MilestoneName;
        return true;
    }
    public boolean setMilestoneInfo(String _MilestoneInfo) {
        MilestoneInfo = _MilestoneInfo;
        return true;
    }
    public boolean setDateOfMilestone(LocalDate _DateOfMilestones) {
        DateOfMilestone = _DateOfMilestones;
        return true;
    }
    public boolean setMilestoneID(Integer _MilestoneID)
    {
        ID =_MilestoneID;

        return true;
    }
    public boolean setMilestoneProjectID(Integer _MilestoneProjectID){this.ProjectID =_MilestoneProjectID; return true;}
}

