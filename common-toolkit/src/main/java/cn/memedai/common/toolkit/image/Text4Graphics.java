package cn.memedai.common.toolkit.image;

import java.awt.Color;
import java.awt.Font;

public class Text4Graphics {
	private String text;//要添加的文字
	private Font font;//字体
	private Color color;//颜色
	private int x;//x轴
	private int y;//y轴
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
