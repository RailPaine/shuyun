package listviewForShare;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rail.shuyun.R;
import com.example.rail.shuyun.resourceShare;
import com.gc.materialdesign.views.ProgressBarDeterminate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;


/**
 * Created by rail on 2016/7/8.
 */
public class MyShareItemAdapter extends BaseAdapter {

    private static final int TYPE_FILE=0;
    private static final int TYPE_LINK=1;

    private LayoutInflater inflater;
    private ArrayList<shareItem>list;
    private Context context;

    public MyShareItemAdapter(Context context,ArrayList<shareItem> list) {
        this.list = list;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final shareItem shareItem=list.get(position);
        int type=shareItem.getType();
        ViewHolderFile viewHolderFile=null;
        ViewHolderLink viewHolderLink=null;
        if(convertView==null)
        {
            switch(type)
            {
                case TYPE_FILE:
                    viewHolderFile=new ViewHolderFile();
                    convertView=inflater.inflate(R.layout.list_item_file,null);
                    viewHolderFile.download=(Button)convertView.findViewById(R.id.share_file_download);
                    viewHolderFile.imgForFile=(ImageView)convertView.findViewById(R.id.share_file_image);
                    viewHolderFile.titleForFile=(TextView)convertView.findViewById(R.id.share_file_title);
                    viewHolderFile.contentForFile=(TextView)convertView.findViewById(R.id.share_file_content);
                    viewHolderFile.imgForFile.setImageResource(shareItem.getImgId());
                    viewHolderFile.titleForFile.setText(shareItem.getTitle());
                    viewHolderFile.contentForFile.setText(shareItem.getContent());
                    //此处给点击按钮下载的代码
                    viewHolderFile.download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BmobFile bmobFile=new BmobFile(shareItem.getContent(),"",shareItem.getContentPath());
                            File path=new File(Environment.getExternalStorageDirectory()+"/shuyun");
                            if (!path.exists())
                            {
                                path.mkdir();
                            }
                            File file=new File(path,bmobFile.getFilename());
                            final ProgressDialog progressDialog=new ProgressDialog(context);
                            progressDialog.setIndeterminate(false);
                            progressDialog.setMax(100);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setTitle("下载进度");
                            progressDialog.show();

                            bmobFile.download(file,new DownloadFileListener() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null)
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(context,"下载成功！保存路径为："+s,Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        System.out.println("fail");
                                    }
                                }

                                @Override
                                public void onProgress(Integer integer, long l) {
                                    int progress=integer.intValue();
                                  progressDialog.setProgress(progress);
                                   progressDialog.setMessage("已下载"+progress+"%");
                                }
                            });
                        }
                    });
                    convertView.setTag(viewHolderFile);
                    break;
                case TYPE_LINK:
                    viewHolderLink=new ViewHolderLink();
                    convertView=inflater.inflate(R.layout.list_item_link,null);
                    viewHolderLink.imgForLink=(ImageView)convertView.findViewById(R.id.share_link_image);
                    viewHolderLink.titleForLink=(TextView)convertView.findViewById(R.id.share_link_title);
                    viewHolderLink.contentForLink=(TextView)convertView.findViewById(R.id.share_link_content);
                    viewHolderLink.linkPath=(TextView)convertView.findViewById(R.id.share_link);
                    viewHolderLink.imgForLink.setImageResource(shareItem.getImgId());
                    viewHolderLink.titleForLink.setText(shareItem.getTitle());
                    viewHolderLink.contentForLink.setText(shareItem.getContent());

                    viewHolderLink.linkPath.setAutoLinkMask(Linkify.WEB_URLS);
                    viewHolderLink.linkPath.setMovementMethod(LinkMovementMethod.getInstance());
                    viewHolderLink.linkPath.setText(shareItem.getContentPath());
                    convertView.setTag(viewHolderLink);
                    break;
                default:
                    break;
            }

        }
        else
        {
            switch (type)
            {
                case TYPE_FILE:
                    viewHolderFile=(ViewHolderFile)convertView.getTag();
                    viewHolderFile.imgForFile.setImageResource(shareItem.getImgId());
                    viewHolderFile.titleForFile.setText(shareItem.getTitle());
                    viewHolderFile.contentForFile.setText(shareItem.getContent());
                    //此处给点击按钮下载的代码
                    viewHolderFile.download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BmobFile bmobFile=new BmobFile(shareItem.getContent(),"",shareItem.getContentPath());
                            File path=new File(Environment.getExternalStorageDirectory()+"/shuyun");
                            if (!path.exists())
                            {
                                path.mkdir();
                            }
                            File file=new File(path,bmobFile.getFilename());
                            final ProgressDialog progressDialog=new ProgressDialog(context);
                            progressDialog.setIndeterminate(false);
                            progressDialog.setMax(100);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setTitle("下载进度");
                            progressDialog.show();
                            bmobFile.download(file,new DownloadFileListener() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null)
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(context,"下载成功！保存路径为："+s,Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        System.out.println("fail");
                                        System.out.println(e.getErrorCode()+e.getMessage());
                                    }
                                }

                                @Override
                                public void onProgress(Integer integer, long l) {
                                    int progress=integer.intValue();
                                    progressDialog.setProgress(progress);
                                    progressDialog.setMessage("已下载"+progress+"%");
                                }
                            });
                        }
                    });
                    break;
                case TYPE_LINK:
                    viewHolderLink=(ViewHolderLink)convertView.getTag();
                    viewHolderLink.imgForLink.setImageResource(shareItem.getImgId());
                    viewHolderLink.titleForLink.setText(shareItem.getTitle());
                    viewHolderLink.contentForLink.setText(shareItem.getContent());

                    viewHolderLink.linkPath.setAutoLinkMask(Linkify.WEB_URLS);
                    viewHolderLink.linkPath.setMovementMethod(LinkMovementMethod.getInstance());
                    viewHolderLink.linkPath.setText(shareItem.getContentPath());
                    break;
                default:
                    break;
            }
        }
        return convertView;
    }

    class ViewHolderFile
    {
        private ImageView imgForFile;
        private TextView titleForFile;
        private TextView contentForFile;
        private Button download;

    }

    class ViewHolderLink
    {
        private ImageView imgForLink;
        private  TextView titleForLink;
        private TextView contentForLink;
        private TextView linkPath;

    }
}
