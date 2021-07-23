package com.example.githubuserapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.*
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.databinding.FragmentFollowingBinding
import com.example.githubuserapp.model.Person
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowingFragment : Fragment() {
    companion object{
        const val EXTRA_PERSON1 = "person"
    }
    private var listData: ArrayList<Person> = ArrayList()
    private lateinit var binding: FragmentFollowingBinding
    var API_KEY: String = BuildConfig.API_KEY
    var BASE_URL: String = BuildConfig.BASE_URL
    private lateinit var adapter: FollowingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FollowingAdapter(listData)
        listData.clear()
        val followingData = activity!!.intent.getParcelableExtra<Person>(EXTRA_PERSON1) as Person
        getList(followingData.login_username.toString())
    }
    private fun showRecyclerList(){
        binding.rvFolowing.layoutManager = LinearLayoutManager(activity)
        val listUserAdapter = PersonAdapter(listFollowingData)
        binding.rvFolowing.adapter = listUserAdapter
    }

    private fun showLoading(boolean: Boolean){
        if (boolean){
            binding.progressbarFollowing.visibility = View.VISIBLE
        }
        else{
            binding.progressbarFollowing.visibility = View.GONE
        }
    }

    private fun getList(dat: String){
        showLoading(true)
        val asyncHttpClient = AsyncHttpClient()
        asyncHttpClient.addHeader("Authorization", "token $API_KEY")
        asyncHttpClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$dat/following"
        asyncHttpClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                showLoading(false)
                val res = responseBody?.let { String(it) }
                try {
                    val jsonArry = JSONArray(res)
                    for (position in 0 until jsonArry.length()) {
                        val jsonObj = jsonArry.getJSONObject(position)
                        val username: String = jsonObj.getString("login")
                        getListData(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                showLoading(false)
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getListData(idUser: String) {
        showLoading(true)
        val asyncHttpClient = AsyncHttpClient()
        asyncHttpClient.addHeader("Authorization","token $API_KEY")
        asyncHttpClient.addHeader("User-Agent","request")
        val url = "https://api.github.com/users/$idUser"
        asyncHttpClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                showLoading(false)
                val res = responseBody?.let { String(it) }
                try {
                    val person = Person()
                    val jsonObj = JSONObject(res)
                    person.login_username = jsonObj.getString("login").toString()
                    person.avatar_url = jsonObj.getString("avatar_url").toString()
                    person.company = jsonObj.getString("company").toString()
                    person.location = jsonObj.getString("location").toString()
                    person.repository = jsonObj.getInt("public_repos")
                    person.follower = jsonObj.getInt("followers")
                    person.following  = jsonObj.getInt("following")
                    listData.add(person)
                    showRecyclerList()
                } catch (e: Exception) {
                    if (context != null) {
                        Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                    }
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                showLoading(false)
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}