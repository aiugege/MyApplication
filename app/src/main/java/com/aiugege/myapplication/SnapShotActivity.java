package com.aiugege.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aiugege.myapplication.util.FileService;

/**
 * 获取webView快照与屏幕的截屏
 * @author ayj
 *
 */
public class SnapShotActivity extends AppCompatActivity {
	
    private Bitmap bmp = null;
    private WebView webView = null;
    private ImageView image = null;
    private FileService fileService = null;
    private static final String TAG = "SnapShotActivity";
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }
        LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(this, R.layout.activity_snap_shot, null);
        setContentView(linearLayout);
        //初始化view
        Button button1 = (Button) findViewById(R.id.btn01);
        Button button2 = (Button) findViewById(R.id.btn02);
        Button button3 = (Button) findViewById(R.id.btn03);
        image = (ImageView) findViewById(R.id.imageView);

        webView = (WebView)findViewById(R.id.webview);
        webView.setInitialScale(1);
        //全屏显示
        webView.getSettings().setLoadWithOverviewMode(true);
        // 开启缩放控件的缩放
        webView.getSettings().setSupportZoom(true);
        //		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        //自动适应屏幕，支持两手滑动缩放
        webView.getSettings().setUseWideViewPort(true);

        //开启缩放控件
        webView.getSettings().setBuiltInZoomControls(true);

        // 支持多窗口
        webView.getSettings().setSupportMultipleWindows(true);
        // webView设置启用JavaScript.
        webView.getSettings().setJavaScriptEnabled(true);


        //开启自动加载图片
        webView.getSettings().setLoadsImagesAutomatically(true);
        //显示网络图片
        webView.getSettings().setBlockNetworkImage(false);
        //渲染优先级
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 默认字体大小
        //		webView.getSettings().setDefaultFontSize(50);
        //支持手势焦点
        webView.requestFocusFromTouch();
        //隐藏滚动条
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // 支持插件
        //webView.getSettings().setPluginsEnabled(true);

        //设置缓冲大小，我设的是8M
        webView.getSettings().setAppCacheMaxSize(1024*1024*8);
        String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webView.getSettings().setAppCachePath(appCacheDir);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        //启用数据库
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setGeolocationDatabasePath(this.getFilesDir().getPath());
        //启用地理定位
        webView.getSettings().setGeolocationEnabled(true);
        //设置定位的数据库路径
        // 设置可以使用localStorage
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setDrawingCacheEnabled(true);
        webView.loadUrl("https://www.baidu.com/");

        
//        webView.setVisibility(View.INVISIBLE);
        fileService = new FileService(this);

        //获取webView快照
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        webView.post(new Runnable() {
                            @Override
                            public void run() {
                                bmp = captureWebView(webView);

                                System.out.println("bmpWidth="+bmp.getWidth());
                                System.out.println("bmpHeight="+bmp.getHeight());

                                image.setBackgroundDrawable(new BitmapDrawable(bmp));

                                Log.i(TAG, "获取快照");

                                String fileName = fileService.saveBitmapToSDCard("" + System.currentTimeMillis()+".png", bmp);
                                Toast.makeText(getApplicationContext(), "文件" + fileName + "保存成功！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }.start();



            }
        });
        
        //获取截屏
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	bmp = captureScreen(SnapShotActivity.this);
                
                System.out.println("bmpWidth="+bmp.getWidth());
                System.out.println("bmpHeight="+bmp.getHeight());
                
                image.setBackgroundDrawable(new BitmapDrawable(bmp));
                
                Log.i(TAG, "获取截屏");
                
                String fileName = fileService.saveBitmapToSDCard("" + System.currentTimeMillis()+".png", bmp);
                Toast.makeText(getApplicationContext(), "文件" + fileName + "保存成功！", Toast.LENGTH_SHORT).show();
            }
        });
        
        //获取webView显示区域的截图
        button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	bmp = captureWebViewVisibleSize(webView);
                
                System.out.println("bmpWidth="+bmp.getWidth());
                System.out.println("bmpHeight="+bmp.getHeight());
                
                image.setBackgroundDrawable(new BitmapDrawable(bmp));
                
                Log.i(TAG, "获取webView显示区域的截图");
                
                String fileName = fileService.saveBitmapToSDCard("" + System.currentTimeMillis()+".png", bmp);
                Toast.makeText(getApplicationContext(), "文件" + fileName + "保存成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    /**
     * 截取webView可视区域的截图
     * @param webView 前提：WebView要设置webView.setDrawingCacheEnabled(true);
     * @return
     */
	private Bitmap captureWebViewVisibleSize(WebView webView){
		Bitmap bmp = webView.getDrawingCache();
		return bmp;

//        webView.setDrawingCacheEnabled(true);
//        int viewWidth = webView.getContentHeight();
//        int viewHeight = webView.getContentHeight();
//        if (viewWidth > 0 && viewHeight > 0) {
//            Bitmap b = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
//            Canvas cvs = new Canvas(b);
//            webView.draw(cvs);
//            return b;
//        }
//        return null;
	}
    
	/**
	 * 截取webView快照(webView加载的整个内容的大小)
	 * @param webView
	 * @return
	 */
	private Bitmap captureWebView(WebView webView){
/*
            问题：android 5.0+ 截屏超出屏幕的部分空白
         */
//		Picture snapShot = webView.capturePicture();
//		Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),snapShot.getHeight(), Bitmap.Config.ARGB_8888);
//		Canvas canvas = new Canvas(bmp);
//		snapShot.draw(canvas);
//		return bmp;

        /*
            问题：截屏之后 webview 不能滑动
         */
        // WebView 生成长图，也就是超过一屏的图片，代码中的 longImage 就是最后生成的长图
//        webView.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED),
//                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//        webView.layout(0, 0, webView.getMeasuredWidth(), webView.getMeasuredHeight());
//        webView.setDrawingCacheEnabled(true);
//        webView.buildDrawingCache();
//        Bitmap longImage = Bitmap.createBitmap(webView.getMeasuredWidth(),
//                webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(longImage);	// 画布的宽高和 WebView 的网页保持一致
//        Paint paint = new Paint();
//        canvas.drawBitmap(longImage, 0, webView.getMeasuredHeight(), paint);
//        webView.draw(canvas);
//        return longImage;

        try {
            //获取webview缩放率
            float scale = webView.getScale();
            //得到缩放后webview内容的高度
            int webViewHeight = (int) (webView.getContentHeight()*scale + 0.5);
            Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(),webViewHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            //绘制
            webView.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 截屏
	 * @param context
	 * @return
	 */
	private Bitmap captureScreen(Activity context){
      View cv = context.getWindow().getDecorView();
      Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(),Config.ARGB_8888);
      Canvas canvas = new Canvas(bmp);
      cv.draw(canvas);
      return bmp;
	}
	
	/**
	 * 回收图片
	 */
	public void destoryBitmap(){
	    if((null != bmp) && (!bmp.isRecycled())){
	    	bmp.recycle();  
	    	System.out.println("回收图片！");
	    }
	    	
	}
	
	protected void onDestroy() {
		super.onDestroy();
		destoryBitmap();
	}
	
}