package rusakov.com.semetralniprace

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*
import kotlin.system.exitProcess

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        textView.setOnClickListener({
            val changeActivity = Intent(this, singlePlayerActivity::class.java)
            startActivity(changeActivity)
        })
        textView2.setOnClickListener({
            val changeActivity = Intent(this, multiplayerActivity::class.java)
            startActivity(changeActivity)
        })
        textView3.setOnClickListener({
            val changeActivity = Intent(this, leaderboardActivity::class.java)
            startActivity(changeActivity)
        })
        textView4.setOnClickListener {
            exitProcess(0)
        }
    }

}
