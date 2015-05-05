package com.dahuatech.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.internal.view.menu.MenuView.ItemView;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.ContactInfo;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;

/**
 * @ClassName ContactsAdapter
 * @Description ��ȡͨѶ¼��������
 * @author 21291
 * @date 2014��6��26�� ����10:23:57
 */
public class ContactsAdapter extends MyBaseAdapter<ContactInfo> {

	private DbManager mDbHelper; // ���ݿ������

	private final int TYPE_COUNT = 3; // ��������
	private final int CONTACT_TYPE = 1; // ͨѶ¼
	private final int INVITATION_TYPE = 2; // ����ͬ������
	private final int CLEAR_TYPE = 0; // ����ͬ������

	public ContactsAdapter(Context context, List<ContactInfo> data,
			int resource, DbManager mDbHelper) {
		super(context, data, resource);
		this.mDbHelper = mDbHelper;
	}

	// �Զ���ؼ�����
	static class ContactsItemView {
		public ImageButton ct_fItemsIconVisit;
		public ImageButton ct_fItemsIconMsg;
		public ImageButton ct_fItemsIconCal;
		public TextView ct_fItemNumber;
		public TextView ct_fItemName;
		public TextView ct_fCornet;
		public TextView ct_fDepartment;
		public TextView ct_fClear;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContactsItemView listItemView = null;
		ContactInfo contactInfo = null;
		int itemViewtype = getItemViewType(position);
		// �Զ�����ͼ
		switch (itemViewtype) {
		case CONTACT_TYPE:
			// ͨѶ¼����
			System.out.println("type:" + CONTACT_TYPE);
			if (convertView == null) {
				// ��ȡcontactslistlayout�����ļ�����ͼ
				convertView = listContainer
						.inflate(this.itemViewResource, null);
				listItemView = new ContactsItemView();

				// ��ȡ�ؼ�����

				listItemView.ct_fItemsIconMsg = (ImageButton) convertView
						.findViewById(R.id.contactslist_itemsIconmsg);
				listItemView.ct_fItemsIconCal = (ImageButton) convertView
						.findViewById(R.id.contactslist_itemsIconcal);
				listItemView.ct_fItemNumber = (TextView) convertView
						.findViewById(R.id.contactslist_FItemNumber);
				listItemView.ct_fItemName = (TextView) convertView
						.findViewById(R.id.contactslist_FItemName);
				listItemView.ct_fCornet = (TextView) convertView
						.findViewById(R.id.contactslist_FCornet);
				listItemView.ct_fDepartment = (TextView) convertView
						.findViewById(R.id.contactslist_FDepartment);

				// ���ÿؼ�����convertView
				convertView.setTag(listItemView);
			} else {
				// ȡ����ǰ������tag�е��Զ�����ͼ����
				listItemView = (ContactsItemView) convertView.getTag();
			}

			// ����position,�Ӽ��ϻ�ȡһ������
			contactInfo = listItems.get(position);

			// �������ز���(ʵ����)
			listItemView.ct_fItemsIconMsg.setTag(contactInfo);
			listItemView.ct_fItemsIconCal.setTag(contactInfo);
			listItemView.ct_fCornet.setTag(contactInfo);
			listItemView.ct_fItemName.setTag(contactInfo);
			listItemView.ct_fItemNumber.setText(contactInfo.getFItemNumber());
			listItemView.ct_fItemName.setText(contactInfo.getFItemName());
			listItemView.ct_fCornet.setText(contactInfo.getFCornet());
			listItemView.ct_fDepartment.setText(contactInfo.getFDepartment());
			
			//˵������������	
			listItemView.ct_fItemsIconMsg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ContactInfo contact=(ContactInfo)v.getTag(); 
					
					// ����ѡ�����Ա��Ϣ���������ݿ�
					mDbHelper.insertContactSearch(contact);
					
					//������
					Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+contact.getFCornet()));
					context.startActivity(intent);
				}
			});
			
			listItemView.ct_fItemsIconCal.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ContactInfo contact=(ContactInfo)v.getTag(); 
					
					// ����ѡ�����Ա��Ϣ���������ݿ�
					mDbHelper.insertContactSearch(contact);
					
					//���ͨѶ¼
					insertContacts(context,contact);
				}
			});	
			
			listItemView.ct_fItemName.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ContactInfo contact=(ContactInfo)v.getTag(); 
					 //��������	
						makeCall(context,contact);
				}
			});
			
			listItemView.ct_fCornet.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ContactInfo contact=(ContactInfo)v.getTag(); 
					 //��������	
						makeCall(context,contact);
				}
			});
			
			break;

		case INVITATION_TYPE:
			// ����ͬ��
			System.out.println("type:" + INVITATION_TYPE);

			if (convertView == null) {
				// ��ȡcontactslistlayout�����ļ�����ͼ
				convertView = listContainer
						.inflate(this.itemViewResource, null);
				listItemView = new ContactsItemView();
				listItemView.ct_fItemsIconVisit = (ImageButton) convertView
						.findViewById(R.id.contactslist_itemsIconvisit);
				listItemView.ct_fItemNumber = (TextView) convertView
						.findViewById(R.id.contactslist_FItemNumber);
				listItemView.ct_fItemName = (TextView) convertView
						.findViewById(R.id.contactslist_FItemName);
				listItemView.ct_fCornet = (TextView) convertView
						.findViewById(R.id.contactslist_FCornet);
				listItemView.ct_fDepartment = (TextView) convertView
						.findViewById(R.id.contactslist_FDepartment);
				
				// ���ÿؼ�����convertView
				convertView.setTag(listItemView);
			} else {
				// ȡ����ǰ������tag�е��Զ�����ͼ����
				listItemView = (ContactsItemView) convertView.getTag();
			}

			// ����position,�Ӽ��ϻ�ȡһ������
			contactInfo = listItems.get(position);

			// �������ز���(ʵ����)
			listItemView.ct_fItemsIconVisit.setTag(contactInfo);
			listItemView.ct_fCornet.setTag(contactInfo);
			listItemView.ct_fItemName.setTag(contactInfo);
			listItemView.ct_fItemNumber.setText(contactInfo.getFItemNumber());
			listItemView.ct_fItemName.setText(contactInfo.getFItemName());
			listItemView.ct_fCornet.setText(contactInfo.getFCornet());
			listItemView.ct_fDepartment.setText(contactInfo.getFDepartment());
			
			//˵��������ͬ�½���������	
			listItemView.ct_fItemsIconVisit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ContactInfo contact=(ContactInfo)v.getTag(); 
					backToSms(context,contact);
				}
			});
			
			listItemView.ct_fItemName.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ContactInfo contact=(ContactInfo)v.getTag(); 
						backToSms(context,contact);
					
				}
			});
			
			listItemView.ct_fCornet.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ContactInfo contact=(ContactInfo)v.getTag(); 
					backToSms(context,contact);
				}
			});
			break;
		case CLEAR_TYPE:
			// �����ʷ��¼
			System.out.println("type:" + CLEAR_TYPE);
			if (convertView == null) {
				// ��ȡcontactslistlayout�����ļ�����ͼ
				convertView = listContainer.inflate(
						R.layout.contacts_clear_item, null);
				listItemView = new ContactsItemView();

				// ��ȡ�ؼ�����
				listItemView.ct_fClear = (TextView) convertView
						.findViewById(R.id.contacts_item_FClear);
				// ���ÿؼ�����convertView
				convertView.setTag(listItemView);
			} else {
				// ȡ����ǰ������tag�е��Զ�����ͼ����
				listItemView = (ContactsItemView) convertView.getTag();
			}

			// ����position,�Ӽ��ϻ�ȡһ������
			contactInfo = listItems.get(position);
			listItemView.ct_fClear.setTag(contactInfo);
			listItemView.ct_fClear.setText(contactInfo.getFItemName());
			
			/**��ձ������ݿ⻺���¼*//*
			listItemView.ct_fClear.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mDbHelper.deleteContactSearchAll();
					
				}
			});*/
			break;
		}
		
		
		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		String item = listItems.get(position).getFItemNumber();
		if (listItems != null && "0".equals(item)) {
			return CLEAR_TYPE;
		} else {
			if (R.layout.contacts_item_invitation == this.itemViewResource) {
				return INVITATION_TYPE;
			} else {
				return CONTACT_TYPE;
			}
		}
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	/**
	 * @Title: makeCall
	 * @Description: ����绰
	 * @param @param context �����Ļ���
	 * @param @param contact ��ϵ��ʵ��
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��10��15�� ����4:29:31
	 */
	private void makeCall(Context context, ContactInfo contact) {
		
		// ����ѡ�����Ա��Ϣ���������ݿ�
		mDbHelper.insertContactSearch(contact);
				
		// ����绰
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ contact.getFCornet()));
		context.startActivity(intent);
	}

	/**
	 * @Title: backToSms
	 * @Description: ��������ͬ��ҳ��
	 * @param @param context �����Ļ���
	 * @param @param contact ��ϵ��ʵ��
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��10��15�� ����4:28:07
	 */
	private void backToSms(Context context, ContactInfo contact) {

		// ����ѡ�����Ա��Ϣ���������ݿ�
		mDbHelper.insertContactSearch(contact);

		Intent intent = new Intent();
		intent.putExtra(AppContext.CONTACTS_RETURN_VALUE, contact.getFCornet());
		((Activity) context).setResult(Activity.RESULT_OK, intent);
		((Activity) context).finish();
	}

	/**
	 * @Title: insertContacts
	 * @Description: �����ϵ�˵�ͨѶ¼��
	 * @param @param context
	 * @param @param contact
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��7��8�� ����4:26:41
	 */
	private void insertContacts(Context context, ContactInfo contact) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactID = ops.size();

		// Adding insert operation to operations list
		// to insert a new raw contact in the table ContactsContract.RawContacts
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				.build());

		// ������Ϊ��
		if (!StringUtils.isEmpty(contact.getFItemName())) {
			// Adding insert operation to operations list
			// to insert display name in the table ContactsContract.Data
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
					.withValue(
							ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
							contact.getFItemName()).build());
		}

		// �̺Ų�Ϊ��
		if (!StringUtils.isEmpty(contact.getFCornet())) {
			// Adding insert operation to operations list
			// to insert Mobile Number in the table ContactsContract.Data
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
							contact.getFCornet())
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
							ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
					.build());
		}

		// ���䲻Ϊ��
		if (!StringUtils.isEmpty(contact.getFEmail())) {
			// Adding insert operation to operations list
			// to insert Work Email in the table ContactsContract.Data
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Email.DATA,
							contact.getFEmail())
					.withValue(ContactsContract.CommonDataKinds.Email.TYPE,
							ContactsContract.CommonDataKinds.Email.TYPE_WORK)
					.build());
		}

		try {
			context.getContentResolver().applyBatch(ContactsContract.AUTHORITY,
					ops);
			Toast.makeText(context, R.string.contacts_addsuccess,
					Toast.LENGTH_SHORT).show();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}
	}

	// ���õ���¼�
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}
