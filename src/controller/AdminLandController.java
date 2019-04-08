package controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import constant.Defines;
import model.bean.Land;
import model.dao.CategoryDAO;
import model.dao.DistrictDAO;
import model.dao.LandDAO;
import model.dao.SellerDAO;
import util.FileUtil;

@Controller
@RequestMapping("admin")
public class AdminLandController {
	@Autowired
	private LandDAO landDAO;
	@Autowired
	private CategoryDAO catDAO;
	@Autowired 
	private DistrictDAO districtDAO;
	@Autowired
	private Defines defines;
	@Autowired
	private SellerDAO sellerDAO;
	@ModelAttribute
	public void addCommonsObject(ModelMap modelMap) {
		modelMap.addAttribute("defines", defines);
	}
	@RequestMapping(value="/lands", method= RequestMethod.GET)
	public String index(ModelMap modleMap){
		modleMap.addAttribute("listLands", landDAO.getItems());
		return "admin.land.index";
	}
	
	@RequestMapping(value="/land/del/{id}", method=RequestMethod.GET)
	public String del(@PathVariable("id") int id, RedirectAttributes ra, HttpServletRequest request) {
		Land land = landDAO.getItem(id);
		if(land != null) {
			String fileName = land.getImage();
			if(!"".equals(fileName)) {
				String appPath = request.getServletContext().getRealPath("");
				String dirPath = appPath + Defines.DIR_UPLOAD;
				File file = new File(dirPath + File.separator + fileName);
				file.delete();
			}
			
			if(landDAO.delItem(id) > 0) {
				ra.addFlashAttribute("msg", Defines.SUCCESS);
			}else {
				ra.addFlashAttribute("msg", Defines.ERROR);
			}
		}
		
		return "redirect:/admin/lands";
	}
	@RequestMapping(value="/land/show/{id}", method=RequestMethod.GET)
	public String show(ModelMap modelMap,@PathVariable("id") int id, RedirectAttributes ra, HttpServletRequest request) {
		Land land = landDAO.getItem(id);
		
		modelMap.addAttribute("land", land);
		modelMap.addAttribute("seller", sellerDAO.getItem(land.getId_contact()));
		return "admin.land.show";
	}
	@RequestMapping(value="/land/edit/{id}", method=RequestMethod.GET)
	public String edit(@PathVariable("id") int id, ModelMap modelMap, RedirectAttributes ra) {
		Land land = landDAO.getItem(id);
		if(land != null) {
			System.out.println(land.getTitle());
			modelMap.addAttribute("objLand", land);
			modelMap.addAttribute("listCat", catDAO.getItems());
		}else {
			ra.addFlashAttribute("msg", Defines.ERROR);
			return "redirect:/admin/lands";
		}
		return "admin.land.edit";
	}
	@RequestMapping(value="/land/edit/{id}", method=RequestMethod.POST)
	public String edit(@PathVariable("id") int id, @ModelAttribute("land") Land land,ModelMap modelMap, RedirectAttributes ra) {
		land.setId(id);
		if(landDAO.editItem(land) > 0) {
			ra.addFlashAttribute("msg", Defines.SUCCESS);
		}else {
			ra.addFlashAttribute("msg", Defines.ERROR);
		}
		return "redirect:/admin/lands";
	}
	@RequestMapping(value="/land/add", method=RequestMethod.GET)
	public String add(ModelMap modelMap, RedirectAttributes ra) {
		modelMap.addAttribute("listCat", catDAO.getItems());
		modelMap.addAttribute("listQuan", districtDAO.getItems());
		return "admin.land.add";
	}
	@RequestMapping(value="/land/add", method=RequestMethod.POST)
	public String add( @ModelAttribute("land") Land land, @RequestParam("hinhanh") CommonsMultipartFile cmf, HttpServletRequest request, RedirectAttributes ra, ModelMap modelMap) {
		Date date = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String create_day= fm.format(date);
		land.setCreate_day(create_day);
		String fileName = cmf.getOriginalFilename();
		if(landDAO.getCheckItem(land.getTitle()) > 0) {
			modelMap.addAttribute("objLand", land);
			ra.addFlashAttribute("msg1", "Trùng tên dự án! Vui lòng nhập lại!");
			return "redirect:/admin/land/add"; 
		}
		if(!"".equals(fileName)) {
			//có upload
			fileName = FileUtil.getFileReName(fileName);
			String appPath = request.getServletContext().getRealPath("");
			String dirPath = appPath + Defines.DIR_UPLOAD;
			System.out.println("dir:"+dirPath);
			File createDir = new File(dirPath);
			if(!createDir.exists()) {
				createDir.mkdirs();
			}
			
			String filePath = dirPath + File.separator + fileName;
			System.out.println(filePath);
			try {
				cmf.transferTo(new File(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			land.setImage(fileName);
		}
		if(sellerDAO.addItemContact(land) > 0) {
			land.setId_contact(sellerDAO.getItemId().getId());
			if(landDAO.addItem(land) > 0) {
				ra.addFlashAttribute("msg", Defines.SUCCESS);
			}else {
				ra.addFlashAttribute("msg", Defines.ERROR);
			}
		}else {
			ra.addFlashAttribute("msg", Defines.ERROR);
		}
		return "redirect:/admin/lands";
	}
}
