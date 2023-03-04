package fastcampus.part1.fc_chapter4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        // intent로 데이터 넘겨받기
        val message = intent.getStringExtra("intentMessage") ?: "없음"
        Log.d("intentMessage", message)
    }
}