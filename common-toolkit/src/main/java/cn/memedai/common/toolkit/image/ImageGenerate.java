package cn.memedai.common.toolkit.image;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public class ImageGenerate {
	
	/**
	 * 二维码宽度
	 */
	private static final int CODE_WIDTH = 200;
	/**
	 * 二维码高度
	 */
	private static final int CODE_HEIGHT = 200;
	
	private static List<String> list = new ArrayList<String>();
	
	/**
	 * 列表文件路径下的所有文件
	 * @param filePath 文件路径
	 */
	public static void listAllFiles(String filePath){
		File directoryFile = new File(filePath);
		if(directoryFile.exists()){
			File[] files = directoryFile.listFiles();
			if(files.length==0){
				System.out.println("空文件夹!");
			}else{
				for(File file : files){
					if(file.isDirectory()){
						//System.out.println("文件夹:"+ file.getAbsolutePath());
						listAllFiles(file.getAbsolutePath());
					}else{
						list.add(file.getAbsolutePath());
						//System.out.println("文件路径:" +file.getAbsolutePath() + ",文件名:"+file.getName());
					}
				}
			}
		}else{
			System.out.println("文件不存在");
		}
	}

	
	/**
	 * 确认生成二维码
	 * @param email
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
			listAllFiles("M:/demo/qcode/");
			
			//添加员工编号
			for(int i=0;i<list.size();i++){
				
				//文件全路径
				String filePath = list.get(i);
				System.out.println("文件路径:" + filePath);
				
				//M:\demo\qcode\1016002-4-0997_.png
				//员工编号
				String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
				String text = fileName.substring(0, fileName.lastIndexOf(".png")-1);
				String textName = "编号:"+text;
				System.out.println("编号为:"+fileName);
				
				String fontPath = "M:/demo/ltxh.TTF";
				FileInputStream aixing = new FileInputStream(new File(fontPath));
	            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
	            
	            List<Text4Graphics> textList = new ArrayList<Text4Graphics>();
	            //设置编号
	            Text4Graphics idText4Graphics = new Text4Graphics();
	            idText4Graphics.setText(textName);
	            idText4Graphics.setX(40);
	            idText4Graphics.setY(210);
	            //idText4Graphics.setFont(dynamicFont.deriveFont(Font.PLAIN, 50));
	            idText4Graphics.setFont(new Font("宋体", Font.BOLD, 50));
	            idText4Graphics.setColor(new Color(0x000000));
	            textList.add(idText4Graphics);

				List<Image4Graphics> imageList = new ArrayList<Image4Graphics>();
				/*添加二维码*/
				Image4Graphics iconImage4Graphics = new Image4Graphics();
				//"M:/demo/qcode/1016002-4-0421_.png"
				iconImage4Graphics.setImagePath(filePath);
				iconImage4Graphics.setX(670);
				iconImage4Graphics.setY(20);
				iconImage4Graphics.setClarity(1);
				imageList.add(iconImage4Graphics);
				
				/*添加员工图片*/
				/*Image4Graphics empImage4Graphics = new Image4Graphics();
				empImage4Graphics.setImagePath(file.getPath());
				empImage4Graphics.setX(70);
				empImage4Graphics.setY(238);
				empImage4Graphics.setClarity(1);
				imageList.add(empImage4Graphics);*/
				//生成水印
				generateBadeg(textList, imageList, 0, "M:/demo/1.jpg", "M:/demo/output/"+fileName);
			}
			
			
	}

	
	
	private static final String FORMAT = "png";
	/**
	 * @param textList 文字水印列表
	 * @param imageList 图片水印列表
	 * @param degree 水印图片旋转角度
	 * @param srcImgPath 背景图片
	 * @param targerPath 输出图片
	 */
	private static void generateBadeg(List<Text4Graphics> textList,List<Image4Graphics> imageList,Integer degree,String srcImgPath,String targerPath){
			OutputStream os = null;
			try {
			Image srcImg = ImageIO.read(new File(srcImgPath));
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
			       srcImg.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
			// 得到画笔对象
			Graphics2D g = buffImg.createGraphics();
			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			       RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawImage(
			       srcImg.getScaledInstance(srcImg.getWidth(null),
			               srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
			       null);
			if (null != degree) {
			   // 设置水印旋转
			   g.rotate(Math.toRadians(degree),
			           (double) buffImg.getWidth() / 2,
			           (double) buffImg.getHeight() / 2);
			}
			//添加文字水印
			for(Text4Graphics text4Graphics: textList){
				//设置字体颜色
				g.setColor(text4Graphics.getColor());
				//设置字体
				g.setFont(text4Graphics.getFont());
				g.setStroke(new BasicStroke(3));
				//10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
				g.drawString(text4Graphics.getText(),text4Graphics.getX(),text4Graphics.getY());
				
			}
			//添加图片水印
			for(Image4Graphics image4Graphics: imageList){
				ImageIcon imgIcon = new ImageIcon(image4Graphics.getImagePath());//二维码路径
		        Image img = imgIcon.getImage();
		        //设置透明度
		        g.setComposite(AlphaComposite.getInstance(10, image4Graphics.getClarity()));
		        g.drawImage(img, image4Graphics.getX(), image4Graphics.getY(),CODE_WIDTH,CODE_HEIGHT, null);
		        g.setComposite(AlphaComposite.getInstance(3));
			}
			
			//释放资源
			g.dispose();
			
			File out = new File(targerPath);
			if (out.exists()) {
			   out.delete();
			}
			os = new FileOutputStream(targerPath);
			// 生成图片
			ImageIO.write(buffImg, FORMAT, os);
			} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
			   if (null != os)
			       os.close();
			} catch (Exception e) {
			   e.printStackTrace();
			}
		}

	}
	
		
}
