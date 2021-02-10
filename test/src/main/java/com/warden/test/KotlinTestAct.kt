package com.warden.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.warden.lib.base.BaseAct

class KotlinTestAct : BaseAct() {

    var str1 = "成员变量"

    companion object {
        var str2 = "静态成员变量"
        //静态方法
        fun start(context: Context) {
            val intent = Intent(context, KotlinTestAct::class.java)
            context.startActivity(intent)
        }
    }

    //重载的方法
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_test_kotlin)
        val a = IntArray(3)
        val int = Int(1)
        a[0] = 10
        println("a[0]=" + a[0])
        println("a[1]=" + a[1]++)
        println("a[2]=" + ++a[2])
    }

    private fun Int(i: Int): Int {
        return i
    }

    //普通方法(在XML中直接调用的)
    fun test(view: View) {
        Toast.makeText(view.context, str1+ str2, Toast.LENGTH_SHORT).show()
    }

}