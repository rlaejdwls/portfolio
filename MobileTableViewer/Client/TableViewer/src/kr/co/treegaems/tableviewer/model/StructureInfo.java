package kr.co.treegaems.tableviewer.model;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class StructureInfo {
	@ElementList(entry="TableInfo", type=TableInfo.class, inline=true)
	private ArrayList<TableInfo> tableInfos = new ArrayList<TableInfo>();
	
	public StructureInfo() {
		super();
	}
	public StructureInfo(ArrayList<TableInfo> tableInfos) {
		this.tableInfos = tableInfos;
	}
	
	
	public ArrayList<TableInfo> getTableInfos() {
		return tableInfos;
	}
	public void setTableInfos(ArrayList<TableInfo> tableInfos) {
		this.tableInfos = tableInfos;
	}

	@Override
	public String toString() {
		return "StructureInfo [tableInfos=" + tableInfos + "]";
	}
}
