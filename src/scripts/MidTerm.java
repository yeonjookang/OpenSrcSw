package scripts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MidTerm {
	private String path;
	
	public MidTerm(String path) {
		this.path=path;
	}
	
	public void  showSnippet(String query) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder(); 
		Document Doc = documentBuilder.parse(new File(path));
		
		KeywordExtractor ke=new KeywordExtractor();
		KeywordList k1=ke.extractKeyword(query, true);
		
		ArrayList<String> keywordList = new ArrayList<String>();
		
		for(int i=0;i<k1.size();i++) {
			Keyword kwrd=k1.get(i);
			keywordList.add(kwrd.getString());
		}
		ArrayList<String> bodyList = new ArrayList<String>();
		
		for(int i=0;i<5;i++) {
			NodeList bodys=Doc.getElementsByTagName("body");
			Node body1=bodys.item(i);
			Node bodyvalue=body1.getFirstChild();
			String bodyvalue1=bodyvalue.getNodeValue();
			bodyList.add(bodyvalue1);
		}
		ArrayList<String> thirtyList0 = new ArrayList<String>();
		ArrayList<Integer> Matching0 = new ArrayList<Integer>();
		ArrayList<String> thirtyList1 = new ArrayList<String>();
		ArrayList<Integer> Matching1 = new ArrayList<Integer>();
		ArrayList<String> thirtyList2 = new ArrayList<String>();
		ArrayList<Integer> Matching2 = new ArrayList<Integer>();
		ArrayList<String> thirtyList3 = new ArrayList<String>();
		ArrayList<Integer> Matching3 = new ArrayList<Integer>();
		ArrayList<String> thirtyList4 = new ArrayList<String>();
		ArrayList<Integer> Matching4 = new ArrayList<Integer>();
		
		for(int i=0;i<bodyList.get(0).length()-30;i++) {
			thirtyList0.add(bodyList.get(0).substring(i, i+30));
		}
		for(int i=0;i<bodyList.get(1).length()-30;i++) {
			thirtyList1.add(bodyList.get(1).substring(i, i+30));
		}
		for(int i=0;i<bodyList.get(2).length()-30;i++) {
			thirtyList2.add(bodyList.get(2).substring(i, i+30));
		}
		for(int i=0;i<bodyList.get(3).length()-30;i++) {
			thirtyList3.add(bodyList.get(3).substring(i, i+30));
		}
		for(int i=0;i<bodyList.get(4).length()-30;i++) {
			thirtyList4.add(bodyList.get(4).substring(i, i+30));
		}
		int point=0;
		for(int j=0;j<keywordList.size();j++) {
			for(int i=0;i<thirtyList0.size()-keywordList.get(j).length();i++) {
				String s=thirtyList0.get(i).substring(i, i+(keywordList.get(j).length()));
				if(s.equals(keywordList.get(j))){
					point++;
				}
			}
		}
		Matching0.add(point);
		
		point=0;
		for(int j=0;j<keywordList.size();j++) {
			for(int i=0;i<thirtyList1.size();i++) {
				String s=thirtyList1.get(i).substring(i, i+(keywordList.get(j).length()));
				if(s.equals(keywordList.get(j))){
					point++;
				}
			}
		}
		Matching1.add(point);
		
		point=0;
		for(int j=0;j<keywordList.size();j++) {
			for(int i=0;i<thirtyList2.size();i++) {
				String s=thirtyList2.get(i).substring(i, i+(keywordList.get(j).length()));
				if(s.equals(keywordList.get(j))){
					point++;
				}
			}
		}
		Matching2.add(point);
		
		point=0;
		for(int j=0;j<keywordList.size();j++) {
			for(int i=0;i<thirtyList3.size();i++) {
				String s=thirtyList3.get(i).substring(i, i+(keywordList.get(j).length()));
				if(s.equals(keywordList.get(j))){
					point++;
				}
			}
		}
		Matching3.add(point);
		
		point=0;
		for(int j=0;j<keywordList.size();j++) {
			for(int i=0;i<thirtyList4.size();i++) {
				String s=thirtyList4.get(i).substring(i, i+(keywordList.get(j).length()));
				if(s.equals(keywordList.get(j))){
					point++;
				}
			}
		}
		Matching4.add(point);
		
		int best0=Matching0.get(0);
		
		for(int i=1;i<Matching0.size();i++) {
			if(best0<Matching0.get(i)) {
				best0=Matching0.get(i);
			}
		}
		
		int best1=Matching1.get(0);
		
		for(int i=1;i<Matching1.size();i++) {
			if(best1<Matching1.get(i)) {
				best1=Matching1.get(i);
			}
		}
		
		int best2=Matching0.get(0);
		
		for(int i=1;i<Matching2.size();i++) {
			if(best2<Matching2.get(i)) {
				best2=Matching2.get(i);
			}
		}
		int best3=Matching3.get(0);
		
		for(int i=1;i<Matching3.size();i++) {
			if(best3<Matching3.get(i)) {
				best3=Matching3.get(i);
			}
		}
		int best4=Matching4.get(0);

		for(int i=1;i<Matching4.size();i++) {
			if(best4<Matching4.get(i)) {
				best4=Matching4.get(i);
			}
		}
		
		int best=best0;
		
		if(best<best1) {
			best=best1;
		}
		
		if(best<best2) {
			best=best1;
		}
		
		if(best<best3) {
			best=best1;
		}
		
		if(best<best4) {
			best=best1;
		}
		
		System.out.println(best);
		
	}
}
