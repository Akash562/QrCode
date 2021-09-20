package com.mmi.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {


        ImageView qrimage;
        EditText ed_url;
        public final static int QR_Width = 250;
        Bitmap qr_bitmap;
        BitMatrix bitMatrix;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            qrimage = findViewById(R.id.qrview);
            ed_url = findViewById(R.id.url_field);

        findViewById(R.id.gen).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        qr_bitmap = TextToQRGen(ed_url.getText().toString());
                        qrimage.setImageBitmap(qr_bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Bitmap TextToQRGen(String Value) throws Exception {

            try {
                bitMatrix = new MultiFormatWriter().encode(Value, BarcodeFormat.DATA_MATRIX.QR_CODE, QR_Width, QR_Width, null);
            } catch (Exception e) {
                return null;
            }

            int bitMatrixWidth = bitMatrix.getWidth();
            int bitMatrixHeight = bitMatrix.getHeight();
            int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
            for (int y = 0; y < bitMatrixHeight; y++) {
                int offset = y * bitMatrixWidth;
                for (int x = 0; x < bitMatrixWidth; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
            bitmap.setPixels(pixels, 0, 250, 0, 0, bitMatrixWidth, bitMatrixHeight);
            return bitmap;
        }
    }