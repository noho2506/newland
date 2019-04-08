package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import constant.Defines;
import model.bean.About;
import model.bean.CommentLand;
import model.bean.Contact;
import model.bean.District;
import model.bean.Know;
import model.bean.Land;
import model.bean.Users;
import model.dao.AboutDAO;
import model.dao.AdverDAO;
import model.dao.CategoryDAO;
import model.dao.CommentLandDAO;
import model.dao.ContactDAO;
import model.dao.DistrictDAO;
import model.dao.ImgDAO;
import model.dao.KnowDAO;
import model.dao.LandDAO;
import model.dao.NewsDAO;
import model.dao.ProjectDAO;
import util.SlugUtil;

@Controller
public class PublicLandController {
	@Autowired
	private CategoryDAO catDAO;
	@Autowired
	private ImgDAO imgDAO;
	@Autowired 
	private LandDAO landDAO;
	@Autowired 
	private NewsDAO newsDAO;
	@Autowired 
	private KnowDAO knowDAO;
	@Autowired 
	private ProjectDAO projectDAO;
	@Autowired 
	private DistrictDAO districtDAO;
	@Autowired 
	private CommentLandDAO cmtDAO;
	@Autowired
	private Defines defines;
	@Autowired
	private AdverDAO adverDAO;
	@Autowired
	private AboutDAO aboutDAO;
	@Autowired
	private SlugUtil slugUtil;
	@Autowired
	private  ContactDAO contactDAO;
	
	@ModelAttribute
	public void addCommonsObject(ModelMap modelMap) {
		modelMap.addAttribute("defines", defines);
		modelMap.addAttribute("slugUtil", slugUtil);
	}
	@ModelAttribute
	public void commonsObject(ModelMap modelMap,HttpServletRequest request) {
		HttpSession session=request.getSession();
		Users userLogin = (Users)session.getAttribute("userLogin");
		modelMap.addAttribute("userLogin", userLogin);
		modelMap.addAttribute("listCat", catDAO.getItems());
		modelMap.addAttribute("listQuan", districtDAO.getItems());
	}
	@RequestMapping("")
	public String index(ModelMap modelMap){
		modelMap.addAttribute("listLands", landDAO.getItemsLand());
		modelMap.addAttribute("listProject", projectDAO.getItems());
		// lấy khu vực cao nhất 
		modelMap.addAttribute("getItemTop", landDAO.getItemTop());
		modelMap.addAttribute("getLandsTop", landDAO.getItemsTop());
		modelMap.addAttribute("getItemCat", newsDAO.getItems());
		modelMap.addAttribute("getItemKnow", knowDAO.getItems());
		return "public.land.index";
	}
	@RequestMapping(value= {"/cat/{id}/page-{page}","/cat/{id}"}, method=RequestMethod.GET)
	public String cat(@PathVariable("id")int id,ModelMap modelMap,@PathVariable(value="page", required=false) Integer page ){
		if(page == null) {
			page=1;
		}
		int sumPage =(int)Math.ceil((float)landDAO.getCountCat(id)/Defines.ROW_COUNT);
		int offset = (page -1) * Defines.ROW_COUNT;
		System.out.println();
		modelMap.addAttribute("sumPage", sumPage);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("getItemCat", landDAO.getItemsS(id,offset));
		modelMap.addAttribute("cat",catDAO.getItem(id));
		return "public.land.cat";
	}
	@RequestMapping(value= {"/{nameQuan}-{id_district}/page-{page}","/{nameQuan}-{id_district}"}, method=RequestMethod.GET)
	public String catQuan(@PathVariable("id_district")int id_district,ModelMap modelMap,@PathVariable(value="page", required=false) Integer page ){
		if(page == null) {
			page=1;
		}
		int sumPage =(int)Math.ceil((float)landDAO.getCountCatQuan(id_district)/Defines.ROW_COUNT);
		int offset = (page -1) * Defines.ROW_COUNT;
		System.out.println();
		modelMap.addAttribute("sumPage", sumPage);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("getItemCat", landDAO.getItemsCatQuan(id_district,offset));
		modelMap.addAttribute("cat",catDAO.getItem(id_district));
		return "public.land.cat";
	}
	@RequestMapping(value="cats", method=RequestMethod.POST)
	public String cat(ModelMap modelMap,@RequestParam("id_cat")int id_cat,@RequestParam("id_district")int id_district,@RequestParam("dientich")int dientich,@RequestParam("mucgia")int mucgia){
		String sql_id= " ";
		Land land = new Land(0, "", "", "", "", "", "", "", "", 0,"","","",id_cat, "", id_district, " ","");
		sql_id=defines.search(id_cat,id_district,dientich,mucgia);
		modelMap.addAttribute("getSoTin", landDAO.soTinSearch(sql_id));
		System.out.println("tin:"+landDAO.soTinSearch(sql_id));
		modelMap.addAttribute("getItemCat", landDAO.getItemsS(sql_id));
		modelMap.addAttribute("land", land);
		modelMap.addAttribute("mucgia", mucgia);
		modelMap.addAttribute("dientich", dientich);
		return "public.land.cat";
	}
	@RequestMapping("detail/{id}")
	public String detail(@PathVariable("id")int id,ModelMap modelMap){
		Land land= landDAO.getItem(id);
		if (land!=null) {
			modelMap.addAttribute("getItem", landDAO.getItem(id));
			modelMap.addAttribute("ListItemsR", landDAO.getItemsRelate(landDAO.getItem(id).getId_cat()));
		}
		modelMap.addAttribute("listCmt",cmtDAO.getCmt(id));
		modelMap.addAttribute("cmtDAO",cmtDAO);
		return "public.land.detail";
	}
	@RequestMapping(value="reply",method=RequestMethod.POST)
	public String detail(HttpServletResponse response, HttpServletRequest request,ModelMap modelMap,@ModelAttribute("name")String  name,@ModelAttribute("email")String email,@ModelAttribute("cmt")String  cmt,@ModelAttribute("idC")int idC,@ModelAttribute("idL")int id_land){
		/*binhf luận */
		String chuoi="";
		Date date = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyy HH:mm");
		String date_create= fm.format(date);
		CommentLand comment = new CommentLand(0, name, email, cmt, id_land,date_create,idC);
		cmtDAO.addCmtRep(comment);
		comment= cmtDAO.getItem();
		System.out.println(defines.getUrlPublic());
		PrintWriter out;
		ArrayList<CommentLand> cmtRep =(ArrayList<CommentLand>)cmtDAO.getCmtRep(idC);
		modelMap.addAttribute("listCmtRep", cmtRep);
		try {
			out = response.getWriter();
			for (int i = 0; i < cmtRep.size(); i++) {
				System.out.println("vô for");
				chuoi+="<div class=\"single-room-review-area d-flex align-items-center binhluan-cmt \">\r\n" + 
						"                            <div class=\"reviwer-thumbnail bl-img-rep \">\r\n" + 
						"                                <img src='" +defines.getUrlPublic() +"/img/56a0bb98e9a99a4669bf7f07_user_icon.png' alt=\"\">\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"reviwer-content\">\r\n" + 
						"                                <div class=\"reviwer-title-rating d-flex align-items-center justify-content-between\">\r\n" + 
						"                                    <div class=\"reviwer-title\">\r\n" + 
						"                                        <span>"+cmtRep.get(i).getName()+"</span>\r\n" + 
						"                                        <h6>"+ cmtRep.get(i).getCreate_day()+"</h6>\r\n" + 
						"                                    </div>\r\n" + 
						"                                </div>\r\n" + 
						"                                <p>"+cmtRep.get(i).getCmt()+"</p>\r\n" + 
						"                            </div>\r\n" + 
						"                        </div>";
			}
			System.out.println("cjhuo"+chuoi);
			out.println(chuoi);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value="detail",method=RequestMethod.POST)
	public String detail(HttpServletResponse response, HttpServletRequest request,ModelMap modelMap,@ModelAttribute("name")String  name,@ModelAttribute("email")String email,@ModelAttribute("cmt")String  cmt,@ModelAttribute("id")int id_land){
		/*binhf luận */
		Date date = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyy HH:mm");
		String date_create= fm.format(date);
		CommentLand comment = new CommentLand(0, name, email, cmt, id_land,date_create,0);
		cmtDAO.addCmt(comment);
		comment= cmtDAO.getItem();
		String chuoi="";
		ArrayList<CommentLand> cmtLand= (ArrayList<CommentLand>)cmtDAO.getCmt(id_land);
		modelMap.addAttribute("listCmt", cmtLand);
		System.out.println(defines.getUrlPublic());
		PrintWriter out ;
		
		try {
			out = response.getWriter();
			for (int i = 0; i < cmtLand.size(); i++) {
				ArrayList<CommentLand> cmtLandRep= (ArrayList<CommentLand>)cmtDAO.getCmtRep(cmtLand.get(i).getId());
				chuoi+="<div class=\"single-room-review-area d-flex align-items-center\">\r\n" + 
						"                            <div class=\"reviwer-thumbnail bl-img-rep\">\r\n" + 
						"                                <img src='" +defines.getUrlPublic() +"/img/56a0bb98e9a99a4669bf7f07_user_icon.png' alt=\"\">\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"reviwer-content\">\r\n" + 
						"                                <div class=\"reviwer-title-rating d-flex align-items-center justify-content-between\">\r\n" + 
						"                                    <div class=\"reviwer-title\">\r\n" + 
						"                                        <span>"+cmtLand.get(i).getName()+"</span>\r\n" + 
						"                                        <a class='rep' href='javascript:void(0)' onclick='rep_a("+cmtLand.get(i).getId()+")'>Reply</a>" +
						"                                        <h6>"+ cmtLand.get(i).getCreate_day()+"</h6>\r\n" + 
						"                                    </div>\r\n" + 
						"                                </div>\r\n" + 
						"                                <p>"+cmtLand.get(i).getCmt()+"</p>\r\n" + 
						"                            </div>\r\n" + 
						"                        </div>"
						+ "<div class=\"repLand_"+cmtLand.get(i).getId()+"\" style=\"margin-left:80px\">";
					if (cmtLandRep!=null) {
						for (int j = 0; j < cmtLandRep.size(); j++) {
							chuoi+="<div class=\"single-room-review-area d-flex align-items-center binhluan-cmt \">\r\n" + 
									"                            <div class=\"reviwer-thumbnail bl-img-rep \">\r\n" + 
									"                                <img src='" +defines.getUrlPublic() +"/img/56a0bb98e9a99a4669bf7f07_user_icon.png' alt=\"\">\r\n" + 
									"                            </div>\r\n" + 
									"                            <div class=\"reviwer-content\">\r\n" + 
									"                                <div class=\"reviwer-title-rating d-flex align-items-center justify-content-between\">\r\n" + 
									"                                    <div class=\"reviwer-title\">\r\n" + 
									"                                        <span>"+cmtLandRep.get(j).getName()+"</span>\r\n" + 
									"                                        <h6>"+ cmtLandRep.get(j).getCreate_day()+"</h6>\r\n" + 
									"                                    </div>\r\n" + 
									"                                </div>\r\n" + 
									"                                <p>"+cmtLandRep.get(j).getCmt()+"</p>\r\n" + 
									"                            </div>\r\n"
									+ " </div>";
						}
					}
					chuoi+="</div>"
							+ "<div class=\"form_rep"+cmtLand.get(i).getId()+" rep_land_form\" style=\"margin-left:80px\">\r\n" + 
							"                        	<form action=\"#\" method=\"post\">\r\n" + 
							"	                            <div class=\"row contact-form rep-form\">\r\n" + 
							"	                            	<div class=\"col-12 col-lg-6 form-input1 \">\r\n" + 
							"	                            		<lable> Tên *:</lable>\r\n" + 
							"	                                    <input type=\"text\" name=\"message-name\" class=\"form-cmt col-6 ten_rep"+cmtLand.get(i).getId() +"\"  required>\r\n" + 
							"	                                </div>\r\n" + 
							"	                                <div class=\"col-12 col-lg-6 form-input1\" data-wow-delay=\"100ms\">\r\n" + 
							"	                                	Email * : \r\n" + 
							"	                                    <input type=\"email\" name=\"message-email\" class=\"form-cmt col-6 email_rep"+cmtLand.get(i).getId()+"\"  >\r\n" + 
							"	                                </div>\r\n" + 
							"	                            	<div class=\"col-12 wow fadeInUp\" data-wow-delay=\"100ms\">\r\n" + 
							"	                            		<lable> Bình luận </lable>\r\n" + 
							"	                                    <textarea name=\"message\" class=\"form-cmt-tex col-12 cmt_rep"+cmtLand.get(i).getId()+"\" placeholder=\"Your Message\"></textarea>\r\n" + 
							"	                                </div>\r\n" + 
							"	                                <div class=\"col-12 text-center wow fadeInUp\" data-wow-delay=\"100ms\">\r\n" + 
							"	                                    <a href=\"javascript:void(0)\"  class=\"btn roberto-btn mt-15\" onclick='doCommentRep("+cmtLand.get(i).getId()+","+cmtLand.get(i).getId_land()+")'>Phản hồi</a>\r\n" + 
							"	                                </div>\r\n" + 
							"	                            </div>\r\n" + 
							"	                        </form>\r\n" + 
							"                        </div>";
				}
			System.out.println("chuoi"+chuoi);
		out.println(chuoi);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value="thongke", method=RequestMethod.GET)
	public String chart(ModelMap modelMap){
		/*int id_cat=1;
		int year = 2018;
		int quan=0;
		Map<String , Integer> getMap= new LinkedHashMap();
		ArrayList<District> distric = (ArrayList<District>) districtDAO.getItems();
		for (int i = 0; i <distric.size(); i++) {
			quan= i+1;
			int soTin= landDAO.countTin(quan,id_cat,year);
			getMap.put(distric.get(i).getName(),soTin);
		}
		modelMap.addAttribute("getMap", getMap);
		for (String key : getMap.keySet()) {
			System.out.println(key + " " + getMap.get(key));

		}
		System.out.println(getMap.keySet());*/
		/*char chuoi= '100 m';
		/*String str="100 m ";*/
		/*Pattern pattern = Pattern.compile("\\d*");
		String[] chuoi =  str.split("\\s");
		for (String string : chuoi) {
			 Matcher matcher = pattern.matcher(string);
			 if (matcher.matches()) {
		            dt = Integer.parseInt(string);
		            System.out.println(string);
		       }
		}*/
		return "public.land.chart";
	}
	@RequestMapping(value="thongke", method=RequestMethod.POST)
	public String chart(ModelMap modelMap,@RequestParam("cat") int id_cat,@RequestParam("chitieu") int chitieu,@RequestParam("year") int year, RedirectAttributes ra){
		Map<String , Integer> getMap= new LinkedHashMap();
		ArrayList<District> distric = (ArrayList<District>) districtDAO.getItems();
		int quan=0;
		int dt,temp;
		
		// tính theo số tin 
		if ((chitieu==3)&&(year!=0)&&(id_cat!=0)) {
			for (int i = 0; i <distric.size(); i++) {
				quan= i+1;
				int soTin= landDAO.countTin(quan,id_cat,year);
				getMap.put(distric.get(i).getName(),soTin);
			}
		}
		
		// tính theo tổng diện tích
		if ((chitieu==1)&&(year!=0)&&(id_cat!=0)) {
			Pattern pattern = Pattern.compile("\\d*");
			for (int i = 0; i < distric.size(); i++) {
				dt=0;
				quan=i+1;
				List<Land> str = landDAO.getDienTich(id_cat, year, quan);
				for (Land land : str) {
					String[] chuoi =  land.getArea().split("\\s");
					for (String string : chuoi) {
						 Matcher matcher = pattern.matcher(string);
						 temp=0;
						 if (matcher.matches()) {
					            temp = Integer.parseInt(string);
					            dt+=temp;
					       }
					}
				}
				getMap.put(distric.get(i).getName(), dt);
			}
			for (String key : getMap.keySet()) {
				System.out.println(key + " " + getMap.get(key));

			}
			System.out.println("chi tieu :"+chitieu);
			System.out.println(getMap.keySet());
		}

		// tính theo mức giá
		if ((chitieu==2)&&(year!=0)&&(id_cat!=0)) {
			System.out.println("chi tieeu :"+chitieu);
			String price;
			Map<String , String> getPrice= new LinkedHashMap();
			Map<String , Integer> geta=new LinkedHashMap();
			Map<String , Integer> getb= new LinkedHashMap();
			Map<String , Integer> getc= new LinkedHashMap();
			Map<String , Integer> getd= new LinkedHashMap();
			getPrice.put(" 400 AND 900","a");
			getPrice.put(" 1 AND 3" ,"b");
			getPrice.put(" 4 AND 7","c");
			getPrice.put(" >= 8 ","d");
			for (String key : getPrice.keySet()) {
				price = " price BETWEEN  "+key;
				for (int j = 0; j <distric.size(); j++) {
					quan= j+1;
					if (getPrice.get(key).equals("a")) {
						int soTin= landDAO.countTins(quan,id_cat,year,price);
						geta.put(distric.get(j).getName(), quan);
					}
					
					if (getPrice.get(key).equals("b")) {
						int soTin= landDAO.countTins(quan,id_cat,year,price);
						getb.put(distric.get(j).getName(), soTin);
					}
					if (getPrice.get(key).equals("c")) {
						int soTin= landDAO.countTins(quan,id_cat,year,price);
						getc.put(distric.get(j).getName(), soTin);
					}
					if (getPrice.get(key).equals("d")) {
						price = " price "+key;
						int soTin= landDAO.countTins(quan,id_cat,year,price);
						getd.put(distric.get(j).getName(), soTin);
					}
				}
				quan =0;
			}
			modelMap.addAttribute("geta", geta);
			modelMap.addAttribute("getb", getb);
			modelMap.addAttribute("getc", getc);
			modelMap.addAttribute("getd", getd);
		}
		if (chitieu==0||year==0||id_cat==0) {
			ra.addFlashAttribute("msg", "Vui lòng nhập trường dữ liệu ");
			return "redirect:/thongke";
		}
		
		modelMap.addAttribute("getMap", getMap);
		modelMap.addAttribute("chitieu", chitieu);
		modelMap.addAttribute("year", year);
		modelMap.addAttribute("cat",catDAO.getItem(id_cat));
		return "public.land.chart";
	}
	@RequestMapping("/project/{nam-project}-{id}")
	public String project(ModelMap modelMap,@PathVariable("id")int id){
		int id_img= projectDAO.getItem(id).getId_image();
		modelMap.addAttribute("getImg", imgDAO.getIdImg(id_img));
		System.out.println(imgDAO.getIdImg(id_img).getVitri());
		modelMap.addAttribute("getProject", projectDAO.getItem(id));
		return "public.project.detail";
	}
	
	@RequestMapping(value="/project/{nam-project}-{id}",method=RequestMethod.POST)
	@ResponseBody
	  public  String sendText(@ModelAttribute("name")String  name,@ModelAttribute("email")String email,@ModelAttribute("phone")String  phone,@PathVariable("id")int id) throws AddressException, MessagingException {
	    String out="";
	    Contact contact = new Contact(id, name, email, phone, "",id);
	    if (contactDAO.addItemProject(contact)>0) {
			System.out.println("up lienhe");
		}
	    System.out.println("up lienhe1");
		Properties mailServerProperties;
	    Session getMailSession;
	    MimeMessage mailMessage;
	    // Step1: setup Mail Server
	    mailServerProperties = System.getProperties();
	    mailServerProperties.put("mail.smtp.port", "587");
	    mailServerProperties.put("mail.smtp.auth", "true");
	    mailServerProperties.put("mail.smtp.starttls.enable", "true");
	 
	    // Step2: get Mail Session
	    getMailSession = Session.getDefaultInstance(mailServerProperties, null);
	    mailMessage = new MimeMessage(getMailSession);
	 
	    mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));; //Thay abc bằng địa chỉ người nhận
	    mailMessage.setSubject("Demo send gmail from Java");
	    mailMessage.setText("Demo send text by gmail from Java");
	 
	    // Step3: Send mail
	    Transport transport = getMailSession.getTransport("smtp");
	 
	    // Thay your_gmail thành gmail của bạn, thay your_password thành mật khẩu gmail của bạn
	    transport.connect("smtp.gmail.com", "tranhoa24052405@gmail.com", "97duongny"); 
	    transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
	    transport.close();
	    out = "<form action=\"javascript:void()\" class=\"form-connect\">\r\n" + 
	    		"		                                <input name=\"name\" type=\"text\" class=\"form-control form-control-lienhe\" placeholder=\"Your name...\" required >\r\n" + 
	    		"		                                <input name=\"phone\" type=\"text\" class=\"form-control form-control-lienhe\" placeholder=\"Your phone...\" required >\r\n" + 
	    		"		                                <input name=\"email\" type=\"email\" class=\"form-control form-control-lienhe\" placeholder=\"Enter your email...\" required>\r\n" + 
	    		"		                                <button type=\"submit\" onclick=\"demo()\"><i class=\"fa fa-paper-plane\" aria-hidden=\"true\"></i></button>\r\n" + 
	    		"		                            </form>";
	    return out;
	  }
	@RequestMapping("/projects")
	public String project(ModelMap modelMap){
		modelMap.addAttribute("listProjectVip", projectDAO.getItemsVip());
		modelMap.addAttribute("listProject", projectDAO.getItems());
		return "public.project.index";
	}
	@RequestMapping("/news")
	public String news(ModelMap modelMap){
		modelMap.addAttribute("getItemCat", newsDAO.getItems());
		modelMap.addAttribute("getTopViewLand", landDAO.getTopView());
		modelMap.addAttribute("getItemNewsView", newsDAO.getItemView());
		modelMap.addAttribute("getTopViewProject", projectDAO.getTopViewProject());
		return "public.news.index";
	}
	@RequestMapping("/news/{name_news}-{id}")
	public String news(ModelMap modelMap,@PathVariable("id")int id){
		int view = newsDAO.getView(id);
		view+=1;
		if (newsDAO.getViewUpdate(view,id)>0) {
			System.out.println("tăng view");
		}
		modelMap.addAttribute("getItemNews", newsDAO.getItem(id));
		modelMap.addAttribute("getItemCat", newsDAO.getItems());
		modelMap.addAttribute("getItemNewsView", newsDAO.getItemView());
		return "public.news.detail";
	}
	@RequestMapping("/kien-thuc")
	public String know(ModelMap modelMap){
		About about = aboutDAO.getItem();
		
		modelMap.addAttribute("getItemKnow", knowDAO.getItemsCat());
		modelMap.addAttribute("getTopViewKnow", knowDAO.getItemViewKnow());
		modelMap.addAttribute("getAdv", adverDAO.getItemAdv());
		modelMap.addAttribute("getAdv", adverDAO.getItemAdv());
		modelMap.addAttribute("about", about);
		return "public.know.index";
	}
	
	@RequestMapping(value= "/kien-thuc", method=RequestMethod.POST)
	public String know(ModelMap modelMap,@ModelAttribute("form")int form, HttpServletResponse response, HttpServletRequest request){
		modelMap.addAttribute("listProject", projectDAO.getItems());
		About Oldabout = aboutDAO.getItem();
		int temp;
		String sql="";
		if (1==form) {
			temp= Oldabout.getForm1() +1;
			sql= "form1 = "+temp+" WHERE id = "+Oldabout.getId();
			aboutDAO.updateItem(sql);
		}
		if (2==form) {
			System.out.println("ca");
			temp= Oldabout.getForm2() +1;
			sql= "form2 = "+temp+" WHERE id = "+Oldabout.getId();
			aboutDAO.updateItem(sql);		
				}
		if (3==form) {
			temp= Oldabout.getForm3() +1;
			sql= "form3 = "+temp+" WHERE id = "+Oldabout.getId();
			aboutDAO.updateItem(sql);
		}
		if (4==form) {
			temp= Oldabout.getForm4() +1;
			sql= "form4 = "+temp+" WHERE id = "+Oldabout.getId();
			aboutDAO.updateItem(sql);
		}
		if (5==form) {
			temp= Oldabout.getForm5() +1;
			sql= "form5 = "+temp+" WHERE id = "+Oldabout.getId();
			aboutDAO.updateItem(sql);
		}
		Oldabout = aboutDAO.getItem();
		Map<String , Integer> hinhthuc= new LinkedHashMap();
		About about = aboutDAO.getItem();
		hinhthuc.put("Google.com.vn, bing.com, ...", Oldabout.getForm1());
		hinhthuc.put("Bạn bè giới thiệu ", Oldabout.getForm2());
		hinhthuc.put("Báo chí, truyền thông", Oldabout.getForm3());
		hinhthuc.put("Mạng xã hội", Oldabout.getForm4());
		hinhthuc.put("Khác", Oldabout.getForm5());
		String chuoi="";
		try {
			int i=1;
			PrintWriter out = response.getWriter();
			for (String key : hinhthuc.keySet()) {
				chuoi= chuoi + " <div>"
						+ "<input type='radio' name='form' value='" + i+"' id='myRadio' >" + 
						"	<div class='name-khaosat'>" +key+ " </div>" + 
						"	<div class='meter'>" + 
						"		<span style='width:"+ Defines.percent(hinhthuc.get(key))+"%'></span>" + 
						"		 <p></p>" + 
						"	</div><SPAN class='numberofvote'>"+hinhthuc.get(key) +"phiếu</SPAN>"
						+ "</div>";
				i++;

			}
			out.println(chuoi);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/kien-thuc/{name-know}-{id}")
	public String know(ModelMap modelMap,@PathVariable("id")int id){
		Know know = knowDAO.getItem(id);
		int view = know.getView();
		view+=1;
		if (knowDAO.getViewUpdate(view,id)>0) {
			System.out.println("tăng view");
		}
		modelMap.addAttribute("know", know);
		modelMap.addAttribute("getItemCat", knowDAO.getItemsCat());
		modelMap.addAttribute("getItemKnowView", knowDAO.getItemView());
		return "public.know.detail";
	}
}
