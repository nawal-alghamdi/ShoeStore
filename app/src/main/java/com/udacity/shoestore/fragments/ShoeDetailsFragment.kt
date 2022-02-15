package com.udacity.shoestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentShoeDetailsBinding
import com.udacity.shoestore.models.ShoeViewModel


class ShoeDetailsFragment : Fragment() {

    private val sharedViewModel: ShoeViewModel by activityViewModels()

    /**
    Binding object instance corresponding to the fragment_shoe_details.xml layout
    This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    when the view hierarchy is attached to the fragment.
     */
    private var binding: FragmentShoeDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentShoeDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            // Specify the fragment as the lifecycle owner and by setting the lifecycle owner, the app will be able to observe LiveData objects
            lifecycleOwner = viewLifecycleOwner

            // Because I have a data variable called viewModel, and I must assign my sharedViewModel to it
            viewModel = sharedViewModel

            // Assign the fragment with "this @ShoeDetailsFragment" because inside the binding?.apply block,
            // the keyword this refers to the binding instance, not the fragment instance.
            shoeDetailsFragment = this@ShoeDetailsFragment
        }

        // Observe the value of eventSaveShoe and check if it's set to true
        binding?.viewModel?.eventSaveShoe?.observe(viewLifecycleOwner, { save ->
            if (save) {
                val name = sharedViewModel.shoeNameValue.value?.trim()
                val size = sharedViewModel.shoeSizeValue.value?.trim()
                val company = sharedViewModel.shoeCompanyValue.value?.trim()
                val description = sharedViewModel.shoeDescriptionValue.value?.trim()

                if (name?.isNotEmpty() == true && size?.isNotEmpty() == true &&
                    company?.isNotEmpty() == true && description?.isNotEmpty() == true
                ) {
                    sharedViewModel.addShoe(name, size.toDouble(), company, description)
                    findNavController().navigate(ShoeDetailsFragmentDirections.actionShoeDetailsFragmentToShoeFragment())
                } else {
                    Toast.makeText(
                        context,
                        "Please fill all the details for the shoe",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                sharedViewModel.onSaveShoeComplete()
            }
        })

    }

    fun cancelSavingTheShoe() {
        findNavController().navigate(ShoeDetailsFragmentDirections.actionShoeDetailsFragmentToShoeFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        sharedViewModel.destroyLiveDateValues()
    }

}