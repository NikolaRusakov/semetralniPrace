package rusakov.com.semetralniprace

import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.SeekBar
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_single_player.*
import kotlinx.android.synthetic.main.content_single_player.*
import android.animation.Animator
import rusakov.com.semetralniprace.R.id.textView
import android.view.animation.DecelerateInterpolator
import rusakov.com.semetralniprace.R.id.progressBar
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.widget.ImageView
import java.util.*


class singlePlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player)
        setSupportActionBar(toolbar)
        var playerScore:Int?=0
        var aiScore:Int?=0
        var playerPick:Int?=0
        var aiPick:Int?=0
        fun getImage(i:Int,image:Int ){

            val imageView = findViewById<ImageView>(image)
            when(i){
                0->imageView.setImageResource(R.drawable.rock)
                1->imageView.setImageResource(R.drawable.paper)
                2->imageView.setImageResource(R.drawable.scissors)
            }
        }
        fun ClosedRange<Int>.random() =
                Random().nextInt(endInclusive - start) +  start

        fun compareItemsLogic(player:Int?,ai:Int?){

            when (ai) {
                player?.plus(1) -> playerScore=playerScore?.inc()
                player?.minus(2) -> aiScore=aiScore?.inc()
                player?.plus(2) -> aiScore=aiScore?.inc()
                player -> {playerScore=playerScore?.inc();aiScore=aiScore?.inc()}
                else -> aiScore=aiScore?.inc()
            }
        }
        fab.visibility=View.INVISIBLE
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        seekBar.visibility=View.INVISIBLE
        seekBar.max = (3 - 1) / 1
           val countDown= object:CountDownTimer((10 * 1000).toLong(), 10) {

            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                Toast.makeText(applicationContext,"Time's up now!",Toast.LENGTH_SHORT).show()
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                getImage(i,R.id.rock)
                playerPick=i
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
//                Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
//                Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })
        progressBar.max=10000
        rock.setOnClickListener {
            if (progressBar!=null){
                countDown.cancel()
                val imagePicked =(0..3).random()
                aiPick=imagePicked
                getImage(imagePicked,R.id.aiResult)
                compareItemsLogic(playerPick,aiPick)
                pScore.text = playerScore?.toString()
                aScore.text = aiScore?.toString()
            }
        }
        button.setOnClickListener {
            Toast.makeText(applicationContext,"Game has started!",Toast.LENGTH_SHORT).show()
            seekBar.visibility=View.VISIBLE
            rock.setImageResource(R.drawable.rock)
            rock.visibility=View.VISIBLE
            countDown.start()
        }
    }

}
