package constant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.bean.About;
import model.bean.CommentLand;

public class Defines {
	// định nghĩa các thông số cho ứng dụng web
	private String urlPublic;
	private String urlAdmin;
	public static final String SUCCESS = "Xử lý thành công!";
	public static final String ERROR = "Có lỗi xảy ra trong quá trình xử lý!";
	
	public static final int ROW_COUNT = 5;
	public static final int ROW_COUNT_CAT = 6;
	
	public static final String DIR_UPLOAD = "files";
	
	public String getUrlPublic() {
		return urlPublic;
	}
	public void setUrlPublic(String urlPublic) {
		this.urlPublic = urlPublic;
	}
	public String getUrlAdmin() {
		return urlAdmin;
	}
	public void setUrlAdmin(String urlAdmin) {
		this.urlAdmin = urlAdmin;
	}
	public String dientich(int dientich) {
		String temp=null;
		if(dientich==1) {
			temp = "  area BETWEEN 30 AND 90 ";
		}
		if(dientich==2) {
			temp = " area BETWEEN 100 AND 250 ";					
			}
		if(dientich==3) {
			temp = "  area BETWEEN 250 AND 500 ";
		}
		if(dientich==4) {
			temp = "  area BETWEEN 500 AND 1000 ";
		}
		if(dientich==5) {
			temp = " area >= 1000 ";
		}
		return temp;
	}

	public String mucGia(int mucgia) {
		String temp=null;
		if(mucgia==1) {
			temp = "  price BETWEEN 400 AND 900";
		}
		if(mucgia==2) {
			temp = " price BETWEEN 1 AND 3 ";					
			}
		if(mucgia==3) {
			temp = "  price BETWEEN 4 AND 7 ";
		}
		if(mucgia==4) {
			temp = "  price >=8 && price LIKE '%Tỷ%' ";
		}
		return temp;
	}
	public String search(int id_cat , int id_district , int dientich, int mucgia) {
		String sql_id=null;
		String tempDienTich = "";
		String tempMucGia = "";
		//if có id cat
			//them dk id cat --> sql_id  = sql_id + "AND id_cat="+id_cat
		
		// if co id quan
			// them dk id quan --> sql_id  = sql_id + "AND id_district="+id_district
		
		// if co dien tich
			// them dk dien tich
		
		// if co muc gia
			// them dk muc gia
		
		if ((id_cat!=0)) {
			sql_id = " id_cat = "+id_cat;
			if (id_district!=0) {
				sql_id = " id_district = "+id_district+" && id_cat = "+id_cat;
				if(dientich !=0) {
					tempDienTich =dientich(dientich);
					sql_id = " id_district = "+id_district+" && id_cat = "+id_cat + " && " + tempDienTich ; 
					if(mucgia!=0) {
						// chọn cả 4
						tempMucGia = mucGia(mucgia);
						sql_id = " id_district = "+id_district+" && id_cat = "+id_cat + " && " + tempMucGia  + " && "+tempDienTich; 
					}
				}else {
					if(mucgia!=0) {
						tempMucGia = mucGia(mucgia);
						sql_id = " id_district = "+id_district+" && id_cat = "+id_cat + " && " + tempMucGia ; 
					}
				}
			}else {
				if(dientich !=0) {
					tempDienTich =dientich(dientich);
					sql_id = " id_cat = "+id_cat + " && " + tempDienTich ; 
					if(mucgia!=0) {
						// chọn cả 4
						tempMucGia = mucGia(mucgia);
						sql_id = "  id_cat = "+id_cat + " && " + tempMucGia  + " && "+tempDienTich; 
					}
				}else {
					if(mucgia!=0) {
						tempMucGia = mucGia(mucgia);
						sql_id = "  id_cat = "+id_cat + " && " + tempMucGia ; 
					}
				}
			}
		}else {
			if (id_district!=0) {
				sql_id = " id_district = "+id_district;
				if(dientich !=0) {
					tempDienTich =dientich(dientich);
					sql_id = " id_district = "+id_district+ " && " + tempDienTich ; 
					if(mucgia!=0) {
						// chọn cả 4
						tempMucGia = mucGia(mucgia);
						sql_id = " id_district = "+id_district+" && " + tempMucGia  + " && "+tempDienTich; 
					}
				}else {
					if(mucgia!=0) {
						tempMucGia = mucGia(mucgia);
						sql_id = " id_district = "+id_district + " && " + tempMucGia ; 
					}
				}
			}else {
				if(dientich !=0) {
					tempDienTich =dientich(dientich);
					sql_id = tempDienTich ; 
					if(mucgia!=0) {
						// chọn cả 4
						tempMucGia =mucGia(mucgia);
						sql_id = tempMucGia  + " && "+tempDienTich; 
					}
				}else {
					if(mucgia!=0) {
						tempMucGia = mucGia(mucgia);
						sql_id =  tempMucGia ; 
					}
				}
			}
		}
		return sql_id;
	}
	public static int percent(int chon) {
		int a= (int)Math.ceil((float)chon/1000*100);
		return a;
	}
	/*public List<CommentLand> hamRep(ArrayList<CommentLand> listL){
		System.out.println("a");
		ArrayList<CommentLand> listRep= new ArrayList<CommentLand>();
		if (listL==null) {
			System.out.println("lỗi");
		}else {
			for (CommentLand commentLand : listL) {
				for (CommentLand comment : listL) {
					System.out.println("c"+comment.getSub_id());
					System.out.println(commentLand.getId());
					if (commentLand.getId()==comment.getSub_id()) {
						System.out.println(comment.getId());
						listRep.add(comment);
					}
				}
			}
		}
		for (CommentLand commentLand : listRep) {
			System.out.println("a:"+commentLand.getSub_id()+","+commentLand.getId());
		}
		System.out.println("dai"+listRep.size());
		return listRep;
	}*/
}
