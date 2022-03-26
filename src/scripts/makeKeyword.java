package scripts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;

import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class makeKeyword {

	private String path;
	
	public makeKeyword(String path) {
		// TODO Auto-generated constructor stub
		this.path=path;
	}

	public void convertXml() throws ParserConfigurationException, TransformerException, SAXException, IOException {
		// TODO Auto-generated method stub
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder(); 
		Document Doc = documentBuilder.parse(new File("./root/collection.xml"));
				
		
		DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc= docBuilder.newDocument();
		
		Element docs= doc.createElement("docs");
		doc.appendChild(docs);
		
		for(int i=0 ; i<5;i++) {
			
			
			Element doc2 = doc.createElement("doc");
			docs.appendChild(doc2);
					
			doc2.setAttribute("id",String.valueOf(i));
					
			NodeList titles=Doc.getElementsByTagName("title");
			Node title1=titles.item(i);
			Node titlevalue=title1.getFirstChild();
			
			String titleData = titlevalue.getNodeValue();
			
			Element title= doc.createElement("title");
			title.appendChild((doc).createTextNode(titleData));
			doc2.appendChild(title);
				
			NodeList bodys=Doc.getElementsByTagName("body");
			Node body1=bodys.item(i);
			Node bodyvalue=body1.getFirstChild();
			
			Element body =  doc.createElement("body");
			
			String bodyData=bodyvalue.toString();
			
			KeywordExtractor ke= new KeywordExtractor();
			KeywordList k1 = ke.extractKeyword(bodyData,true);
			for(int j=0;j<k1.size();j++) {
				Keyword kwrd=k1.get(j);
				body.appendChild((doc).createTextNode(kwrd.getString()+':'+kwrd.getCnt()+"#"));
			}
			
			doc2.appendChild(body);
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
		
		DOMSource source=new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File("./root/index.xml")));
		
		transformer.transform(source, result);
		
	}

}
