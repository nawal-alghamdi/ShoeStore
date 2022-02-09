package com.udacity.shoestore.models

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoeViewModel : ViewModel() {

    private var _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>> = _shoeList

    private val _eventSaveShoe = MutableLiveData<Boolean>()
    val eventSaveShoe: LiveData<Boolean>
        get() = _eventSaveShoe

    init {
        _shoeList.value = ArrayList()
    }

    fun addShoe(name: String, size: Double, company: String, description: String) {
        _shoeList.value?.add(Shoe(name, size, company, description))

    }

    fun onSaveShoe() {
        //the logic of save that will add the shoe to the list of shoeViewModel
        _eventSaveShoe.value = true
    }

    fun onSaveShoeComplete() {
        _eventSaveShoe.value = false
    }

    fun initTextView(textView: TextView, parentView: LinearLayout, textValue: String) {
        // TODO 4: Is it ok to  pass a context to a viewModel function? ex: in this fun, not to the viewModel itself
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textView.text = textValue
        parentView.addView(textView)
    }

    fun initCardView(cardView: CardView?, parentView: LinearLayout) {
        cardView?.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        cardView?.radius = 6F
        cardView?.cardElevation = 2F
        cardView?.setContentPadding(30, 20, 30, 20)
        val cardViewMarginParams = cardView?.layoutParams as ViewGroup.MarginLayoutParams
        cardViewMarginParams.setMargins(0, 0, 0, 4)
        cardView.requestLayout()
        parentView.addView(cardView)
    }

}