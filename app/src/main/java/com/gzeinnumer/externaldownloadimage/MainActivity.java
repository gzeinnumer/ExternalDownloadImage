package com.gzeinnumer.externaldownloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzeinnumer.externaldownloadimage.helper.FunctionGlobalFile;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity_";

    TextView tv;
    ImageView img;
    String msg = "externaldownloadimage\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(TAG);

        tv = findViewById(R.id.tv);
        img = findViewById(R.id.img);

        tv.setText(msg);

        String imgUrl = "https://avatars3.githubusercontent.com/u/45892408?s=460&u=94158c6479290600dcc39bc0a52c74e4971320fc&v=4";

        //pilih 1 atau 2
        //1. jika isNew true maka file lama akan dihapus dan diganti dengan yang baru
        FunctionGlobalFile.initFileImage(imgUrl, "file name", img, true);
        //2. jika isNew false maka akan otomatis load file dan disimpan
        FunctionGlobalFile.initFileImage(imgUrl, "file name", img, false);
    }
}