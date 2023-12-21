package com.renhuan.rhutils.http

/**
 * 
 * time : 2021/2/1 16:54
 * describe : 数据基础模型
 *  *   {
 *     "code": 0,
 *     "msg": "成功",
 *     "data":
 *     {
 *
 *      }
 *   }
 */
data class BaseResponseModel<T>(var errorCode: Int, var msg: String, var data: T)