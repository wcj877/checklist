package net.fkm.drawermenutest.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.activity.ChecklistActivity;
import net.fkm.drawermenutest.activity.HomeActivity;
import net.fkm.drawermenutest.dao.ListDao;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.Unbinder;

/**
 * 清单页面
 * 显示学习，工作，锻炼等清单
 */
public class ListFragment extends Fragment {

    public static ListFragment instance;

    ListView listView;
    ArrayList<ListInfo> list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //给fragment添加布局文件（1.resource:布局的资源id，2.root 填充的根视图，3.attachToRoot 是否将载入的视图绑定到根视图中）
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        instance = this;
        listView = view.findViewById(R.id.paihanglist);

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        showList();
        return view;
    }

    public void showList(){
        if (Constants.user == null)
            return;
        ListDao listDao = new ListDao(getContext());
        list = listDao.queryAll(Constants.user.getUserId());

        MyListAdapter myListAdapter=new MyListAdapter(getContext(),list);
        listView.setAdapter(myListAdapter);
    }

    class MyListAdapter extends BaseAdapter{
        LayoutInflater layoutInflater;
        ArrayList<ListInfo> list;

        public MyListAdapter(Context context, ArrayList<ListInfo> list) {
            layoutInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.list_checklist, null);
                viewHolder.confirm = (ImageView)convertView.findViewById(R.id.list_confirm);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_title);
                viewHolder.priority = (ImageView)convertView.findViewById(R.id.list_priority);
                viewHolder.line = (ConstraintLayout) convertView.findViewById(R.id.line);
                viewHolder.delete = (Button) convertView.findViewById(R.id.btnDelete);

                convertView.setTag(viewHolder);
            }else{
                 viewHolder = (ViewHolder) convertView.getTag();

            }
            ListInfo listInfo = list.get(position);

            //判断清单是否完成以显示对应的图
            switch (listInfo.getIsPerfection()){
                case 0:
                    viewHolder.confirm.setImageDrawable(getResources().getDrawable((R.mipmap.unfinished)));//设置未完成图片
                    break;
                case 1:
                    viewHolder.confirm.setImageDrawable(getResources().getDrawable((R.mipmap.accomplish)));//设置完成图片
                    viewHolder.title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//添加删除线
                    viewHolder.title.setTextColor(R.color.gray); //设置前景色为灰色

                    break;
            }

            //设置清单的内容
            viewHolder.title.setText(listInfo.getListTitle());

            //判断清单的优先级以显示对应的图
            switch (listInfo.getPriority()){
                case 0:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.gray)));
                    break;
                case 1:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.blue)));
                    break;
                case 2:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.yellow)));
                    break;
                case 4:
                    viewHolder.priority.setImageDrawable(getResources().getDrawable((R.mipmap.red)));
                    break;
            }

            //监听清单完成的点击事件
            viewHolder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListDao listDao = new ListDao(getContext());
                    listDao.updateStatus(listInfo.getListId(), listInfo.getIsPerfection());//修改清单是否完成的状态
                    ListFragment.instance.showList();//刷新清单显示
                }
            });

            viewHolder.line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ChecklistActivity.class);
                    intent.putExtra("listInfo", (Parcelable) listInfo);
                    startActivity(intent);
                }
            });

            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListDao dao = new ListDao(getContext());
                    dao.deleteList(String.valueOf(listInfo.getListId()));
                    ListFragment.instance.showList();
                    Toast.makeText(getContext(),"删除成功",Toast.LENGTH_LONG).show();
                }
            });


            return convertView;
        }
    }
    static class ViewHolder {
        public ImageView confirm;
        public TextView title;
        public ImageView priority;
        public ConstraintLayout line;
        public Button delete;
    }
}


