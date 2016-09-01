package kr.ac.gachon.game.pingpong.adapter;

import java.util.ArrayList;

import kr.ac.gachon.game.pingpong.R;
import kr.ac.gachon.game.pingpong.model.RankModel;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RankAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<RankModel> rankList;
	private int layout;
	
	public RankAdapter(LayoutInflater inflater,
			ArrayList<RankModel> rankList, int layout) {
		super();
		this.inflater = inflater;
		this.rankList = rankList;
		this.layout = layout;
	}

	@Override
	public int getCount() {
		return rankList.size();
	}
	@Override
	public Object getItem(int arg0) {
		return rankList.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if (arg1 == null) {
			arg1 = inflater.inflate(layout, arg2, false);
		}
		TextView lblRank = (TextView) arg1.findViewById(R.id.lbl_rank);
		lblRank.setText(rankList.get(arg0).getRank());

		TextView lblId = (TextView) arg1.findViewById(R.id.lbl_id);
		lblId.setText(rankList.get(arg0).getRecMemId());

		TextView lblWins = (TextView) arg1.findViewById(R.id.lbl_wins);
		lblWins.setText(rankList.get(arg0).getRecWins());

		if (arg0 == 0) {
			lblRank.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
			lblId.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
			lblWins.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		}
		if (rankList.get(arg0).isPlayer()) {
			lblRank.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
			lblId.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
			lblWins.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		}
		return arg1;
	}
}
