package kr.co.treegaems.tableviewer.custom;

import java.text.Collator;
import java.util.Comparator;

public class ListData {
    private String txtNo;
	private String name;
	private String type;
	private String length;
	private String comment;
	private String alias;
	private String isNull;
	private String isPK;
     
    public String getTxtNo() {
		return txtNo;
	}
	public void setTxtNo(String txtNo) {
		this.txtNo = txtNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getIsPK() {
		return isPK;
	}
	public void setIsPK(String isPK) {
		this.isPK = isPK;
	}

	public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator collator = Collator.getInstance();

		@Override
		public int compare(ListData lhs, ListData rhs) {
			return collator.compare(lhs.txtNo, rhs.txtNo);
		}
    };
}