package com.itheima.lucene.test;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchTypeTest {

	
	/**
	 * 精准查询
	 */
	@Test
	 public void test1() {
		//直接创建一个TermQuery
		//参数说明，1,term对象  分词查询的
		Query query = new TermQuery(new Term("name","java"));
		//执行查询
		try {
			search(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	/**
	 * 根据价格查询
	 * @throws Exception 
	 */
	@Test
	 public void test2() throws Exception {
		 //根据价格查询
		//参数说明
		Query query = NumericRangeQuery.newFloatRange("price", 55f, 70f, false, false);
		
		search(query);
	 }
	
	/**
	 * 组合查询
	 * @throws Exception 
	 */
	@Test
	 public void test3() throws Exception {
		 //1.根据name:java
		   Query query1 = new TermQuery(new Term("description","java"));
		//2.价格在55-70之间的
		   Query query2 = NumericRangeQuery.newFloatRange("price", 55f, 70f, false, false);
		//3.组合
		   BooleanQuery query = new BooleanQuery();
		//设置组合方式
		query.add(query1,Occur.MUST);
		query.add(query2, Occur.MUST);
		System.err.println(query);
		search(query);
	 }
	/**
	 * 多个域查询 MultiFieldQueryParse
	 * or,或  并集
	 * @throws Exception 
	 */
	@SuppressWarnings("resource")
	@Test
	 public void test4() throws Exception {
		//创建IK分词器 
		IKAnalyzer analyzer = new IKAnalyzer();
		//创建多域的组数
		String[] fields = {"name","description"};
		QueryParser parser = new MultiFieldQueryParser(fields,analyzer);
		//创建条件对象
		Query query = parser.parse("java");
		search(query);
	 }
	
	public void search(Query query) throws Exception {
		// 4创建指定索引库的地址，流对象
				Directory directory = FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\lucene__aa"));
				// 5创建读取索引库数据的对象
				IndexReader indexReader = DirectoryReader.open(directory);
				// 6创建搜索对象
				IndexSearcher indexSearcher = new IndexSearcher(indexReader);
				// 7执行查询，返回结果集 1、指定查询的条件；2、指定查询返回的记录数
				TopDocs topDocs = indexSearcher.search(query, 3);
				// 8打印结果集总记录数 totalHits
				System.out.println(topDocs.totalHits);
				// 9结果集需要的是坐标 ，数组形式
				ScoreDoc[] scoreDocs = topDocs.scoreDocs;
				// 10遍历坐标数据
				for (ScoreDoc scoreDoc : scoreDocs) {
					// 12根据文档ID搜索文档，返回文档对象
					int doc = scoreDoc.doc;
					Document document = indexSearcher.doc(doc);
					// 13打印文档数据
					System.err.println("文档name=============" + document.get("name"));
					System.err.println("文档price =============" + document.get("price"));

				}
		
	}
	
	
}
