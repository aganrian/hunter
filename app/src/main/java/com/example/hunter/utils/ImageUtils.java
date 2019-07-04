package com.example.hunter.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.hunter.utils.constant.I;
import com.example.hunter.utils.glide.GlideApp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.oase.indonesia.oasebrdiepa.R;
import id.zelory.compressor.Compressor;

public class ImageUtils {

    private ImageUtils() {
    }

    public static File compressImageFile(Context context, File actualImageFile) {
        Compressor compressor = new Compressor(context)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath());
        try {
            return compressor.compressToFile(actualImageFile, String.format("IMAGE_%s.jpeg", System.currentTimeMillis()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actualImageFile;
    }

    public static File createImageTarget(Bitmap bitmap,int type,String fileName) {
        try {
            String folder = "";
            if(type == I.TYPE_SELFIE){
                folder = "selfie";
            } else if (type == I.TYPE_DOC){
                folder = "doc";
            }
            File storageDir = new File(Environment.getExternalStorageDirectory()+"/oase/"+folder);
            if (!storageDir.exists()){
                storageDir.mkdirs();
            }
            File file = new File(storageDir, fileName+".JPEG");
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String pathSave() {
        try {
            return Environment.getExternalStorageDirectory()+"/hunter/";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteFile(String path){
        File file = new File(path);
        if(file.exists())
        {
            file.delete();
        }
    }

    public static void deleteRecursive(int type) {
        String folder = "";
        if(type == I.TYPE_SELFIE){
            folder = "selfie";
        } else if (type == I.TYPE_DOC){
            folder = "doc";
        }
        File file = new File(Environment.getExternalStorageDirectory()+"/oase/"+folder);

        String[] files;
        files = file.list();
        for (int i=0; i<files.length; i++) {
            File myFile = new File(file, files[i]);
            myFile.delete();
        }
    }

    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".JPEG";
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/hunter/ocr");
        if (!storageDir.exists())
            storageDir.mkdirs();
        return new File(storageDir, imageFileName);
        /*return File.createTempFile(
                imageFileName,  *//* prefix *//*
                "",             *//* suffix *//*
                storageDir      *//* directory *//*
        );*/
    }

    public static void displayRoundImageFromFile(Context context, ImageView imageView, String imageUrl) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.image_default)
                .centerCrop()
                .dontAnimate();

        GlideApp.with(context)
                .asBitmap()
                .apply(options)
                .load(imageView)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }




    public static void displayImageFromUrl(Context context, ImageView imageView, String imageUrl, RequestListener<Drawable> listener,int drawable) {
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);


        if (listener != null) {
            GlideApp.with(context)
                    .load(imageUrl)
                    .apply(options)
                    .error(drawable)
                    .listener(listener)
                    .into(imageView);
        } else {
            GlideApp.with(context)
                    .load(imageUrl)
                    .apply(options)
                    .error(drawable)
                    .into(imageView);
        }
    }

    public static void displayImageFromUrlWithErrorDrawable(Context context, ImageView imageView, String imageUrl, RequestListener<Drawable> listener,int drawable) {
        Log.e("ini wakka", imageUrl);
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

            GlideApp.with(context)
                    .asBitmap()
                    .apply(options)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(drawable)
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCornerRadius(20);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });

    }

    public static void displayImageFromUrlWithErrorDrawable2(Context context, ImageView imageView, String imageUrl, RequestListener<Drawable> listener,int drawable) {
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        GlideApp.with(context)
                .asBitmap()
                .apply(options)
                .load(imageUrl)
                .placeholder(drawable)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(20);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });

    }

    public static void displayImageRoundFromFile(Context context, ImageView imageView, File imageFile, RequestListener<Drawable> listener) {
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        if (listener != null) {
            GlideApp.with(context)
                    .load(imageFile)
                    .apply(options)
                    .listener(listener)
                    .into(imageView);
        } else {
            GlideApp.with(context)
                    .load(imageFile)
                    .apply(options)
                    .into(imageView);
        }
    }

    public static void displayImageFromFile(Context context, ImageView imageView, File imageFile, RequestListener<Drawable> listener) {
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        if (listener != null) {
            GlideApp.with(context)
                    .load(imageFile)
                    .apply(options)
                    .listener(listener)
                    .into(imageView);
        } else {
            GlideApp.with(context)
                    .load(imageFile)
                    .apply(options)
                    .into(imageView);
        }
    }

    public static File convertDrawableToFile(Context context, int drawableRes) {
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), drawableRes);
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Wallo/Image");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File imageFile = new File(directory, "user_image_default.jpeg");
        try {
            FileOutputStream stream = new FileOutputStream(imageFile);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFile;
    }


    public static String getImageBase64String(File file){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }  catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String simpleBase64Fix(File imageFile)
    {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        String base64 = "";
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Log.e("ini nilai nya brpa ? ", String.valueOf(imageFile.length()/1024));
            if(imageFile.length()/1024  > 180){
                Log.e("masuk sini","1");
                bitmap = scaleDown(rotateImage(bitmap,90),
                        800, false);
                bitmap.compress(Bitmap.CompressFormat.JPEG,90,baos);
            }else {
                Log.e("masuk sini","2");
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
            }
            byte[] imageBytes = baos.toByteArray();
            base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());

        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

//    public static String simpleBase64(String path,Context context)
//    {
//
//        File file = new File(path);
//        Uri mImageUri = null;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            mImageUri = FileProvider.getUriForFile(context,
//                    context.getString(R.string.packageProvider),file);
//        }else {
//            mImageUri = Uri.fromFile(file);
//        }
//        context.getContentResolver().notifyChange(mImageUri, null);
//        ContentResolver cr = context.getContentResolver();
//        Bitmap bitmap;
//        String base64 = "";
//        try
//        {
//            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            if(file.length()/1024  > 195){
//                bitmap = scaleDown(rotateImageIfRequired(bitmap, context.getApplicationContext(), mImageUri),
//                        800, false);
//                bitmap.compress(Bitmap.CompressFormat.JPEG,90,baos);
//            }else {
//                bitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
//            }
//            byte[] imageBytes = baos.toByteArray();
//            base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return base64;
//    }

    public static Bitmap rotateImageIfRequired(Bitmap img, Context context, Uri selectedImage) throws IOException {
        if (selectedImage.getScheme().equals("content")) {
            String[] projection = { MediaStore.Images.ImageColumns.ORIENTATION };
            Cursor c = context.getContentResolver().query(selectedImage, projection, null, null, null);
            if (c.moveToFirst()) {
                final int rotation = c.getInt(0);
                c.close();
                return rotateImage(img, rotation);
            }
            return img;
        } else {
            ExifInterface ei = new ExifInterface(selectedImage.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 270);
                default:
                    return img;
            }
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

}