package cn.memedai.common.toolkit.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列号工具
 * @author chengtx
 *
 */
public class SerializeUtil {
	
	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object){
		if(null == object) return null;
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try{
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		}catch(Exception e){
			throw new RuntimeException("序列化对象异常",e);
		}finally{
			try {
				baos.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	public static Object unSerialize(byte[] bytes){
		if(null == bytes) return null;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try{
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		}catch(Exception e){
			throw new RuntimeException("反序列化异常",e);
		}finally{
			try {
				ois.close();
				bais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
