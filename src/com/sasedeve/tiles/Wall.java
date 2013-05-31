package com.sasedeve.tiles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;


public class Wall {
	
	private final Context context;
	private int mMVPMatrixHandle;
	private int mPositionHandle;
	//private int mColorHandle;
	//private final int mColorDataSize = 4;
	//private final FloatBuffer mWallcolor;
	private final int mPositionDataSize = 3;
	private final FloatBuffer mWallVertices;
	private final int mBytesPerFloat = 4;
	public static float l;//=1.5f;
	private final FloatBuffer texturebuffer;
	private int mTextureHandle;
	private final float[] textureVertices; /*= {
            
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
            1.0f,0.0f,
            
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
};*/
	private final float[] wallVerticesData;// = {
			/*// one part
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
			1,0,0,
			//3rd
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
			1,1,0 */
		//second part	
			/*-l, -l, 0,
			0, -l, 0,
			-l, 0, 0,
			-l, 0, 0,
			0,-l, 0,
			0,0,0,
			//2nd
			0,-l,0,
			l,-l,0,
			0,0,0,
			0,0,0,
			l,-l,0,
			l,0,0,
			//3rd
			-l,0,0,
			0,0,0,
			-l,l,0,
			-l,l,0,
			0,0,0,
			0,l,0,
			//4th
			0,0,0,
			l,0,0,
			0,l,0,
			0,l,0,
			l,0,0,
			l,l,0
			
};*/
	/*private final float[] color={
		1.0f, 0.0f, 0.0f, 1.0f,
		1.0f, 0.0f, 0.0f, 1.0f,
		1.0f, 0.0f, 0.0f, 1.0f,
		1.0f, 0.0f, 0.0f, 1.0f,
		1.0f, 0.0f, 0.0f, 1.0f,
		1.0f, 0.0f, 0.0f, 1.0f
		};
	*/
	
	int size;//=wallVerticesData.length/3;
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
	        +   "   baseColor = texture2D(u_Texture, v_TexCoordinate);      \n"
			 
	     +   "   gl_FragColor = baseColor;   \n"	
			+ "} \n";	
			
			
	public Wall(Context context,float s){
				this.context=context;
				int p=6;//p is no of tiles is to be fitted
				int size1=p*p*18;
				
				l=s/2;
				wallVerticesData=new float[size1];
				int z=0;
				float d=s/p;//d is size of one tile
				int no=p*p*12;
				 for(float y=-l;y<l;)
				   {
				   for(float x=-l;x<l;)
				    {
					 wallVerticesData[z]=x;
				z++;
				     wallVerticesData[z]=y;
				z++;
				     wallVerticesData[z]=0;
				z++;
				     wallVerticesData[z]=x+d;
				z++;
				     wallVerticesData[z]=y;
				z++;
					 wallVerticesData[z]=0;
				z++;
				     wallVerticesData[z]=x;
				z++;
				     wallVerticesData[z]=y+d;
				z++;
				     wallVerticesData[z]=0;
				z++;
				     wallVerticesData[z]=x;
				z++;
				     wallVerticesData[z]=y+d;
				z++;
				     wallVerticesData[z]=0;
				z++;
				     wallVerticesData[z]=x+d;
				z++;
				     wallVerticesData[z]=y;
				z++;
				 	 wallVerticesData[z]=0;
				z++;
				     wallVerticesData[z]=x+d;
				z++;
				     wallVerticesData[z]=y+d;
				z++;
					 wallVerticesData[z]=0;
				z++;
				x=x+d;
				     }
				   y=y+d;
				   }
				 textureVertices= new float[no];
				 for(int i=0;i<textureVertices.length;i++)
				 {
					 textureVertices[i]=0;
					 i++;
					 textureVertices[i]=1;
					 i++;
					 textureVertices[i]=1;
					 i++;
					 textureVertices[i]=1;
					 i++;
					 textureVertices[i]=0;
					 i++;
					 textureVertices[i]=0;
					 i++;
					 textureVertices[i]=0;
					 i++;
					 textureVertices[i]=0;
					 i++;
					 textureVertices[i]=1;
					 i++;
					 textureVertices[i]=1;
					 i++;
					 textureVertices[i]=1;
					 i++;
					 textureVertices[i]=0;
				}
				size=wallVerticesData.length/3;
				mWallVertices = ByteBuffer.allocateDirect(wallVerticesData.length * mBytesPerFloat)
				        .order(ByteOrder.nativeOrder()).asFloatBuffer();
				mWallVertices.put(wallVerticesData).position(0);
				//mWallcolor=ByteBuffer.allocateDirect(color.length*mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
				//mWallcolor.put(color).position(0);
				
				texturebuffer=ByteBuffer.allocateDirect(textureVertices.length * mBytesPerFloat)
				        .order(ByteOrder.nativeOrder()).asFloatBuffer();
				texturebuffer.put(textureVertices).position(0);
	//this can be done in other way describe in end of comment line
				
		/*		int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

				if (vertexShaderHandle != 0)
				{
				
				GLES20.glShaderSource(vertexShaderHandle, vertexShader);
				GLES20.glCompileShader(vertexShaderHandle);
			final int[] compileStatus = new int[1];
				GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
				
				if (compileStatus[0] == 0)
				{	
				GLES20.glDeleteShader(vertexShaderHandle);
				vertexShaderHandle = 0;
				}
				}

				if (vertexShaderHandle == 0)
				{
				throw new RuntimeException("Error creating vertex shader.");
				}

				
			int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

				if (fragmentShaderHandle != 0)
				{
				
				GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);
				GLES20.glCompileShader(fragmentShaderHandle);
			final int[] compileStatus = new int[1];
				GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
				if (compileStatus[0] == 0)
				{	
				GLES20.glDeleteShader(fragmentShaderHandle);
				fragmentShaderHandle = 0;
				}
				}

				if (fragmentShaderHandle == 0)
				{
				throw new RuntimeException("Error creating fragment shader.");
				}
				
				*/
				
			int vertexShaderHandle=loadShader(GLES20.GL_VERTEX_SHADER,vertexShader);	
			int fragmentShaderHandle=loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShader);
			int programHandle = GLES20.glCreateProgram();

				if (programHandle != 0)
				{
				GLES20.glAttachShader(programHandle, vertexShaderHandle);	
				GLES20.glAttachShader(programHandle, fragmentShaderHandle);
				GLES20.glUseProgram(programHandle);
			    GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
				//GLES20.glBindAttribLocation(programHandle, 1, "a_Color");
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

				if (programHandle == 0)
				{
				throw new RuntimeException("Error creating program.");
				}
				        
				        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
				        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
				        //mColorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
				        mTextureHandle=GLES20.glGetAttribLocation(programHandle, "a_TexCoordinate");
				        GLES20.glUseProgram(programHandle);
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
    	if(i==2){
    		
    		mTextureHandle = loadGLTexture(context, R.drawable.floor);	
    	}else
    		if(i==1){
    	mTextureHandle = loadGLTexture(context, R.drawable.a);
    	}else if(i==3){
    		mTextureHandle = loadGLTexture(context, R.drawable.floor1);
    		
    	}else{
    		
    		mTextureHandle = loadGLTexture(context, R.drawable.as);
    	}
    	
		
			        mWallVertices.position(0);
			        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,0, mWallVertices);
			        GLES20.glEnableVertexAttribArray(mPositionHandle);
			       /*
			        mWallcolor.position(0);
			        GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,0, mWallcolor);
			        GLES20.glEnableVertexAttribArray(mColorHandle);
			       */
			        
			        //texture
			        texturebuffer.position(0);
			        GLES20.glVertexAttribPointer(mTextureHandle,2,GLES20.GL_FLOAT,false,0,texturebuffer);
			        GLES20.glEnableVertexAttribArray(mTextureHandle);
			        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
			        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, size);
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