package com.study.trajectory_semifinal.model

class ServiceViewInfo(private val info:ServiceInfo){
    var icon:String? = null
    var name:String? = null
    init{
        this.icon = info.icon_url
        this.name = info.name
    }
}