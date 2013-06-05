package com.sasedeve.tiles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;


public class Wal {
	
	private final Context context;
	private int mMVPMatrixHandle;
	private int mPositionHandle;
	private int mMVPMatrixHandle1;
	private int mPositionHandle1;
	
	private final int mPositionDataSize = 3;
	private final FloatBuffer mWallVertices,mWallVertices1;
	private final int mBytesPerFloat = 4;
	private final FloatBuffer texturebuffer,texturebuffer1;
	private int mTextureHandle,mTextureHandle1;
	//here i have divide two texturevertices
	private final float[] textureVertices = {
            
			0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            0.0f,0.0f,
            1.0f, 1.0f,
            1.0f,0.0f,
           
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            0.0f,0.0f,
            1.0f, 1.0f,
            1.0f,0.0f
            
            		
};
	private final float[] textureVertices1 = {
			0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            0.0f,0.0f,
            1.0f, 1.0f,
            1.0f,0.0f,
            
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            0.0f,0.0f,
            1.0f, 1.0f,
            1.0f,0.0f};
	//here two wallvertices
	private final float[] wallVerticesData1={
			-1,0,0,
			0,0,0,
			-1,1,0,
			-1,1,0,
			0,0,0,
			0,1,0,
			//4th
			0,0,0,
			1,0,0,
			0,1,0,
			0,1,0,
			1,0,0,
			1,1,0 };
	private final float[] wallVerticesData = {
			 //one part
			-1.0f, -1.0f, 0.0f,
			0.0f, -1.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			0,-1f, 0.0f,
			0,0,0,
			//2nd
			0,-1,0,
			1,-1,0,
			0,0,0,
			0,0,0,
			1,-1,0,
			1,0,0
			
};
	
	
	int size,size1;
	//i think i have to change some thing in vertex shader and fragment shader which is written in native code
	final String vertexShader =
			"uniform mat4 u_MVPMatrix; \n"	

			+ "attribute vec4 a_Position; \n"	
			+ "//attribute vec4 a_Color; \n"	
			+ "attribute vec2 a_TexCoordinate;  \n"
			+ "//varying vec4 v_Color; \n"	
			+ "varying vec2 v_TexCoordinate;     \n"
			+ "void main() \n"
			+ "{ \n"
			+ "  v_TexCoordinate = a_TexCoordinate;  \n"
			+ "// v_Color = a_Color; \n"	
			+ " gl_Position = u_MVPMatrix \n" 
			+ " * a_Position; \n" 
			+ "} \n"; 

	final String fragmentShader =
			"precision mediump float; \n"
	        +   "uniform sampler2D u_Texture;   \n"
			
			+ "//varying vec4 v_Color; \n"
			 +   "varying vec2 v_TexCoordinate;  \n"
			+ "void main() \n"	
			+ "{ \n"
			 +   "   vec4 baseColor; \n"
	        +   "   baseColor = texture2D(u_Texture, v_TexCoordinate);  \n"
			 
	     +   "   gl_FragColor = baseColor;   \n"	
			+ "} \n";
	final String vertexShader1 =
			"uniform mat4 u_MVPMatrix; \n"	

			+ "attribute vec4 a_Position; \n"	
			+ "//attribute vec4 a_Color; \n"	
			+ "attribute vec2 a_TexCoordinate;  \n"
			+ "//varying vec4 v_Color; \n"	
			+ "varying vec2 v_TexCoordinate;     \n"
			+ "void main() \n"
			+ "{ \n"
			+ "  v_TexCoordinate = a_TexCoordinate;  \n"
			+ "// v_Color = a_Color; \n"	
			+ " gl_Position = u_MVPMatrix \n" 
			+ " * a_Position; \n" 
			+ "} \n"; 

	final String fragmentShader1 =
			"precision mediump float; \n"
	        +   "uniform sampler2D u_Texture;   \n"
			
			+ "//varying vec4 v_Color; \n"
			 +   "varying vec2 v_TexCoordinate;  \n"
			+ "void main() \n"	
			+ "{ \n"
			 +   "   vec4 baseColor; \n"
	        +   "   baseColor = texture2D(u_Texture, v_TexCoordinate);      \n"
			 
	     +   "   gl_FragColor = baseColor;   \n"	
			+ "} \n";
			
			
	public Wal(Context context,float s){
				this.context=context;
				
				size=wallVerticesData.length/3;
				size1=wallVerticesData1.length/3;
				mWallVertices = ByteBuffer.allocateDirect(wallVerticesData.length * mBytesPerFloat)
				        .order(ByteOrder.nativeOrder()).asFloatBuffer();
				mWallVertices.put(wallVerticesData).position(0);
				mWallVertices1 = ByteBuffer.allocateDirect(wallVerticesData1.length * mBytesPerFloat)
				        .order(ByteOrder.nativeOrder()).asFloatBuffer();
				mWallVertices1.put(wallVerticesData1).position(0);
				
				texturebuffer=ByteBuffer.allocateDirect(textureVertices.length * mBytesPerFloat)
				        .order(ByteOrder.nativeOrder()).asFloatBuffer();
				texturebuffer.put(textureVertices).position(0);
				texturebuffer1=ByteBuffer.allocateDirect(textureVertices1.length * mBytesPerFloat)
				        .order(ByteOrder.nativeOrder()).asFloatBuffer();
				texturebuffer1.put(textureVertices1).position(0);
	
				
			int vertexShaderHandle=loadShader(GLES20.GL_VERTEX_SHADER,vertexShader);	
			int fragmentShaderHandle=loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShader);
			
			int programHandle = GLES20.glCreateProgram();
			

				if (programHandle != 0)
				{
				GLES20.glAttachShader(programHandle, vertexShaderHandle);	
				GLES20.glAttachShader(programHandle, fragmentShaderHandle);
				GLES20.glUseProgram(programHandle);
			    GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
				GLES20.glBindAttribLocation(programHandle, 1, "a_TexCoordinate");
				GLES20.glLinkProgram(programHandle);
			final int[] linkStatus = new int[1];
				GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

				if (linkStatus[0] == 0)
				{	
				GLES20.glDeleteProgram(programHandle);
				programHandle = 0;
				}
				}
				int vertexShaderHandle1=loadShader(GLES20.GL_VERTEX_SHADER,vertexShader1);	
				int fragmentShaderHandle1=loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShader1);
				int programHandle1 = GLES20.glCreateProgram();
				if (programHandle1 != 0)
				{
				GLES20.glAttachShader(programHandle1, vertexShaderHandle1);	
				GLES20.glAttachShader(programHandle1, fragmentShaderHandle1);
				GLES20.glUseProgram(programHandle1);
			    GLES20.glBindAttribLocation(programHandle1, 0, "a_Position");
				GLES20.glBindAttribLocation(programHandle1, 1, "a_TexCoordinate");
				GLES20.glLinkProgram(programHandle1);
			final int[] linkStatus = new int[1];
				GLES20.glGetProgramiv(programHandle1, GLES20.GL_LINK_STATUS, linkStatus, 0);

				if (linkStatus[0] == 0)
				{	
				GLES20.glDeleteProgram(programHandle1);
				programHandle1 = 0;
				}
				}
				if (programHandle == 0 || programHandle1==0)
				{
				throw new RuntimeException("Error creating program.");
				}
				        
				        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
				        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
				        mTextureHandle=GLES20.glGetAttribLocation(programHandle, "a_TexCoordinate");
				        GLES20.glUseProgram(programHandle);
				        mMVPMatrixHandle1 = GLES20.glGetUniformLocation(programHandle1, "u_MVPMatrix");
				        mPositionHandle1 = GLES20.glGetAttribLocation(programHandle1, "a_Position");
				        mTextureHandle1=GLES20.glGetAttribLocation(programHandle1, "a_TexCoordinate");
				       
				        GLES20.glUseProgram(programHandle1);
				}
	public int loadShader(int type,String shadercode){
		int shader=GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, shadercode);
		GLES20.glCompileShader(shader);
		if (shader == 0)
		{
		throw new RuntimeException("Error creating Shader.");
		}
		return shader;
	}
    public void drawWall(final float[] mMVPMatrix,int i)
			{	  
    	
    		
    		mTextureHandle = loadGLTexture(context, R.drawable.h);	
    		
    		
		
			        mWallVertices.position(0);
			        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,0, mWallVertices);
			        GLES20.glEnableVertexAttribArray(mPositionHandle);
			        
			     
			        //texture1
			        texturebuffer.position(0);
			        GLES20.glVertexAttribPointer(mTextureHandle,2,GLES20.GL_FLOAT,false,0,texturebuffer);
			        GLES20.glEnableVertexAttribArray(mTextureHandle);
			        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
			        //drawing one part
			        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, size);
			       
			        mTextureHandle1 = loadGLTexture(context, R.drawable.as);
			         //GLES20.glDisableVertexAttribArray(mPositionHandle);
			        //GLES20.glDisableVertexAttribArray(mTextureHandle);
			        mWallVertices1.position(0);
			        GLES20.glVertexAttribPointer(mPositionHandle1, mPositionDataSize, GLES20.GL_FLOAT, false,0, mWallVertices1);
			        //GLES20.glDisableVertexAttribArray(mPositionHandle1);
			        GLES20.glEnableVertexAttribArray(mPositionHandle1);
			       //texture2
			        texturebuffer1.position(0);
			        GLES20.glVertexAttribPointer(mTextureHandle1,2,GLES20.GL_FLOAT,false,0,texturebuffer1);
			        GLES20.glDisableVertexAttribArray(mTextureHandle1);
			        GLES20.glEnableVertexAttribArray(mTextureHandle1);
			        GLES20.glUniformMatrix4fv(mMVPMatrixHandle1, 1, false, mMVPMatrix, 0);
			        //drawing another part
			        
			        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, size1);
			 }
    private int loadGLTexture(Context context, final int resourceId)
		{
    	
		  final int[] textureHandle = new int[1];
		  GLES20.glGenTextures(1, textureHandle, 0);
		  if (textureHandle[0] != 0)
			   {
			  
			     final BitmapFactory.Options options = new BitmapFactory.Options();
			     options.inScaled = false;  
			     final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
			    // GLES20.glActiveTexture(GLES20.GL_TEXTURE1 );
			     GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
			     GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			     GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
			     GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
			     bitmap.recycle();
			    }
			    if (textureHandle[0] == 0)
			    {
			        throw new RuntimeException("Error loading texture.");
			    }

			    return textureHandle[0];
			}
   
}