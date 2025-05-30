import java.util.Date;

public class Plant {
    private String name;
    private String lightNeed;
    private int waterNeed;
    private Date addedDate;

    public Plant(String name, String lightNeed, int waterNeed) {
        this.name = name;
        this.lightNeed = lightNeed;
        this.waterNeed = waterNeed;
        this.addedDate = new Date();
    }

    public String getName() { return name; }
    public String getLightNeed() { return lightNeed; }
    public int getWaterNeed() { return waterNeed; }
    public Date getAddedDate() { return addedDate; }

    public void setWaterNeed(int waterNeed) { this.waterNeed = waterNeed; }
}