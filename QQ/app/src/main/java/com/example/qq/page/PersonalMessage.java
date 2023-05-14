package com.example.qq.page;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qq.Client.Message;
import com.example.qq.DataBase.Bean;
import com.example.qq.QQThread.ClientConnectServerThead;
import com.example.qq.R;
import com.example.qq.ToolClass.MessageType;
import com.example.qq.ToolClass.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalMessage extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @SuppressLint("SdCardPath")
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";//temp file
    Uri imageUri_crop = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap

    private String mTempPhotoPath;
    private String mTempPhotoPath2;
    private Uri imageUri;
   private  static Message message;

    TextView textUser,textAccount,youxiang;
    RelativeLayout reUp,reMe,reHelp,reBack;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.personal_message_page);
         init();
         setButton();
    }
    private void init(){
        message= data.getMessage();
        textUser=findViewById(R.id.yonghuxingming);
        textUser.setText(message.getAccount_name());

        textAccount=findViewById(R.id.dengruyonghu);
        textAccount.setText(message.getSender());

        youxiang=findViewById(R.id.youxiang_number);
        youxiang.setText(message.getAccount_email());


        reUp=findViewById(R.id.re_xiangce);
        reMe=findViewById(R.id.re_aboutwe);
        reHelp=findViewById(R.id.re_help);
        reBack=findViewById(R.id.tuichu);
        imageView=findViewById(R.id.iv_avatar);
        Bean.setList(imageView);

    }

    private void setButton(){
        reUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeDialog();
            }
        });

        reBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //退出程序
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalMessage.this);
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(PersonalMessage.this, R.layout.dailog, null);

                //设置对话框布局
                dialog.setView(dialogView);
                dialog.show();

                Button btnEnter = (Button) dialogView.findViewById(R.id.btn_enter);
                Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cansel);

                btnEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Message message = new Message();
                        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
                        try {
                            ObjectOutputStream oos = new ObjectOutputStream(ClientConnectServerThead.getSocket().getOutputStream());
                            oos.writeObject(message);
                            oos.close();
                            Intent intent=new Intent(PersonalMessage.this,MainActivity.class);
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("选择头像");
        final String[] items = {"相册", "相机"};
        builder.setCancelable(true);
        builder.setItems(items, (dialogInterface, i) -> {
            Toast.makeText(getApplicationContext(), "You clicked " + items[i], Toast.LENGTH_SHORT).show();
            if (i == 0) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialogInterface.dismiss();
            }
            if (i == 1) {
                if (ContextCompat.checkSelfPermission(PersonalMessage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
                    // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
                    // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
                    Log.e(TAG, "onClick: " + "未授权");
                    ActivityCompat.requestPermissions(PersonalMessage.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            2);
                } else { //权限已经被授予，在这里直接写要执行的相应方法即可
                    takePhoto();
                }
                dialogInterface.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void takePhoto() {
        // 跳转到系统的拍照界面
        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定照片存储位置为sd卡本目录下
        // 这里设置为固定名字 这样就只会只有一张temp图 如果要所有中间图片都保存可以通过时间或者加其他东西设置图片的名称
        // File.separator为系统自带的分隔符 是一个固定的常量
        mTempPhotoPath = this.getExternalFilesDir(null).getAbsolutePath() + File.separator + "photo1.jpeg";//android/data 自动创建目录
        mTempPhotoPath2 = Environment.getExternalStorageDirectory() + File.separator + "photo2.jpeg";
        Log.e(TAG, "takePhoto: " + mTempPhotoPath);
        Log.e(TAG, "takePhoto2: " + mTempPhotoPath2);
        // 获取图片所在位置的Uri路径
        imageUri = FileProvider.getUriForFile(PersonalMessage.this, PersonalMessage.this.getApplicationContext().getPackageName() + ".fileprovider", new File(mTempPhotoPath2));
        Log.e(TAG, "takePhoto3: " + imageUri);
        //下面这句指定调用相机拍照后的照片存储的路径，如果没有这句代码，则不储存照片
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(intentToTakePhoto, 2);
    }

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_crop);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, 3);
    }
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    cropPhoto(uri);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    if (imageUri != null) {
                        Log.e(TAG, "onActivityResult: " + imageUri);
                        cropPhoto(imageUri);
                    }
                }
                break;
            case 3:
                if (imageUri_crop != null) {
                    Bitmap bitmap = decodeUriAsBitmap(imageUri_crop);//decode bitmap
//                    imageView.setImageBitmap(bitmap);
                    List<ImageView> list = Bean.getList();
                    for (int i = 0; i <list.size() ; i++) {
                        list.get(i).setImageBitmap(bitmap);
                    }

                }
                break;
        }
    }



}