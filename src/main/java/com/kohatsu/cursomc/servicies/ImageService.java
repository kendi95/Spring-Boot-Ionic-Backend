package com.kohatsu.cursomc.servicies;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kohatsu.cursomc.servicies.exceptions.FileException;

@Service
public class ImageService {

	
	public BufferedImage getJpgImageFromFile(MultipartFile multipartFile) {
		
		String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		
		if(!"png".equals(ext) && !"jpg".equals(ext)) {
			
			throw new FileException("Somente imagens PNG e JPG são permitidos.");
			
		}
		
		try {
			
			BufferedImage img = ImageIO.read(multipartFile.getInputStream());
			
			if("png".equals(ext)) {
				
				img = pngToJpg(img);
				
			}
			
			return img;
			
		}catch(IOException ex) {
			
			throw new FileException("Erro ao ler arquivo");
			
		}
		
	}
	
	private BufferedImage pngToJpg(BufferedImage img) {
		
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		
		return jpgImage;
		
	}
	
	public InputStream getInputStream(BufferedImage img, String extension) {
		
		try {
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			
			return new ByteArrayInputStream(os.toByteArray());
			
		}catch(IOException ex) {
			
			throw new FileException("Erro ao ler arquivo");
			
		}
		
	}
	
}