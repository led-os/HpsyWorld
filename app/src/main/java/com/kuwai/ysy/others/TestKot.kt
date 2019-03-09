package com.kuwai.ysy.others

import android.content.Context
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes

public class TestKot : View{

    constructor(context:Context,attrs:AttributeSet?):this(context,attrs,0){

    }

    constructor(context : Context, attrs : AttributeSet?, defStyleAttr : Int) : super(context, attrs, defStyleAttr) {

    }

    val array = arrayListOf("1","2","3")

    fun getArray(){
        for (i in array.indices){
            println(array[i])
        }

        for (i in IntRange(1,array.size-1)){
            println(array[i])
        }

        for (text in array){
            println(text)
        }

        var  count = 8;
        when(count){
            0 ->{
              println(count)
            }
            1 ->{
               println(count)
            }

            in 1..3 ->{
                println(count)
            }

            else ->{
                println(count)
            }

        }

        when(count){
            1-> println(count)
            in 1..3 -> print(count)
            else -> print(count)
        }

        var person = Person("Android轮子哥", 100)
        person.name = "HJQ"
        person.age = 50
        println("name: {$person.name}, age: {$person.age}")

    }

}

class Person(var name : String, var age : Int)