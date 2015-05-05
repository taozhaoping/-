package com.dahuatech.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * @ClassName MyBaseAdapter
 * @Description �Զ������BaseAdapter
 * @author 21291
 * @date 2014��6��5�� ����2:10:25
 * @param <T>
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	protected Context context;				// ����������
	protected List<T> listItems;			// ���ݼ���
	protected LayoutInflater listContainer;	// ��ͼ����
	protected int itemViewResource;			// �Զ�������ͼԴ
	
	public List<T> getListItems() {
		return listItems;
	}
	
	public void setListItems(List<T> listItems) {
		if(listItems!=null)
			this.listItems = listItems;
		else
			this.listItems = new ArrayList<T>();
	}
	
	public MyBaseAdapter(Context context, List<T> data,int resource) {
		this.context=context;
		this.listContainer = LayoutInflater.from(context); // ������ͼ����������������
		this.itemViewResource = resource;
		setListItems(data);
	}
	
	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
