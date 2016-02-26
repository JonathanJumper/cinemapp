package model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class Theater {
	
	public String name;
	public @Id String tid;
	public String lat;
	public String lon;
	public String desc;
	public String tel;
	public String city;
	public String url;
	
	public Theater(){}
	
	public Theater(String name, String tid, String lat, String lon,
			String desc, String tel, String city, String url) {
		super();
		this.name = name;
		this.tid = tid;
		this.lat = lat;
		this.lon = lon;
		this.desc = desc;
		this.tel = tel;
		this.city = city;
		this.url = url;
	}

	@Override
	public String toString() {
		return "\n[name=" + name + ", tid=" + tid + ", lat=" + lat
				+ ", lon=" + lon + ", desc=" + desc + ", tel=" + tel
				+ ", city=" + city + ", url=" + url + "]";
	}
	
}
