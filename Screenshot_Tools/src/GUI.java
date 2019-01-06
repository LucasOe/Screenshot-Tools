import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
 
public class GUI extends JFrame {
 
	private static final long serialVersionUID = 1L;
	public static ArrayList<File> fileNames = new ArrayList<File>();
 
	//Colors
	public static Color gray		= new java.awt.Color(44,47,51);
	public static Color dark_gray 	= new java.awt.Color(35,39,42);
	public static Color light_blue 	= new java.awt.Color(153,170,181);
	public static Color white 		= new java.awt.Color(255,255,255);
	public static Color blue 		= new java.awt.Color(114,137,218);
	
	public GUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(400, 400));
		setPreferredSize(new Dimension(400, 400));
	    setResizable(false);
	   	
	    SpringLayout dropLayout = new SpringLayout();
	   	getContentPane().setLayout(dropLayout);
	   	getContentPane().setBackground(gray);
	   	
	   	//Image 
	   	Icon icon = new ImageIcon(this.getClass().getResource("/DragAndDrop_Icon.png"));
	   	
	   	//Drop
	    JLabel drop = new JLabel(icon);
	    
	    dropLayout.putConstraint(SpringLayout.NORTH, 	drop, 5, 	SpringLayout.NORTH, getContentPane());
	   	dropLayout.putConstraint(SpringLayout.WEST, 	drop, 5, 	SpringLayout.WEST, 	getContentPane());
	   	dropLayout.putConstraint(SpringLayout.SOUTH, 	drop, -5, SpringLayout.SOUTH, getContentPane());
	   	dropLayout.putConstraint(SpringLayout.EAST, 	drop, -5, 	SpringLayout.EAST, 	getContentPane());
	   	
	   	drop.setBorder(new LineBorder(white));
	   	drop.setForeground(white);
	   	drop.setBackground(dark_gray);
	   	drop.setOpaque(true);
	   	
	   	getContentPane().add(drop);   	
	   	setVisible(true);
	   	
	    new DropTarget(drop, new DropTargetListener() {
	    	@Override
			public void drop(DropTargetDropEvent dtde) {
				try {
					Transferable tr = dtde.getTransferable();
					DataFlavor[] flavors = tr.getTransferDataFlavors();
					
					for (int i = 0; i < flavors.length; i++) {
						if (flavors[i].isFlavorJavaFileListType()) {
							dtde.acceptDrop(dtde.getDropAction());
							@SuppressWarnings("unchecked")
							java.util.List<File> files = (java.util.List<File>) tr.getTransferData(flavors[i]);
							//Add Files to fileNames Array
							for (int k = 0; k < files.size(); k++) {
								fileNames.add(files.get(k));
								//System.out.println(files.get(k));
							}
						}
					 
						dtde.dropComplete(true);
					}
					
				} catch (Throwable t) {
						    t.printStackTrace();
				}
				dtde.rejectDrop();
				
				System.out.println("Found:");
				convert(fileNames);
				drop.setBackground(dark_gray);
				playSound();
				System.out.println("Completed");
	    	}
		 
	    	@Override
			public void dragEnter(DropTargetDragEvent dtde)
			{
			}
		 
			@Override
			public void dragOver(DropTargetDragEvent dtde)
			{
				drop.setBackground(gray);
			}
		 
			@Override
			public void dropActionChanged(DropTargetDragEvent dtde)
			{
				drop.setBackground(dark_gray);
			}
		 
			@Override
			public void dragExit(DropTargetEvent dte)
			{
				drop.setBackground(dark_gray);
			}
		 
		});
    }
	
	public static void main(String[] args) {
		new GUI();
		//ConvertImageFile converter = new ConvertImageFile();
		//converter.convert(filepath, filenameInput, filenameOutput, directoryName);
    }
	
	public static void convert(ArrayList<File> fileNames) {
		//Vars
		String directoryName = "converted\\";
		
		ConvertImageFile converter = new ConvertImageFile();
		for(File file: fileNames) {
			//Create Vars
			String filepath = file.getParent()+"\\";
			String filename = file.getName().substring(0, file.getName().length()-4);
			System.out.println("Path: "+filepath);
			System.out.println("Name:"+filename);
			//Convert
			if(type(file).equals(".png")) {
				converter.convert(filepath, filename, directoryName);
			} else if(type(file).equals(".jpg")) {
				try {
					converter.move(filepath, directoryName, filename);
				} catch (IOException e) {
					System.out.println("Konnte nicht Kopieren: " + e.getMessage());
				}
			} else {
				System.out.println("Error: Filetype not found");
				return;
			}
		}
	}
	
	public static String type(File file) {
		String ending = file.getName().substring(file.getName().length()-4, file.getName().length());
		return ending;
	}
	
	public static void playSound() {
			URL url = GUI.class.getClassLoader().getResource("orb.wav");
			try {
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
		        clip.start();
				
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		
	}
}
