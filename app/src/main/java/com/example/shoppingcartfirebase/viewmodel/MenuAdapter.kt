package com.example.shoppingcartfirebase.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingcartfirebase.R
import com.example.shoppingcartfirebase.model.MenuModel
import java.lang.StringBuilder

class MenuAdapter (
    private val context: Context,
    private val list: List<MenuModel>
        ): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    class MenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var menuImage: ImageView? = null
        var menuName: TextView? = null
        var menuPrice: TextView? = null

        init {
            menuImage = itemView.findViewById(R.id.imageView) as ImageView
            menuName = itemView.findViewById(R.id.menuItemText) as TextView
            menuPrice = itemView.findViewById(R.id.priceText) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.layout_menu_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        Glide.with(context)
            .load(list[position].image)
            .into(holder.menuImage!!)
        holder.menuName!!.text = StringBuilder().append(list[position].name)
        holder.menuPrice!!.text = StringBuilder("$").append(list[position].price)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}