package com.aegon.pdf2tiff;


import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.sun.javafx.iio.ImageStorage.ImageType;
import com.sun.media.imageio.plugins.tiff.BaselineTIFFTagSet;
import com.sun.media.imageio.plugins.tiff.TIFFTag;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.TIFFEncodeParam;
//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.sun.media.jai.codec.TIFFField;

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
              
        //FileOutputStream fo= new FileOutputStream(new File("c:\\xx.tif"));    
        
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        int pageCounter = 0;
        
        System.out.println(document.getPages());
        
        ArrayList<BufferedImage>  bimTab = new ArrayList();
               
        
        for (PDPage page : document.getPages())
        {
        	
            // note that the page number parameter is zero based
        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter++, 300,org.apache.pdfbox.rendering.ImageType.BINARY);                
          //  ImageIOUtil.
            // suffix in filename will be used as the file format
            bimTab.add(bim);
           //ImageIOUtil.writeImage(bim, "c:\\1" + "-" + (pageCounter++) + ".tif", 300);
            //ImageIOUtil.writeImage(bim, "tif", fo, 300, 0f);
        
            System.out.println("kkkkkkkkkkkkkkkk");
            
        }
        
        new APP().saveAsMultipageTIFF(bimTab,"222.tif",300);
        
        document.close();
        //fo.close();
        /*List<File> srcFile ;
        try{        
        srcFile= new ArrayList();
        srcFile.add(new File("c:\\1-0.tif"));
        srcFile.add(new File("c:\\1-1.tif"));
        new APP().tif2Marge(srcFile,"c:\\2.tif");
        }
        catch (Exception e){
        	e.printStackTrace();
        }*/
        Date end = new Date();  
        System.out.println(end.getTime()-start.getTime());  
        System.out.println("over");  
    }  
    
    
    void saveAsMultipageTIFF(ArrayList<BufferedImage> bimTab, String filename, int dpi) throws IOException
    {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");
        ImageWriter imageWriter = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(new File(filename));
        imageWriter.setOutput(ios);
        imageWriter.prepareWriteSequence(null);
        for (BufferedImage image : bimTab)
        {
            ImageWriteParam param = imageWriter.getDefaultWriteParam();
            IIOMetadata metadata = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image), param);
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            TIFFUtil.setCompressionType(param, image);
            TIFFUtil.updateMetadata(metadata, image, dpi);
            imageWriter.writeToSequence(new IIOImage(image, null, metadata), param);
        }
        imageWriter.endWriteSequence();
        imageWriter.dispose();
        ios.flush();
        ios.close();
    }    
    
  
   
	
}
