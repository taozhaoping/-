package com.dahuatech.app.adapter;

import com.dahuatech.app.bean.SpinnerEntity;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @ClassName SpinnerArray
 * @Description Spinner�ؼ���������
 * @author 21291
 * @date 2014��10��24�� ����1:35:10
 */
public class SpinnerArray extends ArrayAdapter<SpinnerEntity> {

	private Context context; // ������
	private SpinnerEntity[] entitys; // �Զ��弯��

	public SpinnerArray(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public SpinnerArray(Context context, int textViewResourceId,SpinnerEntity[] entitys) {
		super(context, textViewResourceId, entitys);
		this.context = context;
		this.entitys = entitys;
	}

	public int getCount() {
		return entitys.length;
	}

	public SpinnerEntity getItem(int position) {
		return entitys[position];
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        TextView tView = new TextView(context);
        tView.setTextColor(Color.BLACK);
        tView.setText(entitys[position].getFValue());
        return tView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		 TextView tView = new TextView(context);
		 tView.setTextColor(Color.BLACK);
		 tView.setText(entitys[position].getFValue());
		 return tView;
	}
}
