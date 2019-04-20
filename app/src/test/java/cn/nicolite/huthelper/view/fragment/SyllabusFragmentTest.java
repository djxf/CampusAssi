package cn.nicolite.huthelper.view.fragment;

import android.util.Log;

import org.junit.Test;

import cn.nicolite.huthelper.utils.CustomDate;
import cn.nicolite.huthelper.utils.DateUtils;

import static org.junit.Assert.*;

public class SyllabusFragmentTest {
    CustomDate mShowDate;
    @Test
    public void initDate() {
        if (mShowDate == null) {
            mShowDate = DateUtils.getNextSunday();
        }
       // mMonth.setText(mShowDate.getMonth() < 10 ? "0" + mShowDate.getMonth() : mShowDate.getMonth() + "月");
        Log.i("data",mShowDate.getYear()+"/"+mShowDate.getMonth()+"/"+mShowDate.getDay());
    }
}