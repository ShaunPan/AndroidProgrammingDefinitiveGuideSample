package com.pan.guidesample.criminalintent;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.pan.androidprogrammingdefinitiveguidesample.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/*
 * File Name:CrimeListFragment
 * Author:Pan
 * Date:2016/1/13 18:01
 * Description:
 */
public class CrimeListFragment extends ListFragment {

    public static final String TAG = "CrimeListFragment";
    private ArrayList<Crime> mCrimes;
    private boolean mSubtitleVisible;
    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保留Fragment
        setRetainInstance(true);
        mSubtitleVisible = false;
        //通知FragmentManager:CrimeListFragment需接收选项菜单方法回调。
        setHasOptionsMenu(true);

        actionBar = getActivity().getActionBar();

        getActivity().setTitle(R.string.crime_title);
        mCrimes = CrimeLab.get(getActivity()).getmCrimes();

        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        Button addCrime = (Button) view.findViewById(R.id.bt_add_crime);
        addCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCrime();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mSubtitleVisible) {
                actionBar.setSubtitle(R.string.subtitle);
            }
        }

        ListView listView = (ListView) view.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem item = menu.findItem(R.id.menu_item_show_subtitle);
        //当子标题为显示状态且子标题菜单不为空时，菜单内容显示为：隐藏子标题
        if (mSubtitleVisible && item != null) {
            item.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                createCrime();
                return true;
            case R.id.menu_item_show_subtitle:
                //显示和隐藏子标题
                if (actionBar.getSubtitle() == null) {
                    actionBar.setSubtitle(R.string.subtitle);
                    item.setTitle(R.string.hide_subtitle);
                    mSubtitleVisible = true;
                } else {
                    actionBar.setSubtitle(null);
                    item.setTitle(R.string.show_subtitle);
                    mSubtitleVisible = false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        CrimeAdapter listAdapter = (CrimeAdapter) getListAdapter();
        Crime crime = listAdapter.getItem(position);
        switch (item.getItemId()) {
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).deleteCrime(crime);
                listAdapter.notifyDataSetChanged();
                CrimeLab.get(getActivity()).saveCrimes();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 创建陋习记录
     */
    private void createCrime() {
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime crime = (Crime) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
        startActivity(intent);
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            Crime crime = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(crime.getmTitle());
            TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            SimpleDateFormat format = new SimpleDateFormat("EEEE,MMM dd,yyyy", Locale.CHINA);
            dateTextView.setText(format.format(crime.getmDate()));
            CheckBox solvedCheckbox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckbox);
            solvedCheckbox.setChecked(crime.ismSolved());
            return convertView;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //更新list view
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

}
