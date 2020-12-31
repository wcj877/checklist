package net.fkm.drawermenutest.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import net.fkm.drawermenutest.R;
import net.fkm.drawermenutest.dao.UserDao;
import net.fkm.drawermenutest.fragment.CalendarFragment;
import net.fkm.drawermenutest.fragment.CountdownFragment;
import net.fkm.drawermenutest.fragment.ListFragment;
import net.fkm.drawermenutest.model.UserInfo;
import net.fkm.drawermenutest.utils.Constants;
import net.fkm.drawermenutest.utils.GlideUtil;
import net.fkm.drawermenutest.utils.PerfectClickListener;

//import net.fkm.drawermenutest.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity {

    public static HomeActivity instance;
    // 头像URL
    public static final String IC_AVATAR = "https://clouddisc.oss-cn-hongkong.aliyuncs.com/image/ic_user.png?x-oss-process=style/thumb";

    private long exitTime = 0;
    private View headerView;//抽屉视图
    TextView username;//显示用户名的TextView

    @BindView(R.id.drawer_layout)//将字段绑定到指定ID的视图。该视图将自动转换为字段类型
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navView;

    @BindView(R.id.vp_content)
    ViewPager vp_content;

    private List<Fragment> mFragment;

    @BindView(R.id.iv_title_one)
    ImageView ivTitleOne;

    @BindView(R.id.iv_title_two)
    ImageView ivTitleTwo;

    @BindView(R.id.iv_title_three)
    ImageView ivTitleThree;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.activity_home_layout;
    }

    @Override
    protected void initView() {
        //指定的{@link Activity}中BindView带注释的字段和方法。当前内容视图用作视图根。
        ButterKnife.bind(this);
//        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, drawerLayout,
//                this.getResources().getColor(R.color.colorPrimary));
        initDrawerLayout();
        initContentFragment();
    }

    @Override
    protected void initData() {
        UserDao userDao = new UserDao(HomeActivity.this);
        Constants.user = userDao.getUser();
        if (Constants.user != null)
            username.setText(Constants.user.getUserId());

    }

    private void initDrawerLayout() {
        //添加抽屉布局
        navView.inflateHeaderView(R.layout.nav_header_main);

        headerView = navView.getHeaderView(0);

        ImageView iv_avatar = headerView.findViewById(R.id.iv_avatar);
        iv_avatar.setOnClickListener(listener);

        GlideUtil.displayCircle(iv_avatar, IC_AVATAR);

        LinearLayout ll_nav_account = headerView.findViewById(R.id.ll_nav_account);
        ll_nav_account.setOnClickListener(listener);

        LinearLayout ll_nav_password = headerView.findViewById(R.id.ll_nav_password);
        ll_nav_password.setOnClickListener(listener);

        LinearLayout ll_nav_feedback = headerView.findViewById(R.id.ll_nav_feedback);
        ll_nav_feedback.setOnClickListener(listener);

        LinearLayout ll_nav_version_update = headerView.findViewById(R.id.ll_nav_version_update);
        ll_nav_version_update.setOnClickListener(listener);

        LinearLayout ll_nav_score = headerView.findViewById(R.id.ll_nav_score);
        ll_nav_score.setOnClickListener(listener);

        LinearLayout ll_nav_account_switch = headerView.findViewById(R.id.ll_nav_account_switch);
        ll_nav_account_switch.setOnClickListener(listener);

        LinearLayout ll_nav_logout = headerView.findViewById(R.id.ll_nav_logout);
        ll_nav_logout.setOnClickListener(this::onClick);

        LinearLayout ll_nav_inbox = headerView.findViewById(R.id.ll_nav_inbox);
        ll_nav_inbox.setOnClickListener(this::onClick);

        LinearLayout ll_nav_today = headerView.findViewById(R.id.ll_nav_today);
        ll_nav_today.setOnClickListener(this::onClick);

        LinearLayout ll_nav_learn = headerView.findViewById(R.id.ll_nav_learn);
        ll_nav_learn.setOnClickListener(this::onClick);

        LinearLayout ll_nav_workout = headerView.findViewById(R.id.ll_nav_workout);
        ll_nav_workout.setOnClickListener(this::onClick);

        LinearLayout ll_nav_job = headerView.findViewById(R.id.ll_nav_job);
        ll_nav_job.setOnClickListener(this::onClick);

        toolbar.setTitle("");//设置标题为空
        //用toolbar替换actionbar
        setSupportActionBar(toolbar);

        username = headerView.findViewById(R.id.tv_username);


        //设置菜单图标颜色
        Drawable moreIcon = ContextCompat.getDrawable(toolbar.getContext(), R.drawable.abc_ic_menu_overflow_material);
        //设置颜色为黑色
        moreIcon.setColorFilter(ContextCompat.getColor(toolbar.getContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        toolbar.setOverflowIcon(moreIcon);

    }

    private PerfectClickListener listener = new PerfectClickListener() {

        @Override
        protected void onNoDoubleClick(final View v) {
            drawerLayout.closeDrawer(GravityCompat.START);
            drawerLayout.postDelayed(() -> {
                switch (v.getId()) {
                    case R.id.ll_nav_account:
//                        ToastUtil.showToast("个人中心");
                        Intent accountIntent = new Intent(HomeActivity.this, TestActivity.class);
                        accountIntent.putExtra("text", "个人中心");
                        startActivity(accountIntent);
                        break;
                    case R.id.ll_nav_password:
//                        ToastUtil.showToast("密码修改");
                        Intent passwordIntent = new Intent(HomeActivity.this, PasswordActivity.class);
//                        passwordIntent.putExtra("text", "密码设置");
                        startActivity(passwordIntent);
                        break;
                    case R.id.ll_nav_feedback:
//                        ToastUtil.showToast("意见反馈");
                        Intent feedbackIntent = new Intent(HomeActivity.this, TestActivity.class);
                        feedbackIntent.putExtra("text", "意见反馈");
                        startActivity(feedbackIntent);
                        break;
                    case R.id.ll_nav_version_update:
//                        ToastUtil.showToast("版本更新");
                        Intent updateIntent = new Intent(HomeActivity.this, TestActivity.class);
                        updateIntent.putExtra("text", "版本更新");
                        startActivity(updateIntent);
                        break;
                    case R.id.ll_nav_score:
//                        ToastUtil.showToast("给个评分呗");
                        Intent scoreIntent = new Intent(HomeActivity.this, TestActivity.class);
                        scoreIntent.putExtra("text", "给个评分呗");
                        startActivity(scoreIntent);
                        break;
                    case R.id.ll_nav_account_switch:
//                        ToastUtil.showToast("切换账号");

                        if (Constants.user != null){
                            UserDao userDao = new UserDao(HomeActivity.this);
                            Constants.user.setUserStutas(0);
                            userDao.updateStatus(Constants.user);

                            Intent switchIntent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(switchIntent);
                        } else
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        break;
                    case R.id.iv_avatar://登陆
                        Intent avatarIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(avatarIntent);
                        break;
                    default:
                        break;
                }
            }, 260);
        }
    };

    private void initContentFragment() {

//        FloatWindow
//                .with(getApplicationContext())
//                .setView(view)
//                .setWidth(100)                   //100px
//                .setHeight(Screen.width,0.2f)    //屏幕宽度的 20%
//                .build();

        mFragment = new ArrayList<>();
        mFragment.add(new ListFragment());
        mFragment.add(new CalendarFragment());
        mFragment.add(new CountdownFragment());
        //预加载最多的数量
        vp_content.setOffscreenPageLimit(2);
        //设置适配器
        vp_content.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }
        });
        // 设置默认加载第2个Fragment
        setCurrentItem(1);
        // ViewPager的滑动监听
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @OnClick({R.id.ll_title_menu, R.id.iv_title_one, R.id.iv_title_two, R.id.iv_title_three})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:
                // 开启抽屉式菜单
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_one:
                // 这样做的目的是减少cpu的损耗
                if (vp_content.getCurrentItem() != 0) {
                    setCurrentItem(0);
                }
                break;
            case R.id.iv_title_two:
                if (vp_content.getCurrentItem() != 1) {
                    setCurrentItem(1);
                }
                break;
            case R.id.iv_title_three:
                if (vp_content.getCurrentItem() != 2) {
                    setCurrentItem(2);
                }
                break;
            case R.id.ll_nav_inbox:
                //清单切换为收集箱
                Constants.listStatus = 0;
                ListFragment.instance.showList();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_nav_today:
                //清单切换为今天
//                Constants.listStatus = 1;
//                ListFragment.instance.showList();
//                drawerLayout.closeDrawer(GravityCompat.START);
                Constants.listStatus = 0;
                Constants.isToDay = true;
                ListFragment.instance.showList();
                Constants.isToDay = false;
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_nav_learn:
                //清单切换为学习清单
                Constants.listStatus = 1;
                ListFragment.instance.showList();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_nav_workout:
                //清单切换为锻炼清单
                Constants.listStatus = 2;
                ListFragment.instance.showList();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_nav_job:
                //清单切换为工作清单
                Constants.listStatus = 3;
                ListFragment.instance.showList();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_nav_logout:
                // 退出登录
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 切换页面
     *
     * @param position 分类角标
     */
    private void setCurrentItem(int position) {
        boolean isOne = false;
        boolean isTwo = false;
        boolean isThree = false;
        switch (position) {
            case 0:
                isOne = true;
                break;
            case 1:
                isTwo = true;
                break;
            case 2:
                isThree = true;
                break;
            default:
                isTwo = true;
                break;
        }
        vp_content.setCurrentItem(position);
        ivTitleOne.setSelected(isOne);
        ivTitleTwo.setSelected(isTwo);
        ivTitleThree.setSelected(isThree);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                ToastUtil.showToast("再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
//                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addCategory(Intent.CATEGORY_HOME);
//                    startActivity(intent);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 膨胀菜单；这会将项目添加到操作栏（如果有）。
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //重写onOptionsItemSelected方法监听菜单栏点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.menu_add_list://添加清单
                Intent intent = new Intent(HomeActivity.this, ChecklistActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_add_habit:
                startActivity(new Intent(HomeActivity.this, HabitActivity.class));
                break;
            case R.id.show_complete://显示已完成的清单
                Constants.showCompleted = true;
                ListFragment.instance.showList();
                break;
            case R.id.hide_complete://隐藏已完成的清单
                Constants.showCompleted = false;
                ListFragment.instance.showList();
                break;
            case R.id.sort_default://默认排序
                Constants.sortBy = null;
                ListFragment.instance.showList();
                break;
            case R.id.sort_priority://按优先级排序
                Constants.sortBy = "priority";
                ListFragment.instance.showList();
                break;
            case R.id.sort_title://按标题排序
                Constants.sortBy = "list_title";
                ListFragment.instance.showList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
