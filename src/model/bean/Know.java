package model.bean;

public class Know {
	private int id;
	private String title;
	private String description;
	private String image;
	private String time;
	private String  detail;
	private int view;
	
	
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	public Know(int id, String title, String description, String image, String time, String detail, int view) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.image = image;
		this.time = time;
		this.detail = detail;
		this.view = view;
	}
	public Know() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
