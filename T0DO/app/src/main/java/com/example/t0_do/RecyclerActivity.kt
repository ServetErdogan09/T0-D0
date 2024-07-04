package com.example.t0_do

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t0_do.databinding.ActivityRecyclerBinding

lateinit var bindingRecyclerActivity: ActivityRecyclerBinding
lateinit var countDownTimer: CountDownTimer

var kalanSure = 0L

var zaman = "0"

var devamMi = false

var saat = 0L

class RecyclerActivity : AppCompatActivity() {

    private lateinit var list: ArrayList<Gorevler>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingRecyclerActivity = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(bindingRecyclerActivity.root)

        // Veriyi Intent'ten al
        list = intent.getSerializableExtra("gorevListesi") as? ArrayList<Gorevler> ?: ArrayList()
        zaman = intent.getStringExtra("zaman").toString()

        // RecyclerView ve Adapter kurulumunu yap
        val recyclerAdapter = RecyclerAdapter(list)

        bindingRecyclerActivity.Recyclerid.layoutManager = LinearLayoutManager(this)
        bindingRecyclerActivity.Recyclerid.adapter = recyclerAdapter

         saat = zaman.toLong()*60*60*1000


    }


    private fun countDownTimerBaslat(zaman : Long){
        countDownTimer = object : CountDownTimer(zaman, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                kalanSure = millisUntilFinished
                val hours = millisUntilFinished / (1000 * 60 * 60)
                val minutes = millisUntilFinished / (1000 * 60) % 60
                val seconds = millisUntilFinished / 1000 % 60

                bindingRecyclerActivity.zamantextView.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }

            override fun onFinish() {
                Toast.makeText(this@RecyclerActivity, "Süre Bitti", Toast.LENGTH_LONG).show()
            }
        }.start()

    }

    fun baslaZaman(view : View){

        if (!devamMi){
            countDownTimerBaslat(saat)
            bindingRecyclerActivity.devambutton.isEnabled = false
            bindingRecyclerActivity.durdurButon.isEnabled = true

        }else{
            countDownTimerBaslat(kalanSure)
            bindingRecyclerActivity.devambutton.isEnabled = false
            bindingRecyclerActivity.durdurButon.isEnabled = true

        }

    }

    fun durdurZaman(view : View){

        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
            devamMi = true
            bindingRecyclerActivity.devambutton.isEnabled = true
            bindingRecyclerActivity.durdurButon.isEnabled = false
        }
    }

    fun zamanSil() {
        bindingRecyclerActivity.zamantextView.text = "00:00:00"
        kalanSure = 0 // Kalan zamanı sıfırla

        // Sayaç çalışıyorsa durdur lateinit olarak kullanılan değişken başlatıp başlatılmadığına bakıyor
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }

        // Intent'i MainActivity'e yönlendir
        val intent = Intent(bindingRecyclerActivity.root.context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        bindingRecyclerActivity.root.context.startActivity(intent)
        finish()
    }


}
