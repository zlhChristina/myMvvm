package com.zlh.huahua.myproject.model

data class LoginModel(val needPerfect:Int,val sessionId:String,val userId:Int,
                      val nickName:String, val icon:String,val role:Int,val needAccount:Int ) {
}