package com.thomsonreuters.elektron.kotlin

import com.thomsonreuters.ema.access.Msg
import com.thomsonreuters.ema.access.AckMsg
import com.thomsonreuters.ema.access.GenericMsg
import com.thomsonreuters.ema.access.RefreshMsg
import com.thomsonreuters.ema.access.ReqMsg
import com.thomsonreuters.ema.access.StatusMsg
import com.thomsonreuters.ema.access.UpdateMsg
import com.thomsonreuters.ema.access.EmaFactory
import com.thomsonreuters.ema.access.OmmConsumer
import com.thomsonreuters.ema.access.OmmConsumerClient
import com.thomsonreuters.ema.access.OmmConsumerConfig
import com.thomsonreuters.ema.access.OmmConsumerEvent
import com.thomsonreuters.ema.access.OmmException

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

        config = EmaFactory.createOmmConsumerConfig()
        consumer = EmaFactory.createOmmConsumer((config.host("localhost:14002").username("rdc")))

        reqMsg = EmaFactory.createReqMsg();

        println("Kotlin_Consumer_100: Send item request message")
        consumer.registerClient(reqMsg.serviceName("DIRECT_FEED").name("TRI.N"), appClient)

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
