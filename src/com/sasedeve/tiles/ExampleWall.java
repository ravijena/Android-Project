package com.sasedeve.tiles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class ExampleWall {
	private final Context context;
	private int mMVPMatrixHandle[];
	private int mPositionHandle[];
	

	
	private final int mPositionDataSize = 3;
	private final FloatBuffer mWallVertices[];
	private final int mBytesPerFloat = 4;
	private final FloatBuffer texturebuffer[];
	private int mTextureHandle[];
	//here i have divide two texturevertices
	private final float[][] textureVerticess=new float[2][];
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
            1.0f,0.0f};
	/*private final float[] textureVertices1 = {
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
            1.0f,0.0f};*/
	//here two wallvertices
	private final float[][] wallVerticesData=new float[2][];
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
	private final float[] wallVerticesData2 = {
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
	

			
			
	public ExampleWall(Context context,float s){
		 textureVerticess[0]=textureVertices;
		 textureVerticess[1]=textureVertices;
		 wallVerticesData[0]=wallVerticesData1;
		 wallVerticesData[1]=wallVerticesData2;
				this.context=context;
				
				size=wallVerticesData[0].length/3;
				size1=wallVerticesData[1].length/3;
				
				mWallVertices=new FloatBuffer[2];
				texturebuffer=new FloatBuffer[2];
				mMVPMatrixHandle=new int[2];
				mPositionHandle=new int[2];
				mTextureHandle=new int[2];	
				//int programHandle[]=new int[2];
				int programHandle=0;
				int vertexShaderHandle=loadShader(GLES20.GL_VERTEX_SHADER,vertexShader);	
				int fragmentShaderHandle=loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShader);
				for(int h=0;h<2;h++)
				{
				mWallVertices[h] = ByteBuffer.allocateDirect(wallVerticesData[h].length * mBytesPerFloat)
				        .order(ByteOrder.nativeOrder()).asFloatBuffer();
				mWallVertices[h].put(wallVerticesData[h]).position(0);
				/*mWallVertices[1] = ByteBuffer.allocateDirect(wallVerticesData1.length * mBytesPerFloat)
				        .order(ByteOrder.nativeOrder()).asFloatBuffer();
				mWallVertices[1].put(wallVerticesData1).position(0);*/
				
				
				texturebuffer[h]=ByteBuffer.allocateDirect(textureVerticess[h].length * mBytesPerFloat)
				        .order(ByteOrder.nativeOrder()).asFloatBuffer();
				texturebuffer[h].put(textureVerticess[h]).position(0);
				/*texturebuffer[1]=ByteBuffer.allocateDirect(textureVerticess[1].length * mBytesPerFloat)
				        .order(ByteOrder.nativeOrder()).asFloatBuffer();
				texturebuffer[1].put(textureVerticess[1]).position(0);*/
				
				
			/*int vertexShaderHandle=loadShader(GLES20.GL_VERTEX_SHADER,vertexShader);	
			int fragmentShaderHandle=loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShader);*/
			
			programHandle = GLES20.glCreateProgram();
			

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
				/*int vertexShaderHandle1=loadShader(GLES20.GL_VERTEX_SHADER,vertexShader);	
				int fragmentShaderHandle1=loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShader);
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
				}*/
				if (programHandle == 0)
				{
				throw new RuntimeException("Error creating program.");
				}
				 
				        mMVPMatrixHandle[h] = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
				        mPositionHandle[h] = GLES20.glGetAttribLocation(programHandle, "a_Position");
				        mTextureHandle[h]=GLES20.glGetAttribLocation(programHandle, "a_TexCoordinate");
				        GLES20.glUseProgram(programHandle);
				        
				        /*mMVPMatrixHandle[1] = GLES20.glGetUniformLocation(programHandle1, "u_MVPMatrix");
				        mPositionHandle[1] = GLES20.glGetAttribLocation(programHandle1, "a_Position");
				        mTextureHandle[1]=GLES20.glGetAttribLocation(programHandle1, "a_TexCoordinate");
				       
				        GLES20.glUseProgram(programHandle1);*/
				}}
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
    	//2 is to be replace by its size
    	int size2[]=new int[2];
    	size2[0]=size;
    	size2[1]=size1;
    	int drawing[]=new int[2];
    	drawing[0]= R.drawable.h;
    	drawing[1]= R.drawable.as;
    	//2 is to be replace by it size and drawing h should do some trick to do some changes
    		for(int h=0;h<2;h++){
    			mTextureHandle[h] = loadGLTexture(context, drawing[h]);	
        		//vertices
    			mWallVertices[h].position(0);
		        GLES20.glVertexAttribPointer(mPositionHandle[h], mPositionDataSize, GLES20.GL_FLOAT, false,0, mWallVertices[h]);
		        GLES20.glEnableVertexAttribArray(mPositionHandle[h]);
		         //texture
		        texturebuffer[h].position(0);
		        GLES20.glVertexAttribPointer(mTextureHandle[h],2,GLES20.GL_FLOAT,false,0,texturebuffer[h]);
		        GLES20.glEnableVertexAttribArray(mTextureHandle[h]);
		        GLES20.glUniformMatrix4fv(mMVPMatrixHandle[h], 1, false, mMVPMatrix, 0);
		        //draw array
		        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, size2[h]);
    		}
    			/*	mTextureHandle[0] = loadGLTexture(context, R.drawable.h);	
    		
    		
    				mWallVertices.position(0);
			        GLES20.glVertexAttribPointer(mPositionHandle[0], mPositionDataSize, GLES20.GL_FLOAT, false,0, mWallVertices);
			        GLES20.glEnableVertexAttribArray(mPositionHandle[0]);
			        
			     
			        //texture1
			        texturebuffer[0].position(0);
			        GLES20.glVertexAttribPointer(mTextureHandle[0],2,GLES20.GL_FLOAT,false,0,texturebuffer[0]);
			        GLES20.glEnableVertexAttribArray(mTextureHandle[0]);
			        GLES20.glUniformMatrix4fv(mMVPMatrixHandle[0], 1, false, mMVPMatrix, 0);
			        //drawing one part
			        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, size);
			       
			        mTextureHandle[1] = loadGLTexture(context, R.drawable.as);
			         //GLES20.glDisableVertexAttribArray(mPositionHandle);
			        //GLES20.glDisableVertexAttribArray(mTextureHandle);
			        mWallVertices1.position(0);
			        GLES20.glVertexAttribPointer(mPositionHandle[1], mPositionDataSize, GLES20.GL_FLOAT, false,0, mWallVertices1);
			        //GLES20.glDisableVertexAttribArray(mPositionHandle1);
			        GLES20.glEnableVertexAttribArray(mPositionHandle[1]);
			       //texture2
			        texturebuffer[1].position(0);
			        GLES20.glVertexAttribPointer(mTextureHandle[1],2,GLES20.GL_FLOAT,false,0,texturebuffer[1]);
			        GLES20.glDisableVertexAttribArray(mTextureHandle[1]);
			        GLES20.glEnableVertexAttribArray(mTextureHandle[1]);
			        GLES20.glUniformMatrix4fv(mMVPMatrixHandle[1], 1, false, mMVPMatrix, 0);
			        //drawing another part
			        
			        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, size1);*/
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
			     GLES20.glActiveTexture(GLES20.GL_TEXTURE );
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