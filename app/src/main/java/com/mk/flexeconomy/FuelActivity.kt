package com.mk.flexeconomy

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mk.flexeconomy.databinding.ActivityFuelBinding

class FuelActivity : AppCompatActivity() {

    private lateinit var lvFuel: ListView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fuel)

        lvFuel = findViewById(R.id.lvFuel)

        lvFuel.setOnItemClickListener{parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()

            val resourceId = resources.getIdentifier(selectedItem, "array", packageName)
            if (resourceId != 0) {
                val details = resources.getStringArray(resourceId)
                if (details.isNotEmpty()) {
                    val firstItem = details[0]
                    // Aqui você tem o primeiro item do array correspondente

                    intent.putExtra("price", firstItem)
                    intent.putExtra("fuel", selectedItem)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }

        /*binding.lvFuel.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            
            val resourceId = resources.getIdentifier(selectedItem, "array", packageName)
            if (resourceId != 0) {
                val details = resources.getStringArray(resourceId)
                if (details.isNotEmpty()) {
                    val firstItem = details[0]
                    // Aqui você tem o primeiro item do array correspondente

                    intent.putExtra("price", firstItem)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }*/
    }
}