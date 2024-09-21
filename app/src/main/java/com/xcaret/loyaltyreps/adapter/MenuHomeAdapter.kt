package com.xcaret.loyaltyreps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xcaret.loyaltyreps.data.entity.MenuItemHomeEntity


//class MenuHomeAdapter(
//    private var menuList: ArrayList<MenuItemHomeEntity>,
//    val handlerItem: (item: MenuItemHomeEntity)->Unit
//): RecyclerView.Adapter<MenuHomeAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemBinding = MenuItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(itemBinding)
//    }
//
//
//    override fun getItemCount()  = menuList.size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val menuSelected = menuList[position]
//        holder.menuIcon.setImageResource(menuSelected.icon!!)
//        holder.menuTitle.text = menuSelected.title
//        holder.menuDesc.text = menuSelected.desc
//        holder.bind(menuSelected)
//    }
//
//    inner class ViewHolder(itemBinding: MenuItemHomeBinding): RecyclerView.ViewHolder(itemBinding.root){
//        val menuIcon: ImageView = itemBinding.itemMenuIcon
//        val menuTitle: TextView = itemBinding.itemMenuTitle
//        val menuDesc: TextView = itemBinding.itemMenuDesc
//        val container: LinearLayout = itemBinding.itemContainer
//
//        fun bind( item: MenuItemHomeEntity){
//            container.isClickable = true
//            container.onClick {
//                handlerItem(item)
//            }
//        }
//    }
//}
