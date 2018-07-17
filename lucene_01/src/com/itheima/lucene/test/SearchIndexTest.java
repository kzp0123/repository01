package com.itheima.lucene.test;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchIndexTest {

	@Test
	public void test() throws Exception {

		// 1创建分词器对象
		IKAnalyzer analyzer = new IKAnalyzer();
		// 2创建一个搜索解析器对象 两个参数：1、指定默认搜索的域名；2、分词器对象
		QueryParser queryPaser = new QueryParser("description", analyzer);
		// 3创建条件对象，根据解析器对象
		Query query = queryPaser.parse("java");
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
