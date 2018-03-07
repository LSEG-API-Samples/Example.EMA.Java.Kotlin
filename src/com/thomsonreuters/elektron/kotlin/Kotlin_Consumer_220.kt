package com.thomsonreuters.elektron.kotlin

import java.util.Iterator;
import com.thomsonreuters.ema.access.Msg;
import com.thomsonreuters.ema.access.AckMsg;
import com.thomsonreuters.ema.access.GenericMsg;
import com.thomsonreuters.ema.access.RefreshMsg;
import com.thomsonreuters.ema.access.StatusMsg;
import com.thomsonreuters.ema.access.UpdateMsg;
import com.thomsonreuters.ema.access.Data;
import com.thomsonreuters.ema.access.DataType;
import com.thomsonreuters.ema.access.DataType.DataTypes;
import com.thomsonreuters.ema.access.EmaFactory;
import com.thomsonreuters.ema.access.FieldEntry;
import com.thomsonreuters.ema.access.FieldList;
import com.thomsonreuters.ema.access.OmmConsumer;
import com.thomsonreuters.ema.access.OmmConsumerClient;
import com.thomsonreuters.ema.access.OmmConsumerEvent;
import com.thomsonreuters.ema.access.OmmException;

class AppclientFieldListWalk : OmmConsumerClient {
    override fun onRefreshMsg(refreshMsg: RefreshMsg, event: OmmConsumerEvent): Unit {

        if (refreshMsg.hasName()) println("Refresh: Item Name: ${refreshMsg.name()}")

        if (refreshMsg.hasServiceName()) println("Refresh: Service Name: ${refreshMsg.serviceName()}")

        println("Refresh:  Item State: ${refreshMsg.state()}")

        if(DataType.DataTypes.FIELD_LIST == refreshMsg.payload().dataType()) decode(refreshMsg.payload().fieldList())

        println("")
    }

    override fun onUpdateMsg(updateMsg: UpdateMsg, event: OmmConsumerEvent): Unit {

        if (updateMsg.hasName()) println("Update: Item Name: ${updateMsg.name()}")

        if (updateMsg.hasServiceName()) println("Update: Service Name: ${updateMsg.serviceName()}")

        if(DataType.DataTypes.FIELD_LIST == updateMsg.payload().dataType()) decode(updateMsg.payload().fieldList())

        println("")
    }

    override fun onStatusMsg(statusMsg: StatusMsg, event: OmmConsumerEvent): Unit {

        if (statusMsg.hasName()) println("Status: Item Name: ${statusMsg.name()}")

        if (statusMsg.hasServiceName()) println("Status: Service Name: ${statusMsg.serviceName()}")

        if(statusMsg.hasState()) println("Status: Item State: ${statusMsg.state()}")
    }

    override fun onGenericMsg(genericMsg: GenericMsg, event: OmmConsumerEvent): Unit {}

    override fun onAckMsg(ackMsg: AckMsg, event: OmmConsumerEvent): Unit {}

    override fun onAllMsg(msg: Msg, event: OmmConsumerEvent): Unit {}

    fun decode(fieldList: FieldList): Unit {
        for (fieldEntry: FieldEntry in fieldList) {
            print("Fid: ${fieldEntry.fieldId()} Name = ${fieldEntry.name()} DataType: ${DataType.asString(fieldEntry.load().dataType())} Value: ")

            if (fieldEntry.code() == Data.DataCode.BLANK) {
                println(" blank")
            } else {
                when (fieldEntry.loadType()) {
                    DataTypes.REAL -> println(fieldEntry.real().asDouble())
                    DataTypes.DATE -> println("${fieldEntry.date().day()} / ${fieldEntry.date().month()} / ${fieldEntry.date().year()}")
                    DataTypes.TIME -> println("${fieldEntry.time().hour()} : ${fieldEntry.time().minute()} : ${fieldEntry.time().second()} : ${fieldEntry.time().millisecond()}")
                    DataTypes.INT -> println(fieldEntry.intValue())
                    DataTypes.UINT -> println(fieldEntry.uintValue())
                    DataTypes.ASCII -> println(fieldEntry.ascii())
                    DataTypes.ENUM -> println("${if(fieldEntry.hasEnumDisplay()) fieldEntry.enumDisplay() else fieldEntry.enumValue() }")
                    DataTypes.ERROR -> println("(${fieldEntry.error().errorCodeAsString()})")
                    else -> {
                        println("")
                    }
                }
            }
        }
    }

}

fun main(args: Array<String>) {
    lateinit var consumer: OmmConsumer
    //lateinit  var config: OmmConsumerConfig
    val appClient = AppclientFieldListWalk()
    //lateinit var reqMsg:ReqMsg
    try {
        println("Starting Kotlin_Consumer_220 application")

        //config = EmaFactory.createOmmConsumerConfig()
        //consumer = EmaFactory.createOmmConsumer(EmaFactory.createOmmConsumerConfig().host("localhost:14002").username("kotlin"))
        consumer = EmaFactory.createOmmConsumer(EmaFactory.createOmmConsumerConfig().consumerName("Consumer_1"))
        //reqMsg = EmaFactory.createReqMsg();
        println("Kotlin_Consumer_220: Send item request message")
        consumer.registerClient(EmaFactory.createReqMsg().serviceName("DIRECT_FEED").name("TRI.N"), appClient)

        Thread.sleep(60000)

    } catch (excp: InterruptedException) {
        println(excp.message)
    } catch (excp: OmmException) {
        println(excp.message)
    } finally {
        /*consumer?.let {
            consumer.uninitialize()
        }*/
        consumer.uninitialize()
    }
}