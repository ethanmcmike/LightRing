package ethanmcmike.lightring.models;

public class AppModel {

    public String title;
    public String description;
    public int iconRes;
    public Class activity;

    public AppModel(String title, Class activity){
        this.title = title;
        this.activity = activity;
        description = "";
        iconRes = 0;
    }
}
