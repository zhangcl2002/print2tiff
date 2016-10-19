package com.aegon.pdf2tiff;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.sun.javafx.iio.ImageStorage.ImageType;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class APP {

	/*public static void main(String[] args) {
		  //Create a file based on the first command-line argument to the program
		  File file= new File("c:\\1.pdf");
		  File file1= new File("c:\\1-t.tif");
		  
		  
		  
		  try{ 
		  FileInputStream fs= new FileInputStream(file);
		  
		  FileOutputStream fo= new FileOutputStream(file1);

		  Date date=new Date();

		  SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		  System.out.println(df.format(date));
		  
		  Pdf2TiffUtil.pdf2Tiff(fs, fo);
		  
		  Date date1=new Date();


	      System.out.println(df.format(date1));
		  
		  fs.close();
		  fo.close();
		  
		  }
		  catch (Exception e){
			  e.printStackTrace();			  
		  }
		  
		  finally{
			  			  
			  
		  }
		
		
		  
	}*/

	   
    public static void main(String[] args) throws IOException {
    	     
        Date start = new Date();  
        PDDocument document = PDDocument.load(new File("c:\\1.pdf"));
              
        FileOutputStream fo= new FileOutputStream(new File("c:\\xx.tif"));    
        
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        int pageCounter = 0;
    
        for (PDPage page : document.getPages())
        {
            // note that the page number parameter is zero based
            BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, 300,org.apache.pdfbox.rendering.ImageType.BINARY);

          //  ImageIOUtil.
            // suffix in filename will be used as the file format
     //       ImageIOUtil.writeImage(bim, "c:\\1" + "-" + (pageCounter++) + ".tif", 300);
            ImageIOUtil.writeImage(bim, "tif", fo, 300, 0f);
            
        }
        document.close();
        Date end = new Date();  
        System.out.println(end.getTime()-start.getTime());  
        System.out.println("over");  
    }  
	
}
