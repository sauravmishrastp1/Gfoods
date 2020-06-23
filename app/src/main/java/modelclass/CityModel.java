package modelclass;

public class CityModel {
	private String cityname;
	private String id;

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CityModel(String cityname, String id) {
		this.cityname = cityname;
		this.id = id;
	}
}
