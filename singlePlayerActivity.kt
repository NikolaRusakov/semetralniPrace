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
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.widget.ImageView
import java.util.*


class singlePlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player)
        setSupportActionBar(toolbar)

        rockPick.visibility = View.INVISIBLE
        paperPick.visibility = View.INVISIBLE
        scissorsPick.visibility = View.INVISIBLE
        fab.visibility = View.INVISIBLE
        stop.isClickable = false
        seekBar.visibility = View.INVISIBLE
        progressBar.max = 5000

        var progressBarRemaining: Long = 5000
        var playerScore: Int? = 0
        var aiScore: Int? = 0
        var playerPick: Int? = 0
        var aiPick: Int? = 0

        fun getImage(i: Int, image: Int) {

            val imageView = findViewById<ImageView>(image)
            when (i) {
                0 -> imageView.setImageResource(R.drawable.rock)
                1 -> imageView.setImageResource(R.drawable.paper)
                2 -> imageView.setImageResource(R.drawable.scissors)
            }
        }

        fun ClosedRange<Int>.random() =
                Random().nextInt(endInclusive - start) + start

        fun compareItemsLogic(player: Int?, ai: Int?) {
            when {
                player?.minus(1) == ai -> playerScore = playerScore?.inc()
                player?.plus(1) == ai -> aiScore = aiScore?.inc()
                player?.minus(2) == ai -> aiScore = aiScore?.inc()
                ai?.minus(1) == player -> aiScore = aiScore?.inc()
                ai?.plus(1) == player -> playerScore = playerScore?.inc()
                ai?.minus(2) == player -> playerScore = playerScore?.inc()
            }
        }

        val countDown = object : CountDownTimer(5000, 10) {

            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = millisUntilFinished.toInt()
                progressBarRemaining = millisUntilFinished
            }

            override fun onFinish() {
                Toast.makeText(applicationContext, "Time's up now!", Toast.LENGTH_SHORT).show()
                stop.isClickable = false
                button.isClickable = true
                rockPick.isClickable = false
                paperPick.isClickable = false
                scissorsPick.isClickable = false
            }
        }

        fun computeOnClick(default:Long) {
            countDown.cancel()
            val imagePicked = (0..3).random()
            aiPick = imagePicked
            getImage(imagePicked, R.id.aiResult)
            compareItemsLogic(playerPick, aiPick)
            pScore.text = playerScore?.toString()
            aScore.text = aiScore?.toString()
            countDown.onTick(default)
            countDown.start()
        }

        fun showDialog() {
            val builder = AlertDialog.Builder(this)
            countDown.cancel()
            // Set the alert dialog title
            builder.setTitle("Reset dialog")

            // Display a message on alert dialog
            builder.setMessage("Reset the game and score?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("YES") { dialog, which ->
                aScore.text = "0"
                pScore.text = "0"
                aiScore=0
                playerScore=0

                Toast.makeText(applicationContext, "Reset completed!", Toast.LENGTH_SHORT).show()
                countDown.start()

            }


            // Display a negative button on alert dialog
            builder.setNegativeButton("No") { dialog, which ->
                Toast.makeText(applicationContext, "Resumed.", Toast.LENGTH_SHORT).show()
                countDown.start()
            }


            // Display a neutral button on alert dialog
            builder.setNeutralButton("Cancel") { _, _ ->
                Toast.makeText(applicationContext, "Canceled and resumed.", Toast.LENGTH_SHORT).show()
                countDown.start()
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        seekBar.max = (3 - 1) / 1

        stop.setOnClickListener {
            showDialog()
            //            button.isClickable = true
//            stop.isClickable = false
//            rockPick.isClickable = false
//            paperPick.isClickable = false
//            scissorsPick.isClickable = false
        }

        rockPick.setOnClickListener {
            getImage(0, R.id.rock)
            playerPick = 0
            computeOnClick(progressBarRemaining)

        }

        paperPick.setOnClickListener {
            getImage(1, R.id.rock)
            playerPick = 1
            computeOnClick(progressBarRemaining)
        }

        scissorsPick.setOnClickListener {
            getImage(2, R.id.rock)
            playerPick = 2
            computeOnClick(progressBarRemaining)
        }
//        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//
//            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
//                // Display the current progress of SeekBar
//                getImage(i, R.id.rock)
//                playerPick = i
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar) {
//            }
//        })
        button.setOnClickListener {
            button.isClickable = false
            stop.isClickable = true
            rockPick.visibility = View.VISIBLE
            paperPick.visibility = View.VISIBLE
            scissorsPick.visibility = View.VISIBLE
            rockPick.isClickable = true
            paperPick.isClickable = true
            scissorsPick.isClickable = true
            Toast.makeText(applicationContext, "Game has started!", Toast.LENGTH_SHORT).show()
            seekBar.visibility = View.INVISIBLE
            rock.setImageResource(R.drawable.rock)
            rock.visibility = View.VISIBLE
            countDown.start()
        }
    }

}
