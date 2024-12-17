package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment



class FragmentA : Fragment(){
    interface EventHandler {
        fun onNavigateToBClicked()
    }

    var eventHandler: EventHandler? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("Instances", "Content in fragment A: $context")

        require(context is EventHandler){
            "Activity must implement event handler"
        }

        eventHandler = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navigateButton = view.findViewById<View>(R.id.navigate_button)
        navigateButton.setOnClickListener{
            Log.d("Preview", "It is clicked")

            eventHandler?.onNavigateToBClicked()
        }

    }
}