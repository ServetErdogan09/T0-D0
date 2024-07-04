package com.example.t0_do

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.t0_do.databinding.ActivityGorevActivitytBinding

lateinit var gorevBinding: ActivityGorevActivitytBinding
lateinit var arrayList: ArrayList<Gorevler>

class gorevActivityt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        gorevBinding = ActivityGorevActivitytBinding.inflate(layoutInflater)
        setContentView(gorevBinding.root)

        arrayList = ArrayList()

    }

    fun gorevKaydet(view: View) {
        val gorevler = Gorevler(
            gorevBinding.baslikRecycler.text.toString(),
            gorevBinding.metinRecycler.text.toString(),
            gorevBinding.zamanRecycler.text.toString()
        )

        if(gorevBinding.zamanRecycler.text.toString().isNotEmpty() && gorevBinding.metinRecycler.text.toString().isNotEmpty()){

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Göreve Başla")
            alertDialog.setMessage("Görevi Yapmaya Hazırmısın!!")
            alertDialog.setPositiveButton("Evet"){dialog,i ->

                arrayList.add(gorevler)
                val intent = Intent(this, RecyclerActivity::class.java)
                intent.putExtra("gorevListesi", arrayList)
                intent.putExtra("zaman",gorevler.zaman)
                Toast.makeText(this,"Kaydedildi",Toast.LENGTH_LONG).show()
                dialog.dismiss()
                startActivity(intent)

            }
            alertDialog.setNegativeButton("Hayır"){dialog,i ->
                Toast.makeText(this,"Kaydedilmedi Göreve Hazır Değilmisin!!",Toast.LENGTH_LONG).show()
            }
            alertDialog.show()

        }else{
            Toast.makeText(this,"Lütfen Metin ve Zamnı Giriniz !!",Toast.LENGTH_LONG).show()
        }


    }
}
