package com.example.composetoy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    var colorList= listOf(Color.Blue,Color.DarkGray,Color.Cyan,Color.LightGray,Color.Black)
    var fontColorList= listOf(Color.White,Color.White,Color.Black,Color.Black,Color.White)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var num by remember {
                mutableStateOf(1)
            }
            val scoreList = remember {
                mutableStateListOf(0)
            }
            var big by remember {
                mutableStateOf(false)
            }
            val anim = remember {
                Animatable(40, Int.VectorConverter)
            }
            LaunchedEffect(big){
                anim.animateTo(if(big) 80 else 40)
            }
            val animCorner = remember {
                Animatable(12, Int.VectorConverter)
            }
            LaunchedEffect(big){
                animCorner.animateTo(if(big) 20 else 16)
            }
            val changeSize = remember(big) {
                if(big) "缩小字体" else "增大字体"
            }
            val changeIcon= remember(big) {
                if (big) Icons.Rounded.ArrowDropDown else Icons.Rounded.KeyboardArrowUp
            }


            LazyColumn(Modifier.animateContentSize(),content={
                item{
                    Text(modifier = Modifier.padding(10.dp),text = "计分", fontSize = 24.sp)
                    Row(Modifier.padding(5.dp)) {
                        Button(modifier=Modifier.padding(2.dp,0.dp,0.dp,0.dp),onClick =
                        {
                            num++
                            scoreList.add(0)


                        }) {
                            Text(text = "添加")
                        }
                        Button(modifier=Modifier.padding(2.dp,0.dp,0.dp,0.dp),onClick =
                        {
                            if(num!=0){
                                num--
                                scoreList.removeLast()
                            }

                        }) {
                            Text(text = "去除")
                        }
                        Button(modifier=Modifier.padding(2.dp,0.dp,0.dp,0.dp),onClick =
                        {
                        big=!big

                        }) {
                            Icon(changeIcon, contentDescription ="icon" )
                            Text(text = changeSize)
                        }
                    }
                }
                for(i in 0 until num){
                    item{
                        var colorType by remember {
                            mutableStateOf(i)
                        }
                        Row(
                            Modifier.padding(5.dp)
                                .clip(RoundedCornerShape(topStart = animCorner.value.dp, bottomEnd = animCorner.value.dp ))
                                .background(colorList[colorType % 5])
                                .clickable { colorType++ }

                                ) {
                            Text(text = "玩家${i+1} ",
                                Modifier
                                    .padding(10.dp, 10.dp, 0.dp, 0.dp)
                                    .height(anim.value.dp)
                                    .width(76.dp), fontSize = ( anim.value/4+4 ).sp, color = fontColorList[colorType%5])
                            Text(text = "得分：${scoreList[i]}",
                                Modifier
                                    .padding(2.dp, 10.dp, 0.dp, 0.dp)
                                    .height(anim.value.dp)
                                    .width(120.dp), fontSize = ( anim.value/4+4 ).sp, color = fontColorList[colorType%5])
                            Button(onClick = {scoreList[i]++},
                                Modifier
                                    .padding(2.dp)
                                    .width(52.dp)) {
                                Text(text = "+", fontSize = ( anim.value/4+6 ).sp)
                            }
                            Button(onClick = {scoreList[i]--},
                                Modifier.padding(2.dp)
                                    .width(52.dp)) {
                                Text(text = "-", fontSize = ( anim.value/4+6 ).sp)
                            }
                            Button(onClick = {scoreList[i]=0},
                                Modifier
                                    .padding(2.dp)
                                    .width(60.dp)) {
                                Text(text = "清零", fontSize = 14.sp)
                            }

                        }
                    }


                }
            })


        }
    }
}

