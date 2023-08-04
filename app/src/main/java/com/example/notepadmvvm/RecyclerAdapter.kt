package com.example.notepadmvvm

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepadmvvm.data.DBModel
import com.example.notepadmvvm.databinding.RecyclerRowBinding
import com.example.notepadmvvm.presentation.ViewModel.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecyclerAdapter(private val list: ArrayList<DBModel>, private val viewModel: ViewModel): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.titleText.text = list[position].title
        holder.binding.contentText.text = list[position].text
        holder.binding.dateText.text = list[position].date

        holder.itemView.setOnClickListener {
            customDialog(holder.itemView.context,list[position].title,list[position].text,SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()), position)
        }

        holder.itemView.setOnLongClickListener {
            showDeleteAlert(holder.itemView.context, list[position])
            true
        }
    }

    fun updateList(l: List<DBModel>){
        list.clear()
        list.addAll(l)
        notifyDataSetChanged()
    }


    private fun showDeleteAlert(context: Context,dbModel: DBModel){
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Warning")
        alertDialog.setMessage("Are you sure about delete this note?")
        alertDialog.setPositiveButton("Yes") { dialog, _ ->
            viewModel.delete(dbModel)
            dialog.dismiss()
        }
        alertDialog.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.create().show()
    }
    private fun customDialog(context: Context,title: String,text: String, date: String, position: Int){

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_alert_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val button = dialog.findViewById<Button>(R.id.alet_button)
        val buttonSave = dialog.findViewById<Button>(R.id.alet_button_save)
        val titleText = dialog.findViewById<TextView>(R.id.alet_dialog_title)
        val contextText = dialog.findViewById<TextView>(R.id.alet_dialog_context)
        val dateText = dialog.findViewById<TextView>(R.id.alet_dialog_date)

        titleText.text = title
        contextText.text = text
        dateText.text = date
        button.setOnClickListener{
            dialog.dismiss()
        }
        buttonSave.setOnClickListener {
            val newTitle = titleText.text.toString().trim()
            val newContent = contextText.text.toString().trim()

            val updatedDBModel = DBModel(title = newTitle, text = newContent,
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()))
            updatedDBModel.id = list[position].id

            viewModel.update(updatedDBModel)
            dialog.dismiss()
        }

        dialog.show()

    }

}