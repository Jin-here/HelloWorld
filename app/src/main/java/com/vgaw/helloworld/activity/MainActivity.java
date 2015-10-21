package com.vgaw.helloworld.activity;

import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.vgaw.helloworld.R;
import com.vgaw.helloworld.bean.Dir;
import com.vgaw.helloworld.bean.Memo;
import com.vgaw.helloworld.bean.MemoDir;
import com.vgaw.helloworld.db.MyDbHelper;
import com.vgaw.helloworld.fragment.BottomContentFragment;
import com.vgaw.helloworld.fragment.BottomDirFragment;
import com.vgaw.helloworld.fragment.ContentFragment;
import com.vgaw.helloworld.fragment.DirFragment;
import com.vgaw.helloworld.fragment.ListRecyclerViewFragment;
import com.vgaw.helloworld.fragment.OKCancelFragment;
import com.vgaw.helloworld.fragment.TopAddToDirFragment;
import com.vgaw.helloworld.fragment.TopContentFragment;
import com.vgaw.helloworld.fragment.TopDirFragment;
import com.vgaw.helloworld.util.DateTools;
import com.vgaw.helloworld.util.TextEditor;

public class MainActivity extends FragmentActivity {
    private Memo currentMemo = new Memo();
    private Memo newMemo = new Memo();
    private String blankMemoDate;

    private FragmentManager fragmentManager;

    private TopContentFragment topContentFragment;
    private ContentFragment contentFragment;
    private BottomContentFragment bottomContentFragment;
    private TopDirFragment topDirFragment;
    private DirFragment dirFragment;
    private BottomDirFragment bottomDirFragment;
    private TopAddToDirFragment topAddToDirFragment;

    private boolean isContent = true;
    private boolean isToDir = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findAndInitialViews();


    }

    /**
     * change to the right memo
     */
    public void moveToRightMemo() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyDbHelper dbHelper = new MyDbHelper(MainActivity.this);
        Memo memo = dbHelper.getRightMemo(currentMemo.getId());
        if (memo.getId() == Memo.MAX_VALUE) {
            return;
        }
        newMemo = new Memo(memo);

        ContentFragment contentFragment1 = ContentFragment.newInstance(newMemo.getContent());
        fragmentTransaction.replace(R.id.content_fragment, contentFragment1);

        topContentFragment.updateDate(newMemo.getCreate_date());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
    }

    /**
     * change to the left memo
     */
    public void moveToLeftMemo() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyDbHelper dbHelper = new MyDbHelper(MainActivity.this);
        newMemo = dbHelper.getLeftMemo(currentMemo.getId());
        if (newMemo.getId() == Memo.MAX_VALUE) {
            // make the animation.
            ContentFragment contentFragment2 = ContentFragment.newInstance("");
            fragmentTransaction.replace(R.id.content_fragment, contentFragment2);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            blankMemoDate = DateTools.getTimeS();
            topContentFragment.updateDate(blankMemoDate);
            fragmentTransaction.commit();
            // order:currentMemo->saveorupdate,animation,refresh date,
            return;
        }

        ContentFragment contentFragment1 = ContentFragment.newInstance(newMemo.getContent());
        fragmentTransaction.replace(R.id.content_fragment, contentFragment1);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        topContentFragment.updateDate(newMemo.getCreate_date());
        fragmentTransaction.commit();
        // order:currentMemo->saveorupdate,animation,show new memo,
    }

    /**
     * save the memo.
     *
     * @param content
     */
    public void saveMemo(String content) {
        if (TextEditor.isAll32(content)) {
            currentMemo.setContent(content);
            // TODO: 15-10-13 add more info
            MyDbHelper dbHelper = new MyDbHelper(MainActivity.this);
            if (currentMemo.getId() == Memo.MAX_VALUE) {
                System.out.println("save");
                currentMemo.setCreate_date(blankMemoDate);
                dbHelper.saveMemo(currentMemo);

                if (isToDir) {
                    newMemo = dbHelper.getRightMemo(Memo.MAX_VALUE);
                }
            } else {
                System.out.println("update");
                dbHelper.updateMemo(currentMemo);

                if (isToDir) {
                    newMemo = new Memo(currentMemo);
                }
            }
        }

        // refresh the currentMemo to the newMemo.
        currentMemo = new Memo(newMemo);
    }

    // put the query date to the dirfragment.
    private void setDirAContentDate() {
        MyDbHelper dbHelper = new MyDbHelper(getBaseContext());
        dirFragment.setList(dbHelper.queryUndirMemo(), dbHelper.queryDir());
    }

    /**
     * change to dir
     */
    private void moveToDir() {
        topDirFragment = new TopDirFragment();
        dirFragment = new DirFragment();
        setDirAContentDate();

        bottomDirFragment = new BottomDirFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.top_fragment, topDirFragment);
        fragmentTransaction.replace(R.id.content_fragment, dirFragment);
        fragmentTransaction.replace(R.id.bottom_fragment, bottomDirFragment);
        fragmentTransaction.commit();
    }

    /**
     * change to content
     */
    private void moveToContent() {
        if (currentMemo.getId() == Memo.MAX_VALUE) {
            topContentFragment = TopContentFragment.newInstance(DateTools.getTimeS());
        } else {

            topContentFragment = TopContentFragment.newInstance(currentMemo.getCreate_date());
        }
        contentFragment = ContentFragment.newInstance(currentMemo.getContent());
        bottomContentFragment = new BottomContentFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.top_fragment, topContentFragment);
        fragmentTransaction.replace(R.id.content_fragment, contentFragment);
        fragmentTransaction.replace(R.id.bottom_fragment, bottomContentFragment);
        fragmentTransaction.commit();
    }

    /**
     * change between dir and content
     */
    public void changeBT() {
        if (isContent) {
            moveToDir();
            isContent = false;

            isToDir = true;
        } else {
            moveToContent();
            isContent = true;

            isToDir = false;
        }
    }

    /**
     * do something little
     */
    public void weiTiao() {
        setDirAContentDate();
    }

    /**
     * add the current memo to a current dir or a new dir.
     */
    public void addToDir() {
        topAddToDirFragment = new TopAddToDirFragment();

        ListRecyclerViewFragment listRecyclerViewFragment = new ListRecyclerViewFragment();
        MyDbHelper helper = new MyDbHelper(getBaseContext());
        listRecyclerViewFragment.setList(helper.queryDirByKeyWord(""));

        OKCancelFragment okCancelFragment = new OKCancelFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.top_fragment, topAddToDirFragment);
        fragmentTransaction.replace(R.id.content_fragment, listRecyclerViewFragment);
        fragmentTransaction.replace(R.id.bottom_fragment, okCancelFragment);
        fragmentTransaction.commit();

    }

    public void addToDirOk() {
        // query dir_table if the dirname exists,
        // if yes (update the has_file to 1) and (add info to memodir_table),
        // if no also need to (add new dir to dir_table).
        String keyWord = topAddToDirFragment.getKeyWord();
        // TODO: 2015/10/18 if the keyword is not valid, give hint to user
        MyDbHelper helper = new MyDbHelper(getBaseContext());
        Dir dir = helper.queryDirByName(keyWord);
        int dirId;
        if (dir == null){
            Dir dir1 = new Dir();
            dir1.setName(keyWord);
            dir1.setCount(0);
            dir1.setId(helper.addDir(dir1));
            helper.dirCountPlus(dir1);
            dirId = dir1.getId();
        }else{
            helper.dirCountPlus(dir);
            dirId = dir.getId();
        }

        System.out.println(currentMemo);
        helper.hasFile(currentMemo);

        MemoDir memoDir = new MemoDir();
        memoDir.setMemo_id(currentMemo.getId());
        memoDir.setDir_id(dirId);
        helper.addMemoDir(memoDir);
    }

    public void addToDirCancel() {

    }

    private void findAndInitialViews() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 19) {
            enableTint();
        }

        fragmentManager = getSupportFragmentManager();
        blankMemoDate = DateTools.getTimeS();
        topContentFragment = TopContentFragment.newInstance(blankMemoDate);
        contentFragment = ContentFragment.newInstance("");
        bottomContentFragment = new BottomContentFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.top_fragment, topContentFragment, TopContentFragment.TAG);
        fragmentTransaction.add(R.id.content_fragment, contentFragment, ContentFragment.TAG);
        fragmentTransaction.add(R.id.bottom_fragment, bottomContentFragment, BottomContentFragment.TAG);
        fragmentTransaction.commit();
    }

    private void enableTint() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);

        // set a custom tint color for all system bars
        tintManager.setTintColor(Color.parseColor("#ff0000"));
        /*// set a custom navigation bar color
        tintManager.setNavigationBarTintColor(Color.parseColor("#00ff00"));
        // set a custom status bar color
        tintManager.setStatusBarTintColor();*/
    }

}
