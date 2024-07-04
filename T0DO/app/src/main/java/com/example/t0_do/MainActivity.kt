package com.example.t0_do

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Visibility
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.t0_do.databinding.ActivityMainBinding
import org.w3c.dom.Text
import kotlin.random.Random

lateinit var binding: ActivityMainBinding
var runnable: Runnable=Runnable {  } // runnable : zamanlamış işlemleri ve ya arka planda işlemler yapmamaıza olanak sunar işlemleri iş parçacıklarından ayırarak işlemler yaparlar
// Handler, ana iş parçacığında çalışacak
val handler = Handler(Looper.getMainLooper())

lateinit var liste : Array<TextView>
var currentIndex = 0
  var sayac = 0
/*
Paralel İşlem: İşlerin aynı anda yapılmasına olanak tanır, bu da uygulamanın performansını artırabilir.
Yanıt Verebilirlik: Arka planda çalışan iş parçacıkları sayesinde kullanıcı arayüzü donmaz ve kullanıcı daha iyi bir deneyim yaşar.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

     liste = arrayOf(
         binding.textView1,
         binding.textView2,
         binding.textView3,
         binding.textView4,
         binding.textView5
     )


        // runnable arrayüz clasa oluğu için nesne oluturamıyoruz bir objeye atıyoruz objede runnable referans oluyor
        runnable = object : Runnable {
            override fun run() {
                // Tüm TextView'leri görünmez yap
                for (textView in liste) {
                    textView.visibility = View.INVISIBLE
                }

                // Mevcut indexteki TextView'i görünür yap
                if (sayac < liste.size) {
                    liste[sayac].visibility = View.VISIBLE
                    sayac++
                } else {
                    sayac = 0
                    liste[sayac].visibility = View.VISIBLE
                }

                // 300ms sonra Runnable'ı tekrar çalıştır
                handler.postDelayed(this, 300)
            }
        }

        // İlk Runnable'ı başlat
        handler.post(runnable)


    }

    fun gorevEkle(view: View) {
        onPause()
        val intentGorev = Intent(this, gorevActivityt::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentGorev)
        finish()

    }

    fun gorevler(view: View) {
        onPause()
        val intentRecycler = Intent(this, RecyclerActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentRecycler)
        finish()

    }

    /*
    onPause:
onPause yöntemi, kullanıcı başka bir aktiviteye geçtiğinde çağrılır
 ve bu yöntem içinde handler.removeCallbacks(runnable) çağrılarak Runnable durdurulur.
     */
    override fun onPause() {
        // başka activiteye geçtiğinde çağrılır
        super.onPause()
        // Handler'ı durdur ve Runnable'ı kaldır
        handler.removeCallbacks(runnable)
    }
/*
onResume yöntemi, kullanıcı aktiviteye geri döndüğünde çağrılır ve
 bu yöntem içinde handler.post(runnable) çağrılarak Runnable yeniden başlatılır.
 */
    override fun onResume() {
        super.onResume()
        handler.post(runnable)
    }
}
