package com.rock.iscreenblsdk
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rock.iscreenblsdk.databinding.FragmentFirstBinding
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.utils.openiScreenContentFromBl
import com.rockstreamer.iscreensdk.utils.openiScreenSDK

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class FirstFragment : Fragment(), oniScreenPremiumCallBack {

    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.buttonOpenContent.setOnClickListener {
            requireActivity().openiScreenSDK(this)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            requireActivity().openiScreenContentFromBl(id = "840-series", type = "series", this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPremiumContentClick(isPremium: Boolean) {
        Log.d("APP_STATUS", "isPremium $isPremium")
    }

    override fun onTokenInvalid(tokenValid: Boolean) {

    }
}