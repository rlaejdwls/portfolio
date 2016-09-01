package kr.co.treegaems.tableviewer.model;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class TableInfo {
	@Attribute(name = "Name")
	private String name;
	@Attribute(name = "Comment", required=false)
	private String comment;
	@Attribute(name = "Alias")
	private String alias;
	private boolean selected;

	@ElementList(entry="ColumnInfo", type=ColumnInfo.class, inline=true)
	private ArrayList<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();

	public TableInfo(@Attribute(name = "Name") String name, 
			@Attribute(name = "Comment") String comment) {
		super();
		this.name = name;
		this.comment = comment;
		this.selected = false;
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
	public ArrayList<ColumnInfo> getColumnInfos() {
		return columnInfos;
	}
	public void setColumnInfos(ArrayList<ColumnInfo> columnInfos) {
		this.columnInfos = columnInfos;
	}
	public int getColSize() {
		if (columnInfos != null) return columnInfos.size();
		else return 0;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@Override
	public String toString() {
		return "TableInfo [name=" + name + ", comment=" + comment + ", alias="
				+ alias + ", selected=" + selected + ", columnInfos="
				+ columnInfos + "]";
	}
}
