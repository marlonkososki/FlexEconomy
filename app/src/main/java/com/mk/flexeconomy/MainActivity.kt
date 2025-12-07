package com.mk.flexeconomy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mk.flexeconomy.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bt_calculate)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btCalculate.setOnClickListener {
            calculate()
        }

        if (savedInstanceState != null){

            binding.etConsumeFuelOne.setText(savedInstanceState.getString("et_consume_fuel_one"))
            binding.etPriceFuelOne.setText(savedInstanceState.getString("et_price_fuel_one"))

            binding.etConsumeFuelTwo.setText(savedInstanceState.getString("et_consume_fuel_two"))
            binding.etPriceFuelTwo.setText(savedInstanceState.getString("et_price_fuel_two"))

            binding.tvResult.text = savedInstanceState.getString("tv_result")

        }
    }

    private fun calculate() {
        val etConsumeFuelOne = binding.etConsumeFuelOne.text.toString().toDoubleOrNull() ?: return
        val etPriceFuelOne = binding.etPriceFuelOne.text.toString().toDoubleOrNull() ?: return

        val etConsumeFuelTwo = binding.etConsumeFuelTwo.text.toString().toDoubleOrNull() ?: return
        val etPriceFuelTwo = binding.etPriceFuelTwo.text.toString().toDoubleOrNull() ?: return

        // Cost per km (lower is better)
        val costPerKmOne = etPriceFuelOne / etConsumeFuelOne
        val costPerKmTwo = etPriceFuelTwo / etConsumeFuelTwo

        val result = if (costPerKmOne < costPerKmTwo) {
            "The cheapest option is Fuel 1"
        } else if (costPerKmTwo < costPerKmOne) {
            "The cheapest option is Fuel 2"
        } else {
            "Both fuels have the same cost-benefit"
        }

        binding.tvResult.text = result
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("et_consume_fuel_one", binding.etConsumeFuelOne.text.toString())
        outState.putString("et_price_fuel_one", binding.etPriceFuelOne.text.toString())

        outState.putString("et_consume_fuel_two", binding.etConsumeFuelTwo.text.toString())
        outState.putString("et_price_fuel_two", binding.etPriceFuelTwo.text.toString())

        outState.putString("tv_result", binding.tvResult.text.toString())
    }
}


