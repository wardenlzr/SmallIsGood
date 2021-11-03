package com.warden.generateQRcode

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
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
        iv.visibility = View.GONE
        tv_generate.setOnClickListener {
            if (TextUtils.isEmpty(et.text)) {
                ToastUtils.toast("请输入要生成的内容")
                return@setOnClickListener
            }
            KeyboardUtils.hideSoftInput(tv_save)
            val url =
                "https://www.mxnzp.com/api/qrcode/create/single?content=" + et.text + "&app_id=7pvbdvfkmersqpqu&app_secret=bkdYbGYvZTIyYzRRWEliOCtVeUJ6UT09"
            HttpUtils.doGetAsyn(url, object : CallBack {
                override fun ok(result: String) {
                    val bean: QrcodeBean = JsonUtil.toBean(result, QrcodeBean::class.java)
                    if (bean.code == 1) {
                        qrCodeUrl = bean.data?.qrCodeUrl
                        Glide.with(activity).load(qrCodeUrl).into(iv)
                        iv.visibility = View.VISIBLE
                    }

                }

                override fun fail(exception: String) {
                    ToastUtils.toast(exception)
                }
            })
        }
        tv_save.setOnClickListener {
            if (TextUtils.isEmpty(qrCodeUrl)) {
                ToastUtils.toast("请先生成二维码")
                return@setOnClickListener
            }
            save()
        }
        tv_share.setOnClickListener {
            if (TextUtils.isEmpty(qrCodeUrl)) {
                ToastUtils.toast("请先生成二维码")
                return@setOnClickListener
            }
            share()
        }
        et.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (et.text.toString().isEmpty()) {
                    iv.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
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

    private var imgFile: File? = null

    private fun share() {
        imgFile = getImgFile()
        if (imgFile == null) {
            L.e("imgFile == null")
            toast("保存失败无法分享")
            return
        }
        val intent = Intent(Intent.ACTION_SEND)
        val uri = Uri.fromFile(imgFile)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.type = "image/*"
        startActivity(Intent.createChooser(intent, "share"))
    }

    private fun save() {
        imgFile = getImgFile()
        if (imgFile == null) {
            toast("保存失败")
        } else {
            toast("保存成功")
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
        if (!directory.exists()) {
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

    private fun getImgFile(): File? {
        if (imgFile != null) {
            return imgFile
        }
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
            return null
        }
        val bitmap = getBitmapByView(iv)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            bitmap2FileForQ(bitmap)
        } else {
            bitmap2File(bitmap)
        }
    }

    private fun getBitmapByView(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        return view.drawingCache
    }

    /**
     * android 11及以上保存图片到相册
     * @param image
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun bitmap2FileForQ(image: Bitmap): File? {
        val mImageTime = System.currentTimeMillis()
        val mImageFileName = et.text.toString() + "_" + mImageTime + ".png"
        val values = ContentValues();
        val imgPath = Environment.DIRECTORY_PICTURES + File.separator + "generateQRcode"
        values.put(
            MediaStore.MediaColumns.RELATIVE_PATH,
            imgPath
        ) //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, mImageFileName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        values.put(MediaStore.MediaColumns.DATE_ADDED, mImageTime / 1000);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, mImageTime / 1000);
        values.put(
            MediaStore.MediaColumns.DATE_EXPIRES,
            (mImageTime) / 1000
        );
        values.put(MediaStore.MediaColumns.IS_PENDING, 1);

        val resolver = this.contentResolver;
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
            return uriToFile(uri)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun bitmap2File(image: Bitmap): File? {
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
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return imageFile
        }
        return null
    }

    private fun uriToFile(uri: Uri): File? {
        var path: String? = null
        if ("file" == uri.scheme) {
            path = uri.encodedPath
            if (path != null) {
                path = Uri.decode(path)
                val cr: ContentResolver = this.contentResolver
                val buff = StringBuffer()
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append(
                    "'$path'"
                ).append(")")
                val cur: Cursor? = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(
                        MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA
                    ), buff.toString(), null, null
                )
                var index = 0
                var dataIdx = 0
                if (cur == null) {
                    return null
                }
                cur.moveToFirst()
                while (!cur.isAfterLast) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID)
                    index = cur.getInt(index)
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    path = cur.getString(dataIdx)
                    cur.moveToNext()
                }
                cur.close()
                if (index == 0) {
                } else {
                    val u = Uri.parse("content://media/external/images/media/$index")
                    println("temp uri is :$u")
                }
            }
            if (path != null) {
                return File(path)
            }
        } else if ("content" == uri.scheme) {
            // 4.2.2以后
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor =
                this.contentResolver.query(uri, proj, null, null, null) ?: return null
            if (cursor.moveToFirst()) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path = cursor.getString(columnIndex)
            }
            cursor.close()
            return File(path)
        } else {
            L.e("content != uri.scheme")
            //Log.i(TAG, "Uri Scheme:" + uri.getScheme());
        }
        return null
    }

    private fun galleryAddPic(imagePath: String?) {
        if (TextUtils.isEmpty(imagePath)) {
            L.e("TextUtils.isEmpty(imagePath)")
            return
        }
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        sendBroadcast(mediaScanIntent)
    }
}