package scripts;

import java.io.File;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class makeCollection {
	
	private String data;
	
	static class Main {
		public static File[] makeFileList(String path) {
			File dir = new File(path);
			return dir.listFiles();
		}
	}
	
	public makeCollection(String data) {
		// TODO Auto-generated constructor stub
		this.data=data;
	}

	public void makeXml() throws ParserConfigurationException, IOException, TransformerException {
		// TODO Auto-generated method stub
		
		File[] files = Main.makeFileList(data);
		
		DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc= docBuilder.newDocument();
		
		Element docs= doc.createElement("docs");
		doc.appendChild(docs);
		
		//반복문으로 파일 파싱하기
		for(int i=0 ; i<5;i++) {
					
			Element doc2 = doc.createElement("doc");
			docs.appendChild(doc2);
					
			doc2.setAttribute("id",String.valueOf(i));
					
			org.jsoup.nodes.Document html = Jsoup.parse(files[i],"UTF-8");
					
			String titleData = html.title();
			String bodyData=html.body().text();
					
			Element title= doc.createElement("title");
			title.appendChild((doc).createTextNode(titleData));
			doc2.appendChild(title);
				
			Element body =  doc.createElement("body");
			body.appendChild((doc).createTextNode(bodyData));
			doc2.appendChild(body);
		}
				
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
		
		DOMSource source=new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File("./collection.xml")));
		
		transformer.transform(source, result);
	}

	
	
}