package rusakov.com.semetralniprace

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_multiplayer.*
import kotlinx.android.synthetic.main.activity_single_player.*
import kotlinx.android.synthetic.main.content_single_player.*
import java.util.*

class multiplayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player)

        nextMove.isClickable = false
        nextMove.visibility = View.VISIBLE
        stop.isClickable = false
        seekBar.visibility = View.INVISIBLE
        linearLayout2.visibility = View.INVISIBLE
        progressBar.max = 5000
        rock.setImageDrawable(null)
        rock.visibility = View.VISIBLE
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

        val countDown = object : CountDownTimer((5 * 1000).toLong(), 10) {

            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                Toast.makeText(applicationContext, "Time's up now!", Toast.LENGTH_SHORT).show()
                stop.isClickable = false
                button.isClickable = true
                nextMove.isClickable = false
            }
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

        fun computeOnClick() {
            countDown.cancel()
            val aiPicked = (0..3).random()
            val playerPicked = (0..3).random()
            aiPick = aiPicked
            playerPick = playerPicked
            getImage(aiPicked, R.id.aiResult)
            getImage(playerPicked, R.id.rock)
            compareItemsLogic(playerPick, aiPick)
            pScore.text = playerScore?.toString()
            aScore.text = aiScore?.toString()
            countDown.onTick((5 * 1000).toLong())
            countDown.start()
        }

        seekBar.max = (3 - 1) / 1
        nextMove.setOnClickListener {
            computeOnClick()
        }

        stop.setOnClickListener {
            countDown.cancel()
            showDialog()
        }

        button.setOnClickListener {
            button.isClickable = false
            stop.isClickable = true
            nextMove.isClickable = true
            Toast.makeText(applicationContext, "Game has started!", Toast.LENGTH_SHORT).show()
            seekBar.visibility = View.INVISIBLE
            countDown.start()
        }
    }

}
