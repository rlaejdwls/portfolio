package kr.co.treegaems.tableviewer.model;

import org.simpleframework.xml.Attribute;

public class ColumnInfo {
	@Attribute(name = "No")
	private int num;
	@Attribute(name = "Name")
	private String name;
	@Attribute(name = "Type")
	private String type;
	@Attribute(name = "Len")
	private String length;
	@Attribute(name = "Comment", required=false)
	private String comment;
	@Attribute(name = "Alias")
	private String alias;
	@Attribute(name = "NULL")
	private String isNull;
	@Attribute(name = "PK", required=false)
	private String isPK;
	private boolean selected;
	
	public ColumnInfo() {
		super();
		this.selected = false;
	}
	public ColumnInfo(
			@Attribute(name = "No") int num, 
			@Attribute(name = "Name") String name, 
			@Attribute(name = "Type") String type, 
			@Attribute(name = "Len") String length,
			@Attribute(name = "Comment") String comment, 
			@Attribute(name = "Alias") String alias, 
			@Attribute(name = "NULL") String isNull, 
			@Attribute(name = "PK") String isPK) {
		super();
		this.num = num;
		this.name = name;
		this.type = type;
		this.length = length;
		this.comment = comment;
		this.alias = alias;
		this.isNull = isNull;
		this.isPK = isPK;
		this.selected = false;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		
		String temp = "";
		for (int i = 0; i < name.length(); i++) {
			boolean pass = true;
			char ch = name.charAt(i);
			
			if ((ch >= 'A' && ch <= 'Z') || ch == '_') {
				if (ch == '_') { i++; pass = false; }
				ch = name.charAt(i);
				
				if (i != 0 && pass) ch = (char) (ch + 32);
			}
			
			temp += ch;
		}
		
		this.alias = temp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String isNull() {
		return isNull;
	}
	public void setNull(String isNull) {
		this.isNull = isNull;
	}
	public String isPK() {
		return isPK;
	}
	public void setPK(String isPK) {
		this.isPK = isPK;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getAlias2() {
		return ((char)(this.alias.charAt(0) + 32)) + this.alias.substring(1);
	}
	
	@Override
	public String toString() {
		return "ColumnInfo [num=" + num + ", name=" + name + ", type=" + type
				+ ", length=" + length + ", comment=" + comment + ", alias="
				+ alias + ", isNull=" + isNull + ", isPK=" + isPK
				+ ", selected=" + selected + "]";
	}
}
