package cz.crusty.pokemon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (BuildConfig.DEBUG && savedInstanceState == null) {
            //val navController = findNavController(R.id.nav_host_fragment)
            //navController.navigate(R.id.nav_new_job)
        }
    }
}