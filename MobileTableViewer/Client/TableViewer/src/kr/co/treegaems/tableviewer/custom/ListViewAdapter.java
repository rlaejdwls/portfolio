package kr.co.treegaems.tableviewer.custom;

import java.util.ArrayList;
import java.util.Collections;

import kr.co.treegaems.tableviewer.R;
import kr.co.treegaems.tableviewer.model.ColumnInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
    private Context context = null;
    private ArrayList<ListData> listData = new ArrayList<ListData>();
 
    public ListViewAdapter(Context mContext) {
        super();
        this.context = mContext;
    }
 
    @Override
    public int getCount() {
        return listData.size();
    }
 
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
     
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_column_info, null);
     
            holder.setNo((TextView) convertView.findViewById(R.id.txtNo));
            holder.setName((TextView) convertView.findViewById(R.id.txtColumnName));
            holder.setType((TextView) convertView.findViewById(R.id.txtType));
            holder.setLength((TextView) convertView.findViewById(R.id.txtLen));
            holder.setComment((TextView) convertView.findViewById(R.id.txtColumnComment));
            holder.setIsNull((TextView) convertView.findViewById(R.id.txtNullable));
            holder.setIsPK((TextView) convertView.findViewById(R.id.txtPK));
     
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
     
        ListData data = listData.get(position);
     
        holder.getNo().setText(data.getTxtNo());
        holder.getName().setText(data.getName());
        holder.getType().setText(data.getType());
        holder.getLength().setText(data.getLength());
        holder.getComment().setText(data.getComment());
        holder.getIsNull().setText(data.getIsNull());
        holder.getIsPK().setText(data.getIsPK());
     
        return convertView;
    }
    
    public void addItem(ColumnInfo info){
        ListData addInfo = new ListData();
        addInfo.setTxtNo(Integer.toString(info.getNum()));
        addInfo.setName("|" + info.getName());
        addInfo.setType("|" + info.getType());
        addInfo.setLength("|" + info.getLength());
        addInfo.setComment("|" + (info.getComment() != null ? info.getComment() : ""));
        addInfo.setIsNull("|" + info.isNull());
        addInfo.setIsPK("|" + (info.isPK() != null ? "Y" : ""));
                 
        listData.add(addInfo);
    }
     
    public void remove(int position){
        listData.remove(position);
        dataChange();
    }
     
    public void sort(){
        Collections.sort(listData, ListData.ALPHA_COMPARATOR);
        dataChange();
    }
     
    public void dataChange(){
        this.notifyDataSetChanged();
    }
}
