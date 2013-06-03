package com.sasedeve.tiles;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;

public class WallRenderer implements Renderer {
	private Context context;
	private float[] mModelMatrix = new float[16];
	private float[] mProjectionMatrix = new float[16];
	private float[] mViewMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	ExampleWall wall;
	//Wal wall;	
 public WallRenderer(Context context){
		this.context=context;
		
	}
	@Override
	public void onDrawFrame(GL10 gl) {
		
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);	
		final float eyeX = 0.0f;
		final float eyeY = 1.0f;
		final float eyeZ = 4.0f;
		
		final float lookX = 0.0f;
		final float lookY = 0.0f;
		final float lookZ = -5.0f;

		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;
		
		
		
		
		    Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
		    Matrix.setIdentityM(mModelMatrix, 0);
	        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
	        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
	       
	        wall.drawWall(mMVPMatrix,5);
	        
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
	
		GLES20.glViewport(0, 0, width, height);

		final float ratio = (float) width / height;
		final float left =-ratio;
		final float right =ratio;
		final float bottom = -0.92f;
		final float top = 0.45f;
		final float near = 1.0f;
		final float far = 5.0f;

		Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		   
		
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
		wall=new ExampleWall(context,4);
		//wall=new Wal(context, 4);
		
	}

}

	
	
	
	
