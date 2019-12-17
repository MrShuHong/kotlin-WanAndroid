package com.example.wan

import android.content.Intent
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.example.wan.base.BaseActivity
import kotlinx.android.synthetic.main.activity_welcome.*


/**
 * Author: shuhong
 * Date: 2019/12/17 17:01
 * Description:
 */
class WelcomeActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_welcome

    override fun initView() {
        startAnimator()
    }

    private fun startAnimator() {
        var animatorCompat = ViewCompat.animate(img_logo)
            .scaleX(1f)
            .scaleY(1f)
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationEnd(view: View?) {
                    startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                }

                override fun onAnimationCancel(view: View?) {

                }

                override fun onAnimationStart(view: View?) {

                }
            })
            .setDuration(1500)
        animatorCompat.start()
    }

}