package scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class indexer {
	
	private String path;

	public indexer(String path) {
		// TODO Auto-generated constructor stub
		this.path=path;
	}
	@SuppressWarnings({"rawtypes","unchecked","nls"})
	public void invertedXml() throws IOException, ParserConfigurationException, SAXException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		FileOutputStream fileStream = new FileOutputStream("./index.post");
		
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		
		HashMap keyMap = new HashMap();
		
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder(); 
		Document Doc = documentBuilder.parse(new File(path));
		
		String[] arr1 = {}; //id를 구분하기 위한 #를 추가한 배열
		
		for(int i=0;i<5;i++) {
			
			NodeList bodys=Doc.getElementsByTagName("body");
			Node body1=bodys.item(i);
			Node bodyvalue=body1.getFirstChild();
			String bodyvalue1=bodyvalue.getNodeValue();
			String[] arr2 = bodyvalue1.split("#|:");
		
			List<String> list1 = new ArrayList(Arrays.asList(arr1));
			List<String> list2 = new ArrayList(Arrays.asList(arr2));
		
			int num=list2.size();
		
			list2.add(num, "#");
		
			list1.addAll(list2);
		
			arr1 = list1.toArray(new String[0]);
		}
		
		
		String[] arr3 = {}; //key 값과 value 값을 분리해내기 위한 #을 추가안한 배열
		
		for(int i=0;i<5;i++) {
			
			NodeList bodys=Doc.getElementsByTagName("body");
			Node body1=bodys.item(i);
			Node bodyvalue=body1.getFirstChild();
			String bodyvalue1=bodyvalue.getNodeValue();
			String[] arr2 = bodyvalue1.split("#");
			
			List<String> list1 = new ArrayList(Arrays.asList(arr3));
			List<String> list2 = new ArrayList(Arrays.asList(arr2));
			
			int num=list2.size();
			
			list1.addAll(list2);
			
			arr3 = list1.toArray(new String[0]);
			}
		
		String str1 = String.join(" ", arr3);
		String[] ArrayStr = str1.split(":| ");
		
		List<String> keylist = new ArrayList<>();
		for(int i=0;i<ArrayStr.length/2;i++){
			keylist.add(ArrayStr[i*2]);
		}
		
		
		String[] keyarr=keylist.toArray(new String[0]);

		//keyarr 는 key 값을 모아둔 배열
		List<Integer> valuelist = new ArrayList<>();
		for(int i=0;i<ArrayStr.length/2;i++){
			valuelist.add(Integer.parseInt(ArrayStr[i*2+1]));
		}
		
		Integer[]  valuearr=valuelist.toArray(new Integer[0]);

		//valuearr 는 value 값을 모아둔 배열
		
		String[] arr4 = {};
		//arr4는 key 값과 # 표시만 한 배열
		for(int i=0;i<5;i++) {
			
			NodeList bodys=Doc.getElementsByTagName("body");
			Node body1=bodys.item(i);
			Node bodyvalue=body1.getFirstChild();
			String bodyvalue1=bodyvalue.getNodeValue();
			String[] arr2 = bodyvalue1.split("#|:");
			String[] arr5= {};
			List<String> list1 = new ArrayList(Arrays.asList(arr4));
			List<String> list3 = new ArrayList(Arrays.asList(arr5));
			
			for(int j=0;j<arr2.length/2;j++) {
				list3.add(arr2[j*2]);
			}
			list3.add("#");
			
			list1.addAll(list3);
			
			arr4 = list1.toArray(new String[0]);
		}
		

		
		
		for(int i=0;i<keyarr.length;i++) {
			String key=keyarr[i];
			String str = "";
			int N=5;
			int id=0;
			List<Integer> idlist=new ArrayList<>();
			for(int j=0;j<arr4.length;j++) {
				if(arr4[j].equals("#"))
					id++;
				if(key.equals(arr4[j])) {
					idlist.add(id);
				}
			}
			idlist.add(10);
			
			int num=0;
			for(int j=0;j<5;j++) {
				if((idlist.get(num)).equals(j)) {
					int df=idlist.size()-1;
					int tf=valuearr[i];
					double w=tf*Math.log(N/df);
					str=str+j+" "+Math.round(w*100)/100.0+" ";
					num++;
					
				}
				else {
					str=str+j+" "+"0"+" ";
				}
			}
			keyMap.put(key,str);
		}
		
		
		objectOutputStream.writeObject(keyMap);
		
		objectOutputStream.close();
		
		
		
	}
	
}
