package com.hb.android.readcontactsdemo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hb.android.readcontactsdemo.R;
import com.hb.android.readcontactsdemo.adapter.ContactAdapter;
import com.hb.android.readcontactsdemo.bean.ContactInfo;
import com.hb.android.readcontactsdemo.utils.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        recyclerView = findViewById(R.id.rv_contact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 申请权限
        String[] ps = new String[]{Manifest.permission.READ_CONTACTS};
        if (PermissionsUtil.checkAndApplyPermissions(this, ps)) {
            setDate();
        }
    }

    /**
     * 设置联系人列表的数据
     */
    private void setDate() {
        // 设置数据
        List<ContactInfo> contactInfoList = getContactsList();
        ContactAdapter adapter = new ContactAdapter(this, contactInfoList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 获取通讯录数据
     *
     * @return 通讯录数据 List
     */
    @SuppressLint("Range")
    private List<ContactInfo> getContactsList() {
        List<ContactInfo> contactInfos = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        // 将光标移动到下一行。如果光标已经超过结果集中的最后一个条目，则此方法将返回false。
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int isHas = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            if (isHas > 0) {
                // 根据id查询某个人的所有电话号码
                Cursor cursor1 = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null, null);
                StringBuilder number = new StringBuilder();
                //遍历多个电话号码
                while (cursor1.moveToNext()) {
                    String s = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim();
                    // 去掉号码之间的空格
                    s = s.replace(" ", "");
                    number.append(s);
                    // 每个电话号码换一行
                    number.append("\n");
                }
                // 去掉最后一个换行
                number.deleteCharAt(number.length() - 1);
                ContactInfo contactInfo = new ContactInfo(name, number.toString());
                contactInfos.add(contactInfo);
                cursor1.close();
            }
        }
        cursor.close();
        return contactInfos;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionsUtil.checkAuthorized(this, permissions, grantResults)) {
            setDate();
        }
    }

}
