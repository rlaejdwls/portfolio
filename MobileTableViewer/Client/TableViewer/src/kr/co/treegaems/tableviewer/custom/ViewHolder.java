package kr.co.treegaems.tableviewer.custom;

import android.widget.TextView;

public class ViewHolder {
	private TextView no;
	private TextView name;
	private TextView type;
	private TextView length;
	private TextView comment;
	private TextView alias;
	private TextView isNull;
	private TextView isPK;

	public ViewHolder() {
		super();
	}
	
	public TextView getNo() {
		return no;
	}
	public void setNo(TextView no) {
		this.no = no;
	}
	public TextView getName() {
		return name;
	}
	public void setName(TextView name) {
		this.name = name;
	}
	public TextView getType() {
		return type;
	}
	public void setType(TextView type) {
		this.type = type;
	}
	public TextView getLength() {
		return length;
	}
	public void setLength(TextView length) {
		this.length = length;
	}
	public TextView getComment() {
		return comment;
	}
	public void setComment(TextView comment) {
		this.comment = comment;
	}
	public TextView getAlias() {
		return alias;
	}
	public void setAlias(TextView alias) {
		this.alias = alias;
	}
	public TextView getIsNull() {
		return isNull;
	}
	public void setIsNull(TextView isNull) {
		this.isNull = isNull;
	}
	public TextView getIsPK() {
		return isPK;
	}
	public void setIsPK(TextView isPK) {
		this.isPK = isPK;
	}
}
