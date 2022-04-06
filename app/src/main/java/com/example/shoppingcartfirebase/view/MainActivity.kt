package com.example.shoppingcartfirebase.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingcartfirebase.R
import com.example.shoppingcartfirebase.model.MenuModel
import com.example.shoppingcartfirebase.viewmodel.MenuAdapter
import com.example.shoppingcartfirebase.viewmodel.MenuLoadListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Array

class MainActivity : AppCompatActivity(), MenuLoadListener {

    lateinit var menuLoadListener: MenuLoadListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        loadMenuFromFirebase()
    }

    private fun loadMenuFromFirebase() {
        val menuModelsFirebase: MutableList<MenuModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Menu")
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        for(menuSnapshot in snapshot.children) {
                            val menuModel = menuSnapshot.getValue(MenuModel::class.java)
                            menuModel!!.key = menuSnapshot.key
                            menuModelsFirebase.add(menuModel)
                        }
                        menuLoadListener.onMenuLoadSuccess(menuModelsFirebase)
                    }
                    else {
                        menuLoadListener.onMenuLoadSuccess(menuModelsFirebase)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    menuLoadListener.onMenuLoadFailed(error.message)
                }

            })
    }

    private fun init() {
     menuLoadListener = this

        val gridLayoutManager = GridLayoutManager(this, 2)
        menuRecycler.layoutManager = gridLayoutManager
        menuRecycler.addItemDecoration(ItemSpacing())
    }

    override fun onMenuLoadSuccess(menuModelList: List<MenuModel>?) {
        val adapter = MenuAdapter(this, menuModelList!!)
        menuRecycler.adapter = adapter
    }

    override fun onMenuLoadFailed(message: String?) {
        Snackbar.make(mainLayout, message!!, Snackbar.LENGTH_LONG).show()
    }
}