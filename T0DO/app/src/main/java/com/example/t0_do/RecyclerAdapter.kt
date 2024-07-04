package com.example.t0_do

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.t0_do.databinding.ActivityGorevActivitytBinding
import com.example.t0_do.databinding.RecycleDuzenBinding
import com.google.android.material.snackbar.Snackbar



class RecyclerAdapter(val liste : ArrayList<Gorevler>) : RecyclerView.Adapter<RecyclerAdapter.GorevlerHolder>() {

val recyclerActivity = RecyclerActivity()

    class GorevlerHolder(val binding: RecycleDuzenBinding) : RecyclerView.ViewHolder(binding.root) {



    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GorevlerHolder {
         //ViewHolder oluşturacağız
         val  binding = RecycleDuzenBinding.inflate(LayoutInflater.from(parent.context),parent,false)
         return GorevlerHolder(binding)
     }

    // Adapter kaç öğe göstereceğini döndürü
     override fun getItemCount(): Int {
         return liste.size
     }

     override fun onBindViewHolder(holder: GorevlerHolder, position: Int) {

         holder.binding.baslikRecycler.setText(liste[position].baslik)
         holder.binding.metinRecycler.setText(liste[position].metin)
         holder.binding.zamanRecycler.setText(liste[position].zaman)

         // sadece başlık ve metine tıkladığı zaman dinleme yapacak
        holder.binding.metinRecycler.setOnClickListener {
            gorevSilme(holder,position)
        }
         holder.binding.baslikRecycler.setOnClickListener {
             gorevSilme(holder,position)
         }
         holder.binding.zamanRecycler.setOnClickListener {
             gorevSilme(holder,position)
         }


     }

    fun gorevSilme(holder : GorevlerHolder , posizyon : Int){

        val alertDialog = AlertDialog.Builder(holder.itemView.context)

        alertDialog.setTitle("Silme işlemi")
        alertDialog.setMessage("\"Bu öğeyi silmek istediğinizden emin misiniz? Bu işlem geri alınamaz. ")
        alertDialog.setPositiveButton("Evet"){ dialog, i ->
            // EditText içeriğini silmek ve EditText'i temizlemek için
            holder.binding.metinRecycler.text.clear()
            holder.binding.baslikRecycler.text.clear()
            holder.binding.zamanRecycler.text.clear()
            recyclerActivity.zamanSil()
            Toast.makeText(holder.itemView.context,"Silindi",Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

        alertDialog.setNegativeButton("Hayır"){dialog,i ->

            Toast.makeText(holder.itemView.context,"Silme işlemi iptal edildi.",Toast.LENGTH_LONG).show()

        }
        alertDialog.show()

       }
    }

