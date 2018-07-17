package com.itheima.lucene.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.itheima.lucene.dao.BookDao;
import com.itheima.lucene.dao.impl.BookDaoImpl;
import com.itheima.lucene.domain.Book;

public class CreateIndexTest {

	@Test
	public void test() throws Exception {

		// 采集数据
		BookDao bookDao = new BookDaoImpl();
		List<Book> list = bookDao.findAll();
		// 创建文档集合
		List<Document> documentList = new ArrayList<>();
		for (Book book : list) {
			// 创建文档对象
			Document document = new Document();
			// 创建Field对象，存储数据
          Field idField = new StringField("id",book.getId()+"",Store.YES);
          Field nameField = new TextField("name",book.getName()+"",Store.YES);
          Field priceField = new FloatField("price",book.getPrice(),Store.YES);
          Field picField = new StoredField("pic",book.getPic()+"");
          Field descriptionField = new TextField("description",book.getDescription()+"",Store.NO);
			// 把Field添加到文档中
          document.add(idField);
          document.add(nameField);
          document.add(priceField);
          document.add(picField);
          document.add(descriptionField);
			// 把文档加到文档集合中
          documentList.add(document);
		}
		// 创建分词器对象
		IKAnalyzer analyzer =  new IKAnalyzer();
		// 创建指定的索引库地址 流对象Directory
		Directory directory = FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\lucene__aa"));
		// 创建写入索引库的配置文件对象
		IndexWriterConfig indexWriterconfig = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
		// 创建写入索引库的对象
		IndexWriter indexWriter = new IndexWriter(directory,indexWriterconfig);
		// 写入 把文档集合一个文档一个文档写入索引库
           for (Document document : documentList) {
        	   indexWriter.addDocument(document);
		}
		// 关闭流
           indexWriter.close();
	}
}
