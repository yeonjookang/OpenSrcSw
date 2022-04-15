package scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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

public class searcher {
	public String path;
	public String query;
	public searcher(String path) {
		this.path=path;
	}
	
	
	
	
	
	
	
	
	
	public ArrayList<Double> InnerProduct(String query) throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
		this.query=query;
		
		ArrayList<String> hashkeylist = new ArrayList<String>();
		ArrayList<String> hashvaluelist = new ArrayList<String>();
		
		FileInputStream fileStream = new FileInputStream(path);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		
		HashMap hashMap = (HashMap)object;
		Iterator<String> it = hashMap.keySet().iterator();
		
		while(it.hasNext()) {
			String hashkey = it.next();
			hashkeylist.add(hashkey);
			hashvaluelist.add((String)hashMap.get(hashkey));
		}
		ArrayList<Double> hashvaluelist2 = new ArrayList<Double>();
		for(int i=0;i<hashvaluelist.size();i++) {
			String[] arr = (hashvaluelist.get(i)).split(" ");
			ArrayList<String> list1 = new ArrayList<String>(Arrays.asList(arr));
			for(int j=0;j<list1.size();j++) {
				hashvaluelist2.add( Double.parseDouble(list1.get(j)));
			}
		}

		ArrayList<String> keywordlist = new ArrayList<String>(); //Keyword arraylist
		ArrayList<Integer> weightlist = new ArrayList<Integer>(); //Keyword¿« weight arraylist
		
		KeywordExtractor ke= new KeywordExtractor();
		KeywordList k1 = ke.extractKeyword(query,true);
		int index=-1;
		for(int i=0;i<k1.size();i++) {
			Keyword kwrd=k1.get(i);
			keywordlist.add(kwrd.getString());
			weightlist.add(kwrd.getCnt());	
		}
		ArrayList<Double> qidList = new ArrayList<Double>();
		ArrayList<Integer> existList = new ArrayList<Integer>();
		
		for(int i=0;i<keywordlist.size();i++) {
			int n=0;
			for(int j=0;j<hashkeylist.size();j++) {
				if(keywordlist.get(i).equals(hashkeylist.get(j))) {
					existList.add(j);
					n=1;
					break;
				}
			}
			if(n==0) {
				existList.add(-1);
			}
		}

		for(int i=0;i<5;i++) {
			double sum=0;
			for(int j=0;j<keywordlist.size();j++) {
				if(existList.get(j).equals(-1)) {
					break;
				}
				else {
					sum+=(weightlist.get(j))*(hashvaluelist2.get(10*existList.get(j)+(2*i+1)));
				}
			}
			qidList.add(sum);
		}
		
		return qidList;
	}
}


	
