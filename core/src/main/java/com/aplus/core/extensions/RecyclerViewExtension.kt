package com.aplus.core.extensions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setLinearAdapter(value: RecyclerView.Adapter<*>) {
    adapter = value
    layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.setGridAdapter(value: RecyclerView.Adapter<*>, grid: Int) {
    adapter = value
    layoutManager = GridLayoutManager(context, grid)
}