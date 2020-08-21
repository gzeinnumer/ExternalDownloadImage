package com.gzeinnumer.externaldownloadimage.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.gzeinnumer.externaldownloadimage.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class FunctionGlobalFile {

    private static final String TAG = "FunctionGlobalFile_";

    //create file
    public static boolean initFile(String text) {
        File file;
        file = new File(FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + "/File.txt");
        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter writer = new PrintWriter(f);
            writer.println(text + "1");
            writer.println(text + "2");
            writer.println(text + "3");
            writer.flush();
            writer.close();
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void initFileImage(final String imgUrl, final String filename, final ImageView sendImageTo, final boolean isNew) {
        File myDir = new File(FunctionGlobalDir.getStorageCard+FunctionGlobalDir.appFolder);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        if (filename.length()>0){
            myDir = new File(myDir, filename);
        } else {
            myDir = new File(myDir, new Date().toString()+ ".jpg");
        }
        if (!myDir.exists() || isNew){ // file tidak ada or isNew : True
            final File finalMyDir = myDir;
            Picasso.get().load(imgUrl)
                    .placeholder(R.drawable.ic_baseline_sync_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(new Target() {
                              @Override
                              public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                  try {
                                      if (!finalMyDir.exists() || isNew){
                                          //jika isNew true maka foto lama akan dihapus dan diganti dengan yang baru
                                          //jika file tidak ditemukan maka file akan dibuat
                                          FunctionGlobalDir.myLogD(TAG, "Foto baru disimpan ke penyimpanan");
                                          FileOutputStream out = new FileOutputStream(finalMyDir);
                                          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                                          out.flush();
                                          out.close();
                                      } else {
                                          //jika isNew false maka akan load file lama di penyimpanan
                                          FunctionGlobalDir.myLogD(TAG, "Foto lama di load dari penyimpanan");
                                          bitmap = BitmapFactory.decodeFile(finalMyDir.getAbsolutePath());
                                      }
                                      sendImageTo.setImageBitmap(bitmap);
                                  } catch (Exception e) {
                                      FunctionGlobalDir.myLogD(TAG, e.getMessage());
                                  }
                              }

                              @Override
                              public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                  FunctionGlobalDir.myLogD(TAG, e.getMessage());
                              }

                              @Override
                              public void onPrepareLoad(Drawable placeHolderDrawable) { }
                          }
                    );
        } else {
            FunctionGlobalDir.myLogD(TAG, "Foto lama di load dari penyimpanan");
            Bitmap bitmap = BitmapFactory.decodeFile(myDir.getAbsolutePath());
            sendImageTo.setImageBitmap(bitmap);
        }
    }
}
