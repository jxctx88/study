package cn.memedai.common.toolkit.image;

import java.io.File;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		listAllFiles("M:/demo/qcode");
	}
	
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
						System.out.println("文件夹:"+ file.getAbsolutePath());
						listAllFiles(file.getAbsolutePath());
					}else{
						String filePa = file.getAbsolutePath();
						System.out.println("编  号 : "+filePa.substring(filePa.lastIndexOf("\\")+1));
						System.out.println("文件路径:" +file.getAbsolutePath() + ",文件名:"+file.getName());
					}
				}
			}
		}else{
			System.out.println("文件不存在");
		}
	}

}
