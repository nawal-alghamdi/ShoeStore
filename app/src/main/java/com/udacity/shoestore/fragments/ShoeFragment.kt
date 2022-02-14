package com.udacity.shoestore.fragments

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeBinding
import com.udacity.shoestore.models.ShoeViewModel


class ShoeFragment : Fragment() {

    private val sharedViewModel: ShoeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentShoeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shoe, container, false)

        binding.fab.setOnClickListener {
            findNavController().navigate(ShoeFragmentDirections.actionShoeFragmentToShoeDetailsFragment())
        }

        setHasOptionsMenu(true)

        sharedViewModel.shoeList.observe(viewLifecycleOwner, { shoesList ->
            val linearLayout = binding.linearLayout
            if (shoesList != null) {
                for (element in shoesList) {
                    val cardView = context?.let { CardView(it) }
                    sharedViewModel.initCardView(cardView, linearLayout)

                    val verticalLinear = LinearLayout(context)
                    verticalLinear.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    verticalLinear.orientation = LinearLayout.VERTICAL
                    cardView?.addView(verticalLinear)

                    val brandAndName = TextView(context)
                    sharedViewModel.initTextView(
                        brandAndName, verticalLinear,
                        element.company + " " + element.name
                    )

                    val sizeText = TextView(context)
                    sharedViewModel.initTextView(
                        sizeText, verticalLinear,
                        getString(R.string.size, element.size.toString())
                    )

                    val descriptionText = TextView(context)
                    sharedViewModel.initTextView(
                        descriptionText,
                        verticalLinear,
                        element.description
                    )
                }
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_item -> {
                view?.findNavController()
                    ?.navigate(ShoeFragmentDirections.actionShoeFragmentToLoginFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}