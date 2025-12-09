package com.rock.iscreenblsdk
import android.content.Context
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
import java.util.Timer

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
            requireActivity().openiScreenContentFromBl(id = "chokro", type = "series", this)

        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener {
            requireActivity().openiScreenSDK(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPremiumContentClick(context: Context, contentId: String, type: String) {

    }

    override fun onTokenInvalid(tokenValid: Boolean) {

    }
}