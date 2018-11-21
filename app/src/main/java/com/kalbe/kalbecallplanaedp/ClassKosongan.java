package com.kalbe.kalbecallplanaedp;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ASUS on 01/11/2018.
 */

public class ClassKosongan extends Fragment {
    View v;
    File fileFolder = null;
    String pathFolder = new clsHardCode().txtFolderAkuisisi;

    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;
    String dwnload_file_path =
            "http://coderzheaven.com/sample_folder/sample_file.png";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_class_kosongan, container, false);

        Button decrypt = (Button)v.findViewById(R.id.decryprt);
        final Button encrypt = (Button)v.findViewById(R.id.encrypt);

//        showProgress(dwnload_file_path);
//
//        new Thread(new Runnable() {
//            public void run() {
//                downloadFile();
//            }
//        }).start();
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decryptFile();
            }
        });


        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encrypteFile();
            }
        });

        return v;
    }

//    void downloadFile(){
//
//        try {
//            URL url = new URL(dwnload_file_path);
//            HttpURLConnection urlConnection = (HttpURLConnection)
//                    url.openConnection();
//
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setDoOutput(true);
//
//            //connect
//            urlConnection.connect();
//
//            //set the path where we want to save the file
//            File SDCardRoot = Environment.getExternalStorageDirectory();
//            //create a new file, to save the downloaded file
//            File file = new File(SDCardRoot,"downloaded_file.png");
//
//            FileOutputStream fileOutput = new FileOutputStream(file);
//
//            //Stream used for reading the data from the internet
//            InputStream inputStream = urlConnection.getInputStream();
//
//            //this is the total size of the file which we are downloading
//            totalSize = urlConnection.getContentLength();
//
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    pb.setMax(totalSize);
//                }
//            });
//
//            //create a buffer...
//            byte[] buffer = new byte[1024];
//            int bufferLength = 0;
//
//            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
//                fileOutput.write(buffer, 0, bufferLength);
//                downloadedSize += bufferLength;
//                // update the progressbar //
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        pb.setProgress(downloadedSize);
//                        float per = ((float)downloadedSize/totalSize) *
//                                100;
//                        cur_val.setText("Downloaded " + downloadedSize +
//
//                                "KB / " + totalSize + "KB (" + (int)per + "%)" );
//                    }
//                });
//            }
//            //close the output stream when complete //
//            fileOutput.close();
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    // pb.dismiss(); // if you want close it..
//                }
//            });
//
//        } catch (final MalformedURLException e) {
//            showError("Error : MalformedURLException " + e);
//            e.printStackTrace();
//        } catch (final IOException e) {
//            showError("Error : IOException " + e);
//            e.printStackTrace();
//        }
//        catch (final Exception e) {
//            showError("Error : Please check your internet connection " +
//                    e);
//        }
//    }
//
//    void showError(final String err){
//        runOnUiThread(new Runnable() {
//            public void run() {
//                Toast.makeText(DownloadFileDemo1.this, err,
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    void showProgress(String file_path){
//        dialog = new Dialog(DownloadFileDemo1.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.myprogressdialog);
//        dialog.setTitle("Download Progress");
//
//        TextView text = (TextView) dialog.findViewById(R.id.tv1);
//        text.setText("Downloading file from ... " + file_path);
//        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
//        cur_val.setText("Starting download...");
//        dialog.show();
//
//        pb = (ProgressBar)dialog.findViewById(R.id.progress_bar);
//        pb.setProgress(0);
//        pb.setProgressDrawable(
//                getResources().getDrawable(R.drawable.green_progress));
//    }


    private void encrypteFile(){
        String key = "1234567890123456";
//        clsMainActivity _clsMainActivity = new clsMainActivity();
        File file = null;
//        fileFolder = file;

        try {
            byte[] array = getFileencrypt();
            byte[] keyInByte = getKeyBytes(key);
            byte[] arrayFileEncrypt = encrypt(array, keyInByte, keyInByte);

            file = saveFile(arrayFileEncrypt, pathFolder, "dewi");
//            if (data.get_intActive().equals("1")){
//
//
////                data.set_intActive("0");;
////
////                List<clsFileAttach_mobile> dt = new ArrayList<>();
////                dt.add(data);
////
////                new clsFileAttach_mobileBL().saveData(dt);
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    private void decryptFile(){
        String key = "1234567890123456";
//        clsMainActivity _clsMainActivity = new clsMainActivity();
        File file = null;
//        fileFolder = file;

        try {
            byte[] array = getFile();
            byte[] keyInByte = getKeyBytes(key);
            byte[] arrayFileDecrypt = decrypt(array, keyInByte, keyInByte);

            file = saveFile(arrayFileDecrypt, pathFolder, "dewi.pdf");

            Uri uri;
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
//            File mediaStorageDir =  ImagePick.decodeByteArraytoTempFile(dataArray, new clsHardCode().txtPathTempData, fileExtension);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
                uri =  FileProvider.getUriForFile(getContext().getApplicationContext(), getContext().getApplicationContext().getPackageName()+".provider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);

//            if (display(file, data)){
//                data.set_intActive("1");
//                data.set_intStatus("READ");
//
//                List<clsFileAttach_mobile> dt = new ArrayList<>();
//                dt.add(data);
//
//                new clsFileAttach_mobileBL().saveData(dt);
//            }
        } catch (ActivityNotFoundException e){
            ToastCustom.showToasty(getContext(), "You haven't app for open this file", 4);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt(byte[] plainText, byte[] key, byte [] initialVector) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        String cipherTransformation = "AES/CBC/PKCS5Padding";
        String aesEncryptionAlgorithm = "AES";
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        plainText = cipher.doFinal(plainText);
        return plainText;
    }

    public File saveFile(byte[] fileArray, String path, String nameFile) {
        File file = null;
        try {
            file = new File(path, nameFile);

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(fileArray);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public byte[] getFile() throws FileNotFoundException
    {
        byte[] data = null;
        byte[] inarry = null;
        String path = new clsHardCode().txtFolderAkuisisi + "dewi";
//        AssetManager am = getAssets();
        try {
            InputStream is = new FileInputStream(path); // use recorded file instead of getting file from assets folder.
            int length = is.available();
            data = new byte[length];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = is.read(data)) != -1) {
                output.write(data, 0, bytesRead);
            }
            inarry = output.toByteArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inarry;

    }

    public byte[] getFileencrypt() throws FileNotFoundException
    {
        byte[] data = null;
        byte[] inarry = null;
        String path = new clsHardCode().txtFolderAkuisisi + "Dewi Oktaviani.pdf";
//        AssetManager am = getAssets();
        try {
            InputStream is = new FileInputStream(path); // use recorded file instead of getting file from assets folder.
            int length = is.available();
            data = new byte[length];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = is.read(data)) != -1) {
                output.write(data, 0, bytesRead);
            }
            inarry = output.toByteArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inarry;

    }


    public byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
        byte[] keyBytes= new byte[16];
        String characterEncoding = "UTF-8";
        byte[] parameterKeyBytes= key.getBytes(characterEncoding);
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
        return keyBytes;
    }

    public  byte[] decrypt(byte[] cipherText, byte[] key, byte [] initialVector) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        String cipherTransformation = "AES/CBC/PKCS5Padding";
        String aesEncryptionAlgorithm = "AES";
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }
}
