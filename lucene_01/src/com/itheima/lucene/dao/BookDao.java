package com.itheima.lucene.dao;


import java.util.List;
import com.itheima.lucene.domain.Book;

public interface BookDao {

	/**
	 * 查询所有的 book 数据
	 */
	 List<Book> findAll();
}
