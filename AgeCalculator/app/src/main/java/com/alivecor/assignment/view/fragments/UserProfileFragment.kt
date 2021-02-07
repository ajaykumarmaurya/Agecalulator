package com.alivecor.assignment.view.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.alivecor.assignment.R
import com.alivecor.assignment.databinding.FragmentUserProfileBinding
import com.alivecor.assignment.viewmodels.UserProfileViewModel
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UserProfileFragment : Fragment() {
    private lateinit var viewModel: UserProfileViewModel
    private lateinit var binding: FragmentUserProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_profile, container, false
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
        checkValidity()

    }

    private fun checkValidity() {
        viewModel.getUser().observe(viewLifecycleOwner) {

            when {
                TextUtils.isEmpty(Objects.requireNonNull(it).name) -> {
                    binding.edtUserName.setError(getString(R.string.enter_your_name))
                    binding.edtUserName.requestFocus()
                }
                TextUtils.isEmpty(Objects.requireNonNull(it).lastName) -> {
                    binding.edtUserLastName.setError(getString(R.string.enter_your_last_name))
                    binding.edtUserLastName.requestFocus()
                }
                TextUtils.isEmpty(Objects.requireNonNull(it).dob) -> {
                    binding.edtDob.setError(getString(R.string.enter_your_date_of_birth))
                    binding.edtUserLastName.requestFocus()
                }
                else -> {

                    findNavController()
                        .navigate(R.id.action_UserProfileFragment_to_UserDetailsFragment)

                }
            }

        }

    }
}