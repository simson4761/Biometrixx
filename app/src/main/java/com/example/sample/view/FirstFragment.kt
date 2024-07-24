package com.example.sample.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import com.example.sample.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel : com.example.sample.viewmodel.ViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)








        binding.buttonFirst.setOnClickListener {
            authenticate()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer { updatedText ->
            binding.statusText.text = updatedText.statusText
        })


    }

    private fun authenticate() {

//        val biometricManager = BiometricManager.from(requireContext())

        val biometricPrompt = BiometricPrompt(
            requireActivity(),
            ContextCompat.getMainExecutor(requireContext()),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    Toast.makeText(requireContext(),
                        "Authentication Success", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.updateStatusText("Authentication Success")
                    //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    when (errorCode) {
                        BiometricPrompt.ERROR_HW_UNAVAILABLE -> {
                            Toast.makeText(requireContext(), "Biometric hardware unavailable", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("Authentication Success")

                        }
                        BiometricPrompt.ERROR_UNABLE_TO_PROCESS -> {
                            Toast.makeText(requireContext(), "Unable to process biometric data", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("Unable to process biometric data")
                        }
                        BiometricPrompt.ERROR_TIMEOUT -> {
                            Toast.makeText(requireContext(), "Biometric operation timed out", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("Biometric operation timed out")
                        }
                        BiometricPrompt.ERROR_NO_SPACE -> {
                            Toast.makeText(requireContext(), "No space available to process biometric", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("No space available to process biometric")
                        }
                        BiometricPrompt.ERROR_CANCELED -> {
                            Toast.makeText(requireContext(), "Biometric operation canceled", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("Biometric operation canceled")
                        }
                        BiometricPrompt.ERROR_LOCKOUT -> {
                            Toast.makeText(requireContext(), "Too many attempts. Try again later.", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("Too many attempts. Try again later.")
                        }
                        BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> {
                            Toast.makeText(requireContext(), "Biometric operation permanently locked out", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("Biometric operation permanently locked out")
                        }
                        BiometricPrompt.ERROR_USER_CANCELED -> {
                            Toast.makeText(requireContext(), "User canceled the operation", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("User canceled the operation")
                        }
                        BiometricPrompt.ERROR_NO_BIOMETRICS -> {
                            Toast.makeText(requireContext(), "No biometric features available on this device", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("No biometric features available on this device")
                        }
                        BiometricPrompt.ERROR_HW_NOT_PRESENT -> {
                            Toast.makeText(requireContext(), "Biometric hardware not present", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("Biometric hardware not present")
                        }
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {
                            Toast.makeText(requireContext(), "User clicked negative button", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("User clicked negative button")
                        }
                        BiometricPrompt.ERROR_VENDOR -> {
                            Toast.makeText(requireContext(), "Vendor-specific error", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("Vendor-specific error")
                        }
                        else -> {
                            Toast.makeText(requireContext(), "Unknown error occurred: $errString", Toast.LENGTH_SHORT).show()
                            viewModel.updateStatusText("Unknown error occurred: $errString")
                        }
                    }
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or BIOMETRIC_WEAK)

        biometricPrompt.authenticate(promptInfo.build())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}