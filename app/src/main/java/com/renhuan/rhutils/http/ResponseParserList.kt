package com.renhuan.rhutils.http

import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.io.IOException
import java.lang.reflect.Type

/**
 *
 * time : 2021/2/1 17:00
 * describe : data 为数组的时候
 *  *   {
 *     "code": 0,
 *     "msg": "成功",
 *     "data":
 *     [
 *      ]
 *   }
 */
@Parser(name = "ResponseList")
open class ResponseParserList<T> : TypeParser<List<T>> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: Response): List<T> {

        val data: BaseResponseModel<List<T>> =
            response.convertTo(BaseResponseModel::class.java, List::class.java, *types)

        if (data.errorCode != Api.CODE) { //code不等于0，说明数据不正确，抛出异常
            throw ParseException(data.errorCode.toString(), data.msg, response)
        }
        return data.data
    }
}