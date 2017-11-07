package com.yumo.ymtest.kotlin

import com.yumo.demo.view.YmTestFragment

/**
 * Created by yumodev on 17/11/7.
 */
class KotlinTestView : YmTestFragment() {

    fun testShowMessage(){
        showToastMessage("test")
    }
}