package com.yumo.ymtest.test

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.yumo.demo.config.YmTestDefine
import com.yumo.demo.listener.UpdateTitleObservable
import com.yumo.demo.view.YmTestPackageFragment
import com.yumo.ymtest.R
import kotlinx.android.synthetic.main.activity_drawer_layout.*
import kotlinx.android.synthetic.main.app_bar_drawer_layout.*
import java.util.*

class DrawerLayoutActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_layout)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //UpdateTitleObservable.getInstance().addObserver((Observer { o, arg ->{toolbar.setTitle(arg.toString())}  }))
        //UpdateTitleObservable.getInstance().addObserver(UpdateTitle())
        UpdateTitleObservable.getInstance().addObserver(Observer { o, arg ->  toolbar.setTitle(arg.toString())})
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
                showTestPackageHomePage()
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }

    fun showTestPackageHomePage(){
        val fragment = YmTestPackageFragment()
        val bundle = Bundle()
        bundle.putInt(YmTestDefine.KEY_TOOLBAR_VISIBLE, View.GONE)
        bundle.putInt(YmTestDefine.KEY_FRAGMENT_ID, R.id.test_fragment_id)
        fragment.arguments = bundle
        getSupportFragmentManager().beginTransaction().replace(R.id.test_fragment_id, fragment, "package").commit()
    }
}
