package model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import model.bean.Know;
import model.bean.News;

@Repository
public class KnowDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public List<Know> getItems() {
		String sql="SELECT id,title, description,image,time,detail,view FROM knowledge "
				+ " ORDER BY id DESC LIMIT 4";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Know>(Know.class));
	}
	public List<Know> getItemsCat() {
		String sql="SELECT id,title, description,image,time,detail,view FROM knowledge "
				+ " ORDER BY id DESC LIMIT 6";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Know>(Know.class));
	}
	public List<Know> getItemsKnow() {
		String sql="SELECT id,title, description,image,time,detail,view FROM knowledge ";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Know>(Know.class));
	}
	public Know getItem(int id) {
		try {
			String sql = "SELECT id,title, description,image,time,detail,view FROM knowledge WHERE id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper<Know>(Know.class));
		} catch (Exception e) {
			return null;
		}
	}
	public int delItem(int id) {
		String sql = "DELETE FROM knowledge WHERE id = ?";
		return jdbcTemplate.update(sql, new Object[] {id});
	}
	public int delItem(String temp) {
		String sql = "DELETE FROM knowledge "+ temp;
		return jdbcTemplate.update(sql);
	}
	public int editItem(Know Know) {
		String sql = "UPDATE knowledge SET title=?, description=?, image =?, detail =? WHERE id=?";
		return jdbcTemplate.update(sql, new Object[] {Know.getTitle(),Know.getDescription(),Know.getImage(),Know.getDetail(),Know.getId()});
	}
	public List<Know> getItemViewKnow() {
		String sql="SELECT id,title, description,image,time,detail,view FROM knowledge "
				+ " ORDER BY view DESC LIMIT 6";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Know>(Know.class));
	}
	public int getViewUpdate(int view, int id) {
		String sql = "UPDATE knowledge SET view = ? WHERE id= ? ";
		return jdbcTemplate.update(sql, new Object[] {view,id});
	}
	public List<Know> getItemView() {
		String sql="SELECT id,title, description,image,time,detail,view FROM knowledge "
				+ " ORDER BY view DESC LIMIT 6";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Know>(Know.class));
	}
	
	

}
