package com.sasedeve.tiles;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private MyGLSurfaceView mGLView;
	private WallRenderer Rendererwall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        mGLView = (MyGLSurfaceView)findViewById(R.id.gl_surface_view);
        //mGLView.setDebugFlags(GLSurfaceView.DEBUG_LOG_GL_CALLS | GLSurfaceView.DEBUG_CHECK_GL_ERROR);
        mGLView.setEGLContextClientVersion(2);
        Rendererwall=new WallRenderer(this);
        mGLView.setRenderer(Rendererwall);
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        
        /*mGLView.getHolder();
        mGLView.setFixedSize(200,200 );
        mGLView.setEGLContextClientVersion(2);
        mGLView.setRenderer(new WallRenderer(this));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(mGLView);*/
    }
    @Override
    protected void onPause() {
        super.onPause();
        
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
class MyGLSurfaceView extends GLSurfaceView {

    public MyGLSurfaceView(Context context){
        super(context);
        
    }
    public MyGLSurfaceView(Context context,AttributeSet attrs){
    	super(context, attrs);
    }
    public void setRenderer(WallRenderer renderer)
    {
   
    super.setRenderer(renderer);
    }
    
    }

