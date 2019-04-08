package model.bean;

public class ImageProject {
	private int id;
	private String trangchu;
	private String gioithieu;
	private String tienich;
	private String vitri;
	private String thietke;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTrangchu() {
		return trangchu;
	}
	public void setTrangchu(String trangchu) {
		this.trangchu = trangchu;
	}
	public String getGioithieu() {
		return gioithieu;
	}
	public void setGioithieu(String gioithieu) {
		this.gioithieu = gioithieu;
	}
	public String getTienich() {
		return tienich;
	}
	public void setTienich(String tienich) {
		this.tienich = tienich;
	}
	public String getVitri() {
		return vitri;
	}
	public void setVitri(String vitri) {
		this.vitri = vitri;
	}
	public String getThietke() {
		return thietke;
	}
	public void setThietke(String thietke) {
		this.thietke = thietke;
	}
	public ImageProject(int id, String trangchu, String gioithieu, String tienich, String vitri, String thietke) {
		super();
		this.id = id;
		this.trangchu = trangchu;
		this.gioithieu = gioithieu;
		this.tienich = tienich;
		this.vitri = vitri;
		this.thietke = thietke;
	}
	public ImageProject() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
