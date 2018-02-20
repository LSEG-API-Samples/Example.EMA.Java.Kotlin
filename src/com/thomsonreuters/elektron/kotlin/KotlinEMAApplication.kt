package com.thomsonreuters.elektron.kotlin

import com.thomsonreuters.ema.access.Msg;
import com.thomsonreuters.ema.access.AckMsg;
import com.thomsonreuters.ema.access.GenericMsg;
import com.thomsonreuters.ema.access.RefreshMsg;
import com.thomsonreuters.ema.access.ReqMsg;
import com.thomsonreuters.ema.access.StatusMsg;
import com.thomsonreuters.ema.access.UpdateMsg;
import com.thomsonreuters.ema.access.EmaFactory;
import com.thomsonreuters.ema.access.OmmConsumer;
import com.thomsonreuters.ema.access.OmmConsumerClient;
import com.thomsonreuters.ema.access.OmmConsumerConfig;
import com.thomsonreuters.ema.access.OmmConsumerEvent;
import com.thomsonreuters.ema.access.OmmException;

class Appclient : OmmConsumerClient {
    override fun onRefreshMsg(refreshMsg: RefreshMsg, event: OmmConsumerEvent) {
        println(refreshMsg)
    }

    override fun onUpdateMsg(updateMsg: UpdateMsg, event: OmmConsumerEvent) {
        println(updateMsg)
    }

    override fun onStatusMsg(statusMsg: StatusMsg, event: OmmConsumerEvent) {
        println(statusMsg)
    }

    override fun onGenericMsg(genericMsg: GenericMsg, event: OmmConsumerEvent) {
        println(genericMsg)
    }

    override fun onAckMsg(ackMsg: AckMsg, event: OmmConsumerEvent) {
        println(ackMsg)
    }

    override fun onAllMsg(msg: Msg, event: OmmConsumerEvent) {
        println(msg)
    }
}

fun main(args: Array<String>) {


    lateinit var consumer: OmmConsumer
    lateinit  var config: OmmConsumerConfig
    var appClient = Appclient()
    lateinit var reqMsg:ReqMsg
    try {
        println("Hello this is main")

        config = EmaFactory.createOmmConsumerConfig()
        consumer = EmaFactory.createOmmConsumer((config.host("172.20.33.30:14002").username("rdc")))

        reqMsg = EmaFactory.createReqMsg();

        consumer.registerClient(reqMsg.serviceName("ELEKTRON_DD").name("EUR="), appClient)

        Thread.sleep(60000)

    } catch (excp: InterruptedException) {
        println(excp.message)
    } catch (excp: OmmException) {
        println(excp.message)
    }finally {
        consumer?.let {
            consumer.uninitialize()
        }
    }
}

