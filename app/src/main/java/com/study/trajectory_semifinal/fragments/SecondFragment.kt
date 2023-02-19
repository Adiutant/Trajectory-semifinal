package com.study.trajectory_semifinal.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.study.trajectory_semifinal.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.study.trajectory_semifinal.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var binding: FragmentSecondBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val serviceName = requireView().findViewById<TextView>(R.id.name_field)
        val serviceDescription = requireView().findViewById<TextView>(R.id.description_field)
        val serviceImageHolder = requireView().findViewById<ImageView>(R.id.icon_view)
        val serviceUrl = requireView().findViewById<TextView>(R.id.url_field)
        val bundle = arguments ?: return
        serviceName.text = bundle.getString("name")
        serviceDescription.text = bundle.getString("description")
        Glide.with(requireContext())
            .load(bundle.getString("icon_url"))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(serviceImageHolder)
        serviceUrl.text = bundle.getString("service_url")
        val callback = requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
            val nav = Navigation.findNavController(
                requireActivity(),
                R.id.nav_host_fragment_content_main
            )
            nav.safeNavigate(R.id.SecondFragment,R.id.action_SecondFragment_to_FirstFragment)
        }
    }
    private fun NavController.safeNavigate(
        @IdRes currentDestinationId: Int,
        @IdRes id: Int,
        args: Bundle? = null
    ) {
        if (currentDestinationId == currentDestination?.id) {
            navigate(id, args)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}