package com.alivecor.assignment.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.alivecor.assignment.R
import com.alivecor.assignment.databinding.FragmentUserProfileDetailsBinding
import com.alivecor.assignment.viewmodels.UserProfileViewModel

/**
 * A simple user details fragment.
 */
class UserDetailsFragment : Fragment() {
    private lateinit var viewModel: UserProfileViewModel
    private lateinit var binding: FragmentUserProfileDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_profile_details, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(requireActivity()).get(UserProfileViewModel::class.java)
        } ?: throw Exception("Invalid Fragment")
        binding.userProfileViewModel = viewModel
        binding.lifecycleOwner = this
        showUserDetails()
        view.findViewById<Button>(R.id.btn_back).setOnClickListener {
            findNavController().navigate(R.id.action_UserDetailsFragment_to_UserProfileFragment)
        }
    }

    /* show user details on screen like name last name */
    private fun showUserDetails() {
        viewModel.getUserDataForDetails().observe(viewLifecycleOwner) {
            val name = getString(R.string.first_name) + " " + it.name
            binding.txtUserName.text = name
            val last_name = getString(R.string.last_name) + " " + it.lastName
            binding.txtUserLastName.text = last_name

        }

    }
}