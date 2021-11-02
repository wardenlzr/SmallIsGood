package com.warden.generateQRcode

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.warden.lib.base.BaseAct
import com.warden.lib.util.*
import com.warden.lib.util.HttpUtils.CallBack
import kotlinx.android.synthetic.main.act_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yubin 2021年9月20日 16:47:09
 */
class MainAct : BaseAct() {
    private val REQUESTCODE: Int = 999
    private var qrCodeUrl: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        bt_generate.setOnClickListener {
            if (TextUtils.isEmpty(et.text)) {
                ToastUtils.toast("请输入要生成的内容")
                return@setOnClickListener
            }
            KeyboardUtils.hideSoftInput(bt_save)
            val url =
                "https://www.mxnzp.com/api/qrcode/create/single?content=" + et.text + "&app_id=7pvbdvfkmersqpqu&app_secret=bkdYbGYvZTIyYzRRWEliOCtVeUJ6UT09"
            HttpUtils.doGetAsyn(url, object : CallBack {
                override fun ok(result: String) {
                    val bean: QrcodeBean = JsonUtil.toBean(result, QrcodeBean::class.java)
                    if (bean.code == 1) {
                        qrCodeUrl = bean.data?.qrCodeUrl
                        Glide.with(activity).load(qrCodeUrl).into(iv)
                    }

                }

                override fun fail(exception: String) {
                    ToastUtils.toast(exception)
                }
            })
        }
        bt_save.setOnClickListener {
            if (TextUtils.isEmpty(qrCodeUrl)) {
                ToastUtils.toast("请先生成二维码")
                return@setOnClickListener
            }
            save()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUESTCODE) {
            save()
        } else {
            toast("没有权限不可以保存哦！")
        }
    }

    private fun save() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            //进入到这里代表没有权限
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUESTCODE
            )
            return
        }
        val image = getBitmapByView(iv)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageToGallery(this, image)
        } else {
            saveImage(image)
        }
        /*
        Glide.with(activity)
            .load(qrCodeUrl)
            .asBitmap()
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    glideAnimation: GlideAnimation<in Bitmap>?
                ) {
                    saveImage(resource)
                }
            })
        * */
        /*if (!directory.exists()) {
            directory.mkdir()
        }
        val filename = System.currentTimeMillis().toString() + ".png"
        try {
            val file = File(directory, filename)
            val out = FileOutputStream(file)
            val options: BitmapFactory.Options = BitmapFactory.Options()
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.toString(), options);
            // 设置为值>1，则请求解码器对原始图像进行二次采样，返回较小的图像以节省内存，例如，inSampleSize==4返回的图像的宽度/高度为原始图像的1/4，像素数为1/16
            options.inSampleSize = 1
            // 如果设置为true，解码器将返回null（无位图）
            options.inJustDecodeBounds = false;
            val mBitmap: Bitmap = BitmapFactory.decodeFile(file.toString(), options)
            mBitmap.compress(
                Bitmap.CompressFormat.JPEG,
                70,
                out
            ) // 70 是压缩率，表示压缩掉30%，保留70%; 如果不压缩是100，表示压缩率为0
            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
            runOnUiThread {
                ToastUtils.toast("图片保存失败：$e")
            }
        }*/
    }

    private fun getBitmapByView(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        return view.drawingCache
    }

    /**
     * android 11及以上保存图片到相册
     * @param context
     * @param image
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageToGallery(context: Context, image: Bitmap) {
        val mImageTime = System.currentTimeMillis()
        val mImageFileName = et.text.toString() + "_" + mImageTime + ".png"
        val values = ContentValues();
        values.put(
            MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES
                    + File.separator + "generateQRcode"
        ); //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, mImageFileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.DATE_ADDED, mImageTime / 1000);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, mImageTime / 1000);
        values.put(
            MediaStore.MediaColumns.DATE_EXPIRES,
            (mImageTime) / 1000
        );
        values.put(MediaStore.MediaColumns.IS_PENDING, 1);

        val resolver = context.contentResolver;
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // First, write the actual data for our screenshot
        try {
            val out = resolver.openOutputStream(uri!!)
            if (!image.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                throw IOException("Failed to compress");
            }
            // Everything went well above, publish it!
            values.clear();
            values.put(MediaStore.MediaColumns.IS_PENDING, 0);
            values.putNull(MediaStore.MediaColumns.DATE_EXPIRES);
            resolver.update(uri, values, null, null);
            toast("保存成功")
        } catch (e: IOException) {
            e.printStackTrace()
            toast("保存失败")
        }
    }

    private fun saveImage(image: Bitmap) {
        val saveImagePath: String
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        // time为转换格式后的字符串
        val time = dateFormat.format(Date(System.currentTimeMillis()));
        val imageFileName = et.text.toString() + "_" + time + ".png"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
                    + "/generateQRcode"
        )
        L.e("storageDir:$storageDir")
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            saveImagePath = imageFile.absolutePath
            try {
                val fos: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 70, fos)
                fos.close()
                // Add the image to the system gallery
                galleryAddPic(saveImagePath)
                toast("保存成功")
            } catch (e: Exception) {
                e.printStackTrace()
                toast("保存失败" + e.message)
            }
        } else toast("保存失败")
    }

    private fun galleryAddPic(imagePath: String?) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        sendBroadcast(mediaScanIntent)
    }
}