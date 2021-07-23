package com.example.githubuserapp

import com.example.githubuserapp.model.Person

object PersonData {
    private val nameData = arrayOf(" Elon Musk",
        "Jeff Bezos",
        "Mark Zuckerberg",
        "Tim Cook",
        "Sundar Pichai",
        "Evan Spiegel",
        "Bill Gates",
        "Nadiem Makarim",
        "William Tanuwijaya",
        "Achmad Zaky")

    private val usernameData = arrayOf("ElonMsk",
        "JeffBZ",
        "MarkZkrbg",
        "TimCook123",
        "SundarPic77",
        "EvSpiegel",
        "BillGts0",
        "NadieMkrim",
        "WiliamTz",
        "Achmad_Zk")

    private val imgData = intArrayOf(R.drawable.elon,
        R.drawable.jeff,
    R.drawable.mark,
    R.drawable.tim,
    R.drawable.sundar,
    R.drawable.evan,
    R.drawable.bill,
    R.drawable.nadiem,
    R.drawable.wiliam,
    R.drawable.achmad)

    private val followerData = intArrayOf(11090,11201,22020,1800,25200,11555,23000,5550,3200,1980)
    private val followingData = intArrayOf(1,4,20,15,13,8,5,21,30,50)
    private val repositoryData = intArrayOf(12,33,45,32,55,61,45,54,56,70)
    private val companyData = arrayOf("Tesla","Amazon","Apple","Google","SnapChat","Apple","Microsoft","Gojek","Tokopedia","Bukalapak")
    private val locationData = arrayOf("Texan-USA","Washington-USA","California-USA","Los Angeles-USA","San Francisco-USA","Los Angeles-USA","Washington-USA","Jakarta-Indonesia","Jakarta-Indonesia","Bandung-Indonesia")

    val listData: ArrayList<Person>
        get(){
            val list = arrayListOf<Person>()
            for (i in nameData.indices){
                val person = Person()

                list.add(person)
            }
            return list
    }
}