package com.example.extensionstest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.extensionstest.extensionstestlib.SourceAnime
import com.example.extensionstest.extensionstestlib.SourceEpisode

class ListAdapter<T>(private val dataSet: List<T>, private val itemOnClickListener: View.OnClickListener?) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
     class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView


        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.list_item_text)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_row_item, viewGroup, false)

        itemOnClickListener?.let {
            view.setOnClickListener(itemOnClickListener)
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position];


        viewHolder.textView.text = when(item) {
            is SourceAnime -> item.title
            is SourceEpisode -> item.title.ifEmpty { item.episodeNumber }
            else -> "Title"
        }

        Log.d("ASDF", "Item ${item.toString()}")
    }

    override fun getItemCount() = dataSet.size

}

