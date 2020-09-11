//|-----------------------------------------------------------------------------
//|            This source code is provided under the Apache 2.0 license      --
//|  and is provided AS IS with no warranty or guarantee of fit for purpose.  --
//|                See the project's LICENSE.md for details.                  --
//|            Copyright (C) 2017-2020 Refinitiv. All rights reserved.        --
//|-----------------------------------------------------------------------------

package com.refinitiv.realtime.kotlin

import com.thomsonreuters.ema.access.EmaFactory
import com.thomsonreuters.ema.access.FieldList
import com.thomsonreuters.ema.access.GenericMsg
import com.thomsonreuters.ema.access.Msg
import com.thomsonreuters.ema.access.OmmException
import com.thomsonreuters.ema.access.OmmIProviderConfig
import com.thomsonreuters.ema.access.OmmProvider
import com.thomsonreuters.ema.access.OmmProviderClient
import com.thomsonreuters.ema.access.OmmProviderEvent
import com.thomsonreuters.ema.access.OmmReal
import com.thomsonreuters.ema.access.OmmState
import com.thomsonreuters.ema.access.PostMsg
import com.thomsonreuters.ema.access.RefreshMsg
import com.thomsonreuters.ema.access.ReqMsg
import com.thomsonreuters.ema.access.StatusMsg
import com.thomsonreuters.ema.access.UpdateMsg
import com.thomsonreuters.ema.rdm.EmaRdm

//Client class, implements OmmProviderClient interface
class IProviderAppClient : OmmProviderClient {

    var itemHandle: Long = 0L // Initial item Handle

    override fun onReqMsg(reqMsg: ReqMsg, providerEvent: OmmProviderEvent) {
        //Verify incoming message type
        when (reqMsg.domainType()) {
            EmaRdm.MMT_LOGIN -> processLoginRequest(reqMsg, providerEvent)
            EmaRdm.MMT_MARKET_PRICE -> processMarketPriceRequest(reqMsg, providerEvent)
            else -> {
                processInvalidItemRequest(reqMsg, providerEvent)
            }
        }
    }

    override fun onRefreshMsg(refreshMsg: RefreshMsg, providerEvent: OmmProviderEvent) {}

    override fun onStatusMsg(statusMsg: StatusMsg, providerEvent: OmmProviderEvent) {}

    override fun onAllMsg(msg: Msg, providerEvent: OmmProviderEvent) {}

    override fun onClose(closeMsg: ReqMsg, providerEvent: OmmProviderEvent) {}

    override fun onGenericMsg(genericMsg: GenericMsg, providerEvent: OmmProviderEvent) {}

    override fun onPostMsg(postMsg: PostMsg, providerEvent: OmmProviderEvent) {}

    override fun onReissue(reissueMsg: ReqMsg, providerEvent: OmmProviderEvent) {}

    fun processLoginRequest(reqMsg: ReqMsg, event: OmmProviderEvent) {

        println("Receive Login Request message from ${reqMsg.name()}, send Login Refresh")
        //Send Login REFESH_RESP message to consumer
        event.provider().submit(
                EmaFactory.createRefreshMsg()
                        .domainType(EmaRdm.MMT_LOGIN)
                        .name(reqMsg.name())
                        .nameType(EmaRdm.USER_NAME)
                        .complete(true)
                        .solicited(true)
                        .state(OmmState.StreamState.OPEN, OmmState.DataState.OK, OmmState.StatusCode.NONE, "Login accept")
                        .attrib(EmaFactory.createElementList())
                , event.handle())
    }

    private fun processMarketPriceRequest(reqMsg: ReqMsg, event: OmmProviderEvent) {

        println("Kotlin_IProvider_200: Receive Market Price Request message")

        if (itemHandle.toInt() != 0) { //if (itemHandle == 0 || itemHandle != event.handle())
            processInvalidItemRequest(reqMsg, event)
            return
        }

        //Creates Market Price Payload and Send Refresh Message
        val fieldList: FieldList = EmaFactory.createFieldList()

        fieldList.add(EmaFactory.createFieldEntry().ascii(3, reqMsg.name()))
        fieldList.add(EmaFactory.createFieldEntry().enumValue(15, 840))
        fieldList.add(EmaFactory.createFieldEntry().real(21, 3900, OmmReal.MagnitudeType.EXPONENT_NEG_2))
        fieldList.add(EmaFactory.createFieldEntry().real(22, 3990, OmmReal.MagnitudeType.EXPONENT_NEG_2))
        fieldList.add(EmaFactory.createFieldEntry().real(25, 3994, OmmReal.MagnitudeType.EXPONENT_NEG_2))
        fieldList.add(EmaFactory.createFieldEntry().real(30, 9, OmmReal.MagnitudeType.EXPONENT_0))
        fieldList.add(EmaFactory.createFieldEntry().real(31, 19, OmmReal.MagnitudeType.EXPONENT_0))

        println("Kotlin_IProvider_200: Send  Market Price Refresh message")
        event.provider().submit(
                EmaFactory.createRefreshMsg()
                        .serviceName(reqMsg.serviceName())
                        .name(reqMsg.name())
                        .state(OmmState.StreamState.OPEN, OmmState.DataState.OK, OmmState.StatusCode.NONE, "Refresh Completed")
                        .solicited(true)
                        .payload(fieldList)
                        .complete(true)
                , event.handle())

        itemHandle = event.handle()
    }

    private fun processInvalidItemRequest(reqMsg: ReqMsg, event: OmmProviderEvent) {
        event.provider().submit(
                EmaFactory.createStatusMsg()
                        .name(reqMsg.name())
                        .serviceName(reqMsg.serviceName())
                        .domainType(reqMsg.domainType())
                        .state(OmmState.StreamState.CLOSED, OmmState.DataState.SUSPECT, OmmState.StatusCode.NOT_FOUND, "Item not found")
                , event.handle())
    }
}

fun main(args: Array<String>) {
    lateinit var provider: OmmProvider
    val appCient = IProviderAppClient()
    try {

        println("Starting Kotlin_IProvider_200 application, waiting for a consumer application")

        //Creats OMM FieldList and Update Message objects
        val fieldList: FieldList = EmaFactory.createFieldList()
        val updateMsg: UpdateMsg = EmaFactory.createUpdateMsg()

        //OMMProvider creation and establish sersver port.
        provider = EmaFactory.createOmmProvider(EmaFactory.createOmmIProviderConfig().providerName("Provider_1").operationModel(OmmIProviderConfig.OperationModel.USER_DISPATCH), appCient)

        while (appCient.itemHandle.toInt() == 0) {
            provider.dispatch(1000L)
            Thread.sleep(1000L)
        }

        for (index in 1..59) {
            val startTime: Long = System.currentTimeMillis()

            provider.dispatch(1000L)

            fieldList.clear()
            fieldList.add(EmaFactory.createFieldEntry().real(22, 3991 + index.toLong(), OmmReal.MagnitudeType.EXPONENT_NEG_2))
            fieldList.add(EmaFactory.createFieldEntry().real(25, 3994 + index.toLong(), OmmReal.MagnitudeType.EXPONENT_NEG_2))
            fieldList.add(EmaFactory.createFieldEntry().real(30, 10 + index.toLong(), OmmReal.MagnitudeType.EXPONENT_0))
            fieldList.add(EmaFactory.createFieldEntry().real(31, 19 + index.toLong(), OmmReal.MagnitudeType.EXPONENT_0))

            provider.submit(updateMsg.clear().payload(fieldList), appCient.itemHandle)
            println("Kotlin_IProvider_200: Send  Market Price Update messages")
            while (System.currentTimeMillis() - startTime < 1000) {
            } //Keeps sending Update Messages
        }

    } catch (excp: OmmException) {
        println(excp.message)
    } catch (excp: InterruptedException) {
        println(excp.message)
    } finally {
        /*provider?.let {
            provider.uninitialize()
        }*/
        provider.uninitialize()
    }
}