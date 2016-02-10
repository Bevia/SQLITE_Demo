package com.beviacode.asqlitebevia.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Utility {
	
	// convert from bitmap to byte array
	
	/**
	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, stream);
		return stream.toByteArray();
	} */
	
	    public static byte[] getBytes(Bitmap bitmap) {
	    	
	    if ( bitmap != null) {
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
	    int width = bitmap.getWidth(); 
	    int height = bitmap.getHeight(); 

	    float scale = (float) 0.5; //usually calculated in runtime but set for simplicity now.
	    // Resize the bitmap to half the original size! Just for now!
	    Matrix matrix = new Matrix();
	    matrix.postScale(scale, scale);
	    
	    // Recreate the new bitmap
	    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	    
	    stream = new ByteArrayOutputStream();		
	    resizedBitmap.compress(CompressFormat.PNG, 0, stream);		
	    return stream.toByteArray();	
	    }
		return null;
		
	}

	// convert from byte array to bitmap
	public static Bitmap getPhoto(byte[] image) {
		
		if (image != null) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
		}
		return null;
	}
}
