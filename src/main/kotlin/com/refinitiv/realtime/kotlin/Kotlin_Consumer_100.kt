//|-----------------------------------------------------------------------------
//|            This source code is provided under the Apache 2.0 license      --
//|  and is provided AS IS with no warranty or guarantee of fit for purpose.  --
//|                See the project's LICENSE.md for details.                  --
//|            Copyright (C) 2017-2020 Refinitiv. All rights reserved.        --
//|-----------------------------------------------------------------------------

package com.refinitiv.realtime.kotlin

import com.refinitiv.ema.access.Msg
import com.refinitiv.ema.access.AckMsg
import com.refinitiv.ema.access.GenericMsg
import com.refinitiv.ema.access.RefreshMsg
import com.refinitiv.ema.access.ReqMsg
import com.refinitiv.ema.access.StatusMsg
import com.refinitiv.ema.access.UpdateMsg
import com.refinitiv.ema.access.EmaFactory
import com.refinitiv.ema.access.OmmConsumer
import com.refinitiv.ema.access.OmmConsumerClient
import com.refinitiv.ema.access.OmmConsumerConfig
import com.refinitiv.ema.access.OmmConsumerEvent
import com.refinitiv.ema.access.OmmException

//Client class, implements OmmConsumerClient interface
class Appclient : OmmConsumerClient {

    override fun onRefreshMsg(refreshMsg: RefreshMsg, event: OmmConsumerEvent) {
        println("Kotlin_Consumer_100: Receive Market Price Refresh message")
        println(refreshMsg)
    }

    override fun onUpdateMsg(updateMsg: UpdateMsg, event: OmmConsumerEvent) {
        println("Kotlin_Consumer_100: Receive Market Price Update message")
        println(updateMsg)
    }

    override fun onStatusMsg(statusMsg: StatusMsg, event: OmmConsumerEvent) {
        println("Kotlin_Consumer_100: Receive Market Price Status message")
        println(statusMsg)
    }

    override fun onGenericMsg(genericMsg: GenericMsg, event: OmmConsumerEvent) {
        println(genericMsg)
    }

    override fun onAckMsg(ackMsg: AckMsg, event: OmmConsumerEvent) {
        println(ackMsg)
    }

    override fun onAllMsg(msg: Msg, event: OmmConsumerEvent) {
        //println(msg)
    }
}

fun main(args: Array<String>) {
    lateinit var consumer: OmmConsumer
    lateinit  var config: OmmConsumerConfig
    val appClient = Appclient()
    lateinit var reqMsg:ReqMsg
    try {
        println("Starting Kotlin_Consumer_100 application")

        //OmmConsumerConfig creation
        config = EmaFactory.createOmmConsumerConfig()

        //OmmConsumer creation and establish communication.
        consumer = EmaFactory.createOmmConsumer((config.host("localhost:14002").username("kotlin")))

        //ReqMsg creation
        reqMsg = EmaFactory.createReqMsg()

        println("Kotlin_Consumer_100: Send item request message")
        consumer.registerClient(reqMsg.serviceName("DIRECT_FEED").name("EUR="), appClient) //Subscribe for EUR= RIC from DIRECT_FEED service

        Thread.sleep(60000)

    } catch (excp: InterruptedException) {
        println(excp.message)
    } catch (excp: OmmException) {
        println(excp.message)
    }finally {
        /*consumer?.let {
            consumer.uninitialize()
        }*/
        consumer.uninitialize()
    }

}
