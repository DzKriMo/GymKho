package com.thekrimo.gymkho.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.thekrimo.gymkho.R

class CustomSpinnerAdapter(
    context: Context,
    private val resource: Int,
    private val textViewResourceId: Int,
    private val objects: Array<String>
) : ArrayAdapter<String>(context, resource, textViewResourceId, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(textViewResourceId)

        textView.setTextColor(ContextCompat.getColor(context, R.color.purple))

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(textViewResourceId)

        textView.setTextColor(ContextCompat.getColor(context, R.color.white))
        textView.textSize = 80f

        return view
    }
}
