package com.yumo.ymtest.kotlin

import com.yumo.demo.anno.YmClassTest
import com.yumo.demo.view.YmTestFragment

/**
 * Created by yumodev on 17/11/7.
 */
@YmClassTest(name = "测试Kotlin")
class KotlinTestView : YmTestFragment() {

    fun testShowMessage(){
        showToastMessage("test")
    }
}