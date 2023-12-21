package com.renhuan.rhutils.http

import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.lang.reflect.Type

/**
 *
 * time : 2021/2/1 16:54
 * describe : data 为对象的时候
 */
@Parser(name = "Response")
open class ResponseParser<T> : TypeParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    override fun onParse(response: Response): T {

        val data: BaseResponseModel<T> = response.convertTo(BaseResponseModel::class.java, *types)

        //判断我们传入的泛型是String对象，就给data赋值""字符串，确保data不为null
        if (data.data == null && types[0] === String::class.java) {
            data.data = "" as T
        }

        //code不等于0说明数据不正确，抛出异常
        if (data.errorCode != Api.CODE) {
            throw ParseException(data.errorCode.toString(), data.msg, response)
        }
        return data.data
    }
}