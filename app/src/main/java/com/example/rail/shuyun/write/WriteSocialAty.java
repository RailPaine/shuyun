package com.example.rail.shuyun.write;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rail.shuyun.R;
import com.example.rail.shuyun.SocialShareAty;
import com.example.rail.shuyun.entity.BookMessageDetail;
import com.example.rail.shuyun.entity.Person;
import com.example.rail.shuyun.entity.Post;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import util.ImageUtils;

/**
 * Created by qq on 2016/7/12.
 */
public class WriteSocialAty extends Activity {

    private ImageButton title_imageBtn;
    private EditText content;
    private ImageView imageView1;
    private ImageView deleteImage1;
    private Button SureBtn;

    private String imageUrl;

    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_social);
        title_imageBtn = (ImageButton) findViewById(R.id.title_leftImageBtn);
        content = (EditText) findViewById(R.id.write_social_content);
        imageView1 = (ImageView) findViewById(R.id.iv_image);
        deleteImage1 = (ImageView) findViewById(R.id.iv_delete_image);
        SureBtn = (Button) findViewById(R.id.write_social_btn);
        bundle = getIntent().getExtras();
        initListener();

    }

    private void initListener() {
        deleteImage1.setVisibility(View.GONE);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtils.showImagePickDialog(WriteSocialAty.this);
            }
        });
        deleteImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.setImageResource(R.drawable.compose_pic_add_more);
                imageUrl = "";
            }
        });

        title_imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(content.getText().toString()) && !TextUtils.isEmpty(imageUrl)) {
                    Person person = BmobUser.getCurrentUser(Person.class);
                    final Post post = new Post();
                    post.setAuthor(person);
                    post.setContent(content.getText().toString());
                    BookMessageDetail bookMessageDetail = new BookMessageDetail();
                    bookMessageDetail.setObjectId(bundle.getString("bookId"));
                    post.setBook(bookMessageDetail);
                    //文件区域
                    File file = new File(imageUrl);
                    if (file.exists()) {
                        Log.i("TestWriteSocialAty", "文件存在" + file.getName() + ";;" + file.getAbsolutePath());
                    } else {
                        Log.i("TestWriteSocialAty", "文件不存在啊");
                    }
                    final BmobFile bmobFile = new BmobFile(file);
                    bmobFile.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                post.setPostImage(bmobFile);
                                post.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        Log.i("TestWriteSocialAty", "处理完成1" + e.toString());
                                        if (e == null) {
                                            Toast.makeText(WriteSocialAty.this, "数据新建成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(WriteSocialAty.this, SocialShareAty.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(WriteSocialAty.this, "数据新建失败", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(WriteSocialAty.this, SocialShareAty.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        }
                    });




                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_FROM_ALBUM:
                if (resultCode == this.RESULT_CANCELED) {
                    Log.i("ImagePICTURE", "fail");
                    return;
                }
                Uri imageUri = data.getData();

                String img_path = ImageUtils.getImageAbsolutePath19(this, imageUri);
                Log.i("ImagePICTURE", imageUri.toString());
                //进行压缩图片操作
                Bitmap bitmap1 = getimage(img_path);
                imageView1.setImageBitmap(bitmap1);

                //将图片转化为uri
                Uri uri1 = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap1, null, null));

                imageUrl = ImageUtils.getImageAbsolutePath19(this, uri1);


                updateImgs();
                break;
            case ImageUtils.REQUEST_CODE_FROM_CAMERA:
                if (resultCode == this.RESULT_CANCELED) {
                    ImageUtils.deleteImageUri(this, ImageUtils.imageUriFromCamera);
                    Log.i("ImagePICTURE", "fail");
                } else {
                    Uri imageUriCamera = ImageUtils.imageUriFromCamera;

                    String img_path1 = ImageUtils.getImageAbsolutePath19(this, imageUriCamera);
                    Log.i("ImagePICTURE", imageUriCamera.toString());
                    //进行压缩图片操作
                    Bitmap temp_bitmap = getimage(img_path1);
                    imageView1.setImageBitmap(temp_bitmap);

                    //将图片转化为uri
                    Uri temp_url = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), temp_bitmap, null, null));
                    imageUrl = ImageUtils.getImageAbsolutePath19(this, temp_url);
                    updateImgs();
                }
                break;

            default:
                break;
        }
    }

    private Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    private void updateImgs() {
        if (!TextUtils.isEmpty(imageUrl)) {
            deleteImage1.setVisibility(View.VISIBLE);
        } else {
            deleteImage1.setVisibility(View.GONE);
        }
    }
}
