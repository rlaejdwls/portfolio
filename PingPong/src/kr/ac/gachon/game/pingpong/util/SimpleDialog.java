package kr.ac.gachon.game.pingpong.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;

public class SimpleDialog {
	public static Builder getOKButtonDialog(Context context, String title, String text){
		return new AlertDialog.Builder(context)
			.setTitle(title)
			.setMessage(text);
	}
}
