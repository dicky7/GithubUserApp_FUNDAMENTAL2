package com.example.githubuserapp
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.PersonAdapter
import com.example.githubuserapp.adapter.listUserData
import com.example.githubuserapp.model.Person
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var list: ArrayList<Person> = arrayListOf()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonAdapter
    var API_KEY: String = BuildConfig.API_KEY
    var BASE_URL: String = BuildConfig.BASE_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = PersonAdapter(list)
        getList()
        searchUser()

    }
    private fun showRecyclerList(){
        binding.rvGithub.layoutManager = LinearLayoutManager(this@MainActivity)
        val listUserAdapter = PersonAdapter(listUserData)
        binding.rvGithub.adapter = listUserAdapter
    }
    private fun showLoading(boolean: Boolean){
        if (boolean){
            binding.progressBar.visibility = View.VISIBLE
        }
        else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getList(){
        showLoading(true)
        val asyncHttpClient = AsyncHttpClient()
        asyncHttpClient.addHeader("Authorization", "token $API_KEY")
        asyncHttpClient.addHeader("User-Agent", "request")
        asyncHttpClient.get(BASE_URL, object : AsyncHttpResponseHandler() {
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
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                showLoading(false)
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getListData(idUser: String) {
        showLoading(true)
        val asyncHttpClient = AsyncHttpClient()
        asyncHttpClient.addHeader("Authorization","token $API_KEY")
        asyncHttpClient.addHeader("User-Agent","request")
        val url = "$BASE_URL/$idUser"
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
                    list.add(person)
                    showRecyclerList()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()

                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                showLoading(false)
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun searchUser(){
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query.isNullOrEmpty()){
                    return true
                }
                else{
                    list.clear()
                    getSearchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }
        })
    }

    private fun getSearchUser(idUser: String){
        showLoading(true)
        val asyncHttpClient = AsyncHttpClient()
        asyncHttpClient.addHeader("Authorization","token $API_KEY")
        asyncHttpClient.addHeader("User-Agent","request")
        val url = "https://api.github.com/search/users?q=$idUser"
        asyncHttpClient.get(url,object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                showLoading(false)
                val res = String(responseBody)
                try {
                    val jsArry = JSONObject(res)
                    val items = jsArry.getJSONArray("items")
                    for (position in 0 until items.length()){
                        val jsObj = items.getJSONObject(position)
                        val  username: String = jsObj.getString("login")
                        getListData(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                showLoading(false)
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_language){
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}


