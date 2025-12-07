package com.mk.flexeconomy

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mk.flexeconomy.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var lastClicked = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bt_calculate)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null){

            binding.etConsumeFuelOne.setText(savedInstanceState.getString("et_consume_fuel_one"))
            binding.etPriceFuelOne.setText(savedInstanceState.getString("et_price_fuel_one"))

            binding.etConsumeFuelTwo.setText(savedInstanceState.getString("et_consume_fuel_two"))
            binding.etPriceFuelTwo.setText(savedInstanceState.getString("et_price_fuel_two"))

            binding.tvResult.text = savedInstanceState.getString("tv_result")

        }

        binding.btCalculate.setOnClickListener {
            calculate()
        }

        binding.btConsumeFuelOne.setOnClickListener {
            lastClicked = 1
            getResult.launch(Intent(this, FuelActivity::class.java))
        }

        binding.btConsumeFuelTwo.setOnClickListener {
            lastClicked = 2
            getResult.launch(Intent(this, FuelActivity::class.java))
        }

    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() ){ it ->

        if (it.resultCode == RESULT_OK){
            val data = it.data
            val price = data?.getStringExtra("price").toString()
            val fuel = data?.getStringExtra("fuel").toString()

            if (lastClicked == 1) {
                binding.tlPriceFuelOne.hint = fuel
                binding.etPriceFuelOne.setText(price)
            } else if (lastClicked == 2) {
                binding.tlPriceFuelTwo.hint = fuel
                binding.etPriceFuelTwo.setText(price)
            }
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
            getString(R.string.fuel_one)
        } else if (costPerKmTwo < costPerKmOne) {
            getString(R.string.fuel_two)
        } else {
            getString(R.string.fuel_three)
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


