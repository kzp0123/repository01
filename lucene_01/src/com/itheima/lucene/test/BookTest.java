package com.itheima.lucene.test;


import java.util.List;

import org.junit.Test;

import com.itheima.lucene.dao.BookDao;
import com.itheima.lucene.dao.impl.BookDaoImpl;
import com.itheima.lucene.domain.Book;

public class BookTest {

	@Test
	public void test() {
		BookDao bookDao = new BookDaoImpl();
		
		List<Book> list = bookDao.findAll();
		
		for (Book book : list) {
			System.out.println(book);
		}
	}
}
