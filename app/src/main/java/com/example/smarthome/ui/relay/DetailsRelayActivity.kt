package com.example.smarthome.ui.relay

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.smarthome.R
import com.example.smarthome.data.model.Relay
import com.example.smarthome.viewmodel.RelayViewModel
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.utilities.Resource
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class DetailsRelayActivity:
    AppCompatActivity(),
    OnClickListeners,
    LiveDataObservers{

    private var viewModel: RelayViewModel? = null
    private lateinit var fabConfirm: ExtendedFloatingActionButton
    private lateinit var fabCancel: ExtendedFloatingActionButton
    private lateinit var etName: EditText
    private lateinit var etIp: EditText
    private var id: Long = 0
    private var on: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_relay_activity)

        viewModel = ViewModelProvider(this).get(RelayViewModel::class.java)

        id =  intent.getStringExtra("id")!!.toLong()
        on = intent.getStringExtra("on")!!.toBoolean()
        val name = intent.getStringExtra("name")
        val ip = intent.getStringExtra("ip")

        etName = findViewById(R.id.details_relay_name)
        etIp = findViewById(R.id.details_relay_ip)
        fabConfirm = findViewById(R.id.details_relay_fab_confirm)
        fabCancel = findViewById(R.id.details_relay_fab_cancel)

        etName.setText(name)
        etIp.setText(ip)

        initLiveDataObservers()
        initOnClickListeners()
    }

    override fun initLiveDataObservers() {
        viewModel?.status?.observe(this){
            when(it.status){
                Resource.Status.LOADING -> {
                    fabConfirm.hide()
                    fabCancel.hide()
                }

                Resource.Status.SUCCESS -> {
                    fabConfirm.show()
                    fabCancel.show()
                    toast(it.message)
                    finish()
                }

                Resource.Status.ERROR -> {
                    toast(it.message)
                }
            }
        }
    }

    override fun initOnClickListeners(){
        fabConfirm.setOnClickListener{
            viewModel?.update(id, Relay(etName.text.toString(), etIp.text.toString(), on))
        }
        fabCancel.setOnClickListener{finish()}
    }

    private fun toast(message: String?){
        if(message != null && message.isNotEmpty())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}