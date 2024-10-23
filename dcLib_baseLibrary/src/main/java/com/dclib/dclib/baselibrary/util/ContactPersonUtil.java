package com.dclib.dclib.baselibrary.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.dclib.dclib.baselibrary.vo.ContactPersonVo;
import java.util.ArrayList;
import java.util.List;

/**
 * 联系人工具类
 * Created on 2019/5/24
 *
 * @author daichao
 */
public class ContactPersonUtil {

    private Context context;

    public ContactPersonUtil(Context context) {
        this.context = context;
    }

    /**
     * 获取联系人信息
     */
    public List<ContactPersonVo> getContactList() {
        List<ContactPersonVo> list = new ArrayList<>();

        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        ContentResolver resolver = context.getContentResolver();

        // 在这里我们给query传递进去一个SORT_KEY_PRIMARY。
        // 告诉ContentResolver获得的结果安装联系人名字的首字母有序排列。
        Cursor cursor = resolver.query(uri, null, null, null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                // 联系人ID
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                // 获得联系人姓名
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                // 获得联系人手机号码
                Cursor phoneCursor = resolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                                + id, null, null);

                // 取得电话号码(可能存在多个号码) 因为同一个名字下，用户可能存有一个以上的号，遍历。
                String phoneNumber = "";
                if (phoneCursor != null) {
                    while (phoneCursor.moveToNext()) {
                        int phoneFieldColumnIndex = phoneCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNumber = phoneCursor.getString(phoneFieldColumnIndex);
                        // 有的手机号中间会带有空格
                        phoneNumber = phoneNumber.replace(" ", "");
                    }
                    phoneCursor.close();
                }

                ContactPersonVo contactPersonVO = new ContactPersonVo();
                contactPersonVO.setName(name);
//                contactPersonVO.setFirstLetter(Pinyin4jUtil.getFirstSpell(name));
//                contactPersonVO.setPinYin(Pinyin4jUtil.getPingYin(name));
                contactPersonVO.setPhone(phoneNumber);

                list.add(contactPersonVO);
            }

            cursor.close();
        }
        return list;
    }
}
