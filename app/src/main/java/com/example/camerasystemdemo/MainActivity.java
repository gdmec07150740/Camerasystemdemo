package com.example.camerasystemdemo;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //定义保存的路径
    private String path;
    private ImageView iv;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"open");
        menu.add(0,2,0,"finish");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                //设置一个信使，调用相机
                Intent intent=new Intent();
                intent.setAction("android.media.action/STILL_IMAGE_CAMERA");
                startActivity(intent);
                break;
            case 2:
                takephoto();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void takephoto() {
        path=android.os.Environment.getExternalStorageDirectory().getPath();
        iv= (ImageView) this.findViewById(R.id.iv);
        //以当前时间作为相片的名字，其中yyyy表示年，mm表示月，dd表示日，hh表示时，mm表示分，ss表示秒
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
        //获取当前的系统时间，以便图片的命名
        Date curDate=new Date(System.currentTimeMillis());
        String str=sdf.format(curDate);
        path=path+"/"+str+".jpg";
        //创建文件
        file=new File(path);
        Uri uri= Uri.fromFile(file);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        //打开系统相机
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (file.exists()){
            iv.setImageURI(Uri.fromFile(file));
        }
    }
}
