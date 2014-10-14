package unal.jomartinezch.cinemapp.model;

/**
 * Created by Usuario on 11/10/2014.
 */
public class Theater {
    public String name;
    public String tid;
    public String lat;
    public String lon;
    public String desc;
    public String tel;
    public String city;

    public Theater(){}

    @Override
    public String toString() {
        return "\n[name=" + name + ", tid=" + tid + ", lat=" + lat
                + ", lon=" + lon + ", desc=" + desc + ", tel=" + tel
                + ", city=" + city + "]";
    }

}
