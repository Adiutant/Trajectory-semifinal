package com.study.trajectory_semifinal.fragments


import androidx.navigation.Navigation.findNavController

import com.study.trajectory_semifinal.model.ServiceViewInfo
import com.study.trajectory_semifinal.adapter.ServiceAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.study.trajectory_semifinal.api.JsonAPI
import com.study.trajectory_semifinal.R
import androidx.recyclerview.widget.LinearLayoutManager
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.View
import androidx.annotation.IdRes
import com.study.trajectory_semifinal.adapter.RecyclerItemClickListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.study.trajectory_semifinal.observers.NetworkStateReceiver
import com.study.trajectory_semifinal.databinding.FragmentFirstBinding
import com.study.trajectory_semifinal.model.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class FirstFragment : Fragment(), Callback<Data?> , Subscriber {
    private var binding: FragmentFirstBinding? = null
    private val services = ArrayList<ServiceViewInfo>()
    private var viewAdapter: ServiceAdapter? = null
    private var servicesView: RecyclerView? = null
    private  var swipeRefreshLayout: SwipeRefreshLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun start() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val BASE_URL = "https://mobile-olympiad-trajectory.hb.bizmrg.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val githubAPI = retrofit.create(JsonAPI::class.java)
        val call = githubAPI.findAll()
        call?.enqueue(this)
    }

    override fun onStart() {
        super.onStart()
        servicesView = requireView().findViewById(R.id.servicesView)
        viewAdapter = ServiceAdapter(requireContext(), services)
        val manager = LinearLayoutManager(requireContext())
        servicesView?.layoutManager = manager
        servicesView?.adapter = viewAdapter
        swipeRefreshLayout = requireView().findViewById(R.id.refreshLayout)
        swipeRefreshLayout?.setOnRefreshListener{
            start()
            swipeRefreshLayout?.isRefreshing = false
        }
        NetworkStateReceiver(requireContext(),this)
        start()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView(response: Data?) {
        services.clear()
        viewAdapter!!.notifyDataSetChanged()
        if (response != null) {
            for (serviceInfo in response.items) {
                services.add(ServiceViewInfo(serviceInfo))
                viewAdapter!!.notifyDataSetChanged()
            }
        }
    }
    override fun onResponse(call: Call<Data?>, response: Response<Data?>) {
        if (response.isSuccessful && response.body() != null) {
            updateRecyclerView(response.body())
            servicesView!!.addOnItemTouchListener(
                RecyclerItemClickListener(
                    activity,
                    servicesView!!,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View?, position: Int) {
                            val nav = findNavController(
                                requireActivity(),
                                R.id.nav_host_fragment_content_main
                            )
                            val bundle = Bundle()
                            bundle.putString("name", response.body()!!.items[position].name)
                            bundle.putString(
                                "description",
                                response.body()!!.items[position].description
                            )
                            bundle.putString("icon_url", response.body()!!.items[position].icon_url)
                            bundle.putString(
                                "service_url",
                                response.body()!!.items[position].service_url
                            )
                            nav.safeNavigate(R.id.FirstFragment,R.id.action_FirstFragment_to_SecondFragment, bundle)
                        }

                        override fun onItemLongClick(view: View?, position: Int) {}
                    })
            )
        }
    }
    private fun showErrorMessage(){
        val mAlertDialogBuilder = AlertDialog.Builder(requireActivity())
        mAlertDialogBuilder.setTitle("Ошибка")
        mAlertDialogBuilder.setIcon(R.mipmap.ic_launcher)
        mAlertDialogBuilder.setMessage("Ошибка запроса к API, проведите вверх, чтобы обновить")
        mAlertDialogBuilder.setCancelable(false);
        mAlertDialogBuilder.setPositiveButton("Ok"){_,_->
        }
        val mAlertDialog = mAlertDialogBuilder.create()
        mAlertDialog.show()
    }
    override fun onFailure(call: Call<Data?>, t: Throwable) {
        showErrorMessage();
    }

    companion object {
        private const val NOTIFY_ID = 101
        private const val CHANNEL_ID = "NOTIFICATION"
    }

    fun NavController.safeNavigate(
        @IdRes currentDestinationId: Int,
        @IdRes id: Int,
        args: Bundle? = null
    ) {
        if (currentDestinationId == currentDestination?.id) {
            navigate(id, args)
        }
    }
    override fun notifyStateChanged(state:Boolean) {
        if (state){
            start();
        } else{
            showErrorMessage();
        }
    }

}