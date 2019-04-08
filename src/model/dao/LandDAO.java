package model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import constant.Defines;
import model.bean.Adver;
import model.bean.Land;

@Repository
public class LandDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public List<Land> getItems() {
		String sql="SELECT l.id,title,description,price,image,create_day,area,location,detail,id_contact,id_cat,id_district FROM lands AS l "
				+ " INNER JOIN category AS c ON l.id_cat = c.id "
				+ " ORDER BY l.id DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Land>(Land.class));
	}
	public List<Land> getItemsLand() {
		String sql="SELECT l.id,title,description,price,image,create_day,area,location,detail,id_contact,id_cat,id_district FROM lands AS l " + 
				" INNER JOIN category AS c ON l.id_cat = c.id "
				+ " ORDER BY id DESC LIMIT 6";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Land>(Land.class));
	}
	public Land getItem(int id) {
		try {
			String sql = "SELECT l.id,title,description,price,image,create_day,area,location,detail,id_contact,id_cat,c.name AS name_cat,id_district FROM lands AS l "
					+ "INNER JOIN category AS c ON l.id_cat = c.id "
					/*+ "INNER JOIN district AS d ON d.id = id_district"*/
					+ " WHERE l.id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<Land>(Land.class));
		} catch (Exception e) {
			return null;
		}
	}
	public int delItem(int id) {
		String sql = "DELETE FROM lands WHERE id = ?";
		return jdbcTemplate.update(sql, new Object[] {id});
	}
	public int editItem(Land land) {
		String sql = "UPDATE lands SET title=?, description=?, id_cat=?, area=?, location=?  WHERE id=?";
		return jdbcTemplate.update(sql, new Object[] {land.getTitle(),land.getDescription(),land.getId_cat(),land.getArea(),land.getLocation(),land.getId()});
	}
	public int countTin(int quan, int id_cat, int year) {
		String sql="SELECT count(*) AS sotin FROM `lands` WHERE id_district=? && id_cat=? && Year(create_day)=?";
		return jdbcTemplate.queryForObject(sql, new Object[] {quan,id_cat,year}, Integer.class);
	}
	public List<Land> getItemsRelate(int id_cat) {
		String sql="SELECT l.id,title,description,price,image,create_day,area,location,detail,id_contact,id_cat,c.name AS name_cat,id_district FROM lands AS l "
				+ "INNER JOIN category AS c ON l.id_cat = c.id "
				+ "INNER JOIN district AS d ON d.id = id_district"
				+ " WHERE id_cat= ?"
				+ "  LIMIT 4";
		return jdbcTemplate.query(sql, new Object[] {id_cat},new BeanPropertyRowMapper<Land>(Land.class));
	}
	public List<Land> getItemsS(String  sql_id_district) {
		String sql="SELECT l.id,title,description,price,image,create_day,area,location,detail,id_contact,id_cat,c.name AS name_cat,id_district FROM lands AS l "
				+ "INNER JOIN category AS c ON l.id_cat = c.id "
				+ "INNER JOIN district AS d ON d.id = id_district"
				+ " WHERE "+sql_id_district
				+ " ORDER BY l.id DESC LIMIT 5";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Land>(Land.class));
	}
	public List<Land> getDienTich(int id_cat, int year, int quan) {
		// TODO Auto-generated method stub
		String sql="SELECT area  FROM `lands` WHERE id_district=? && id_cat=? && Year(create_day)=?";
		return jdbcTemplate.query(sql, new Object[] {quan,id_cat,year},  new BeanPropertyRowMapper<Land>(Land.class));
	}
	public int countTins(int quan, int id_cat, int year, String price) {
		String sql="SELECT count(*) AS sotin FROM `lands` WHERE " + price +" && id_district= "+quan+" && id_cat= "+id_cat+" && Year(create_day)= "+year;
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	public int soTinSearch(String sql_id) {
		String sql="SELECT count(*) AS sotin FROM `lands` WHERE " + sql_id;
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	public List<Land> getItemsS(int id, int offset) {
		String sql="SELECT l.id,title,description,price,image,create_day,area,location,detail,id_contact,id_cat,c.name AS name_cat,id_district FROM lands AS l "
				+ "  INNER JOIN category AS c ON l.id_cat = c.id "
				+ "INNER JOIN district AS d ON d.id = id_district"
				+ " WHERE id_cat= ? "
				+ " ORDER BY l.id DESC LIMIT ?,? ";
		return jdbcTemplate.query(sql, new Object[] {id,offset,Defines.ROW_COUNT},new BeanPropertyRowMapper<Land>(Land.class));
	}
	public int getCountCat(int id) {
		String sql="SELECT count(*) AS sotin FROM `lands` WHERE id_cat = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {id}, Integer.class);
	}
	public Land getItemTop() {
		try {
			String sql="SELECT count(*),d.name AS name_district,id_district,image FROM lands AS l INNER JOIN category AS c ON l.id_cat = c.id INNER JOIN district AS d ON d.id = id_district  GROUP BY id_district ORDER BY l.id DESC LIMIT 1";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Land>(Land.class));
		} catch (Exception e) {
			return null;
		}
	}
	public List<Land> getItemsTop() {
		String sql="SELECT count(*),d.name AS name_district ,id_district,image FROM lands AS l INNER JOIN category AS c ON l.id_cat = c.id INNER JOIN district AS d ON d.id = id_district  GROUP BY id_district ORDER BY l.id DESC LIMIT 1,2";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Land>(Land.class));
	}
	public int getCountCatQuan(int id_district) {
		String sql="SELECT count(*) AS sotin FROM `lands` WHERE id_district = "+id_district; 
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	public List<Land> getItemsCatQuan(int id_district, int offset) {
		String sql="SELECT l.id,title,description,price,image,create_day,area,location,detail,id_contact,id_cat,c.name AS name_cat,id_district FROM lands AS l "
				+ "  INNER JOIN category AS c ON l.id_cat = c.id "
				+ "INNER JOIN district AS d ON d.id = id_district"
				+ " WHERE id_district= ? "
				+ " ORDER BY l.id DESC LIMIT ?,? ";
		return jdbcTemplate.query(sql, new Object[] {id_district,offset,Defines.ROW_COUNT},new BeanPropertyRowMapper<Land>(Land.class));
	}
	public List<Land> getTopView() {
		String sql="SELECT l.id,title,description,price,image,create_day,area,location,detail,id_contact,id_cat,c.name AS name_cat,id_district FROM lands AS l "
				+ "  INNER JOIN category AS c ON l.id_cat = c.id "
				+ "INNER JOIN district AS d ON d.id = id_district"
				+ " ORDER BY view DESC LIMIT 6 ";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Land>(Land.class));
	}
	public int getCheckItem(String name) {
		String sql = "SELECT COUNT(*) AS countItem FROM lands  WHERE title = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {name}, Integer.class);
	}
	public int addItem(Land land) {
		String sql = "INSERT INTO lands(title,description,price,image,area,create_day,location,detail,id_contact,id_cat,id_district) VALUES(?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] {land.getTitle(),land.getDescription(),land.getPrice(),land.getImage(),land.getArea(),land.getCreate_day(),land.getLocation(),land.getDetail(),land.getId_contact(),land.getId_cat(),land.getId_district()});
	}

}
