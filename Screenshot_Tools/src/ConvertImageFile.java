import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ConvertImageFile {
	
	public void convert(String filepath, String filename, String directoryName) {
		
		createFile(filepath, directoryName);
		convertImage(filepath, filename, directoryName);
		try {
			copy(filepath, directoryName, filename);
		} catch (IOException e) {
			System.out.println("Konnte nicht Kopieren: " + e.getMessage());
		}
	}
	
	public void convertImage(String filepath, String filename, String directoryName) {
		BufferedImage bufferedImage;
		try {
			//read image file
			bufferedImage = ImageIO.read(new File(filepath+filename+".png"));

			// create a blank, RGB, same width and height, and a white background
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
			bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.BLACK, null);

			// write to jpeg file
			ImageIO.write(newBufferedImage, "jpg", new File(filepath+directoryName+filename+"_converted.jpg"));

			System.out.println("Converted Files");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createFile(String filepath, String directoryName) {
		new File(filepath+directoryName).mkdir();
		System.out.println("Created new directory: " + directoryName);
	}
	
	public void copy(String filepath, String directoryName, String filename) throws IOException {
		 Path FROM = Paths.get(filepath+directoryName+filename+"_converted.jpg");
		 Path TO = Paths.get(filepath+directoryName+filename+"_converted_vr.jpg");
		 //overwrite existing file, if exists
		 CopyOption[] options = new CopyOption[]{
				 StandardCopyOption.REPLACE_EXISTING,
				 StandardCopyOption.COPY_ATTRIBUTES
		 }; 
		 Files.copy(FROM, TO, options);
		 System.out.println("Copied Files");
	}
	
	public void move(String filepath, String directoryName, String filename) throws IOException {
		 Path FROM = Paths.get(filepath+filename+".jpg");
		 Path TODIR = Paths.get(filepath+directoryName+filename+".jpg");
		 Path TO = Paths.get(filepath+directoryName+filename+"_vr.jpg");
		 //overwrite existing file, if exists
		 CopyOption[] options = new CopyOption[]{
				 StandardCopyOption.REPLACE_EXISTING,
				 StandardCopyOption.COPY_ATTRIBUTES
		 }; 
		 Files.copy(FROM, TODIR, options);
		 Files.copy(TODIR, TO, options);
		 System.out.println("Copied Files");
	}


}