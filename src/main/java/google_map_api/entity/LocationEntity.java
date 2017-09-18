package google_map_api.entity;

public class LocationEntity {
	private String latitude;
	private String longitude;
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public LocationEntity() {
		
	}
	public LocationEntity(String lat, String lon) {
		latitude = lat;
		longitude = lon;
	}
}
