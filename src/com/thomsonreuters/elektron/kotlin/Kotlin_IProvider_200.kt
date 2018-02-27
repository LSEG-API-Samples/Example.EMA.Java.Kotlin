package com.thomsonreuters.elektron.kotlin

import com.thomsonreuters.ema.access.EmaFactory;
import com.thomsonreuters.ema.access.FieldList;
import com.thomsonreuters.ema.access.GenericMsg;
import com.thomsonreuters.ema.access.Msg;
import com.thomsonreuters.ema.access.OmmException;
import com.thomsonreuters.ema.access.OmmIProviderConfig;
import com.thomsonreuters.ema.access.OmmProvider;
import com.thomsonreuters.ema.access.OmmProviderClient;
import com.thomsonreuters.ema.access.OmmProviderEvent;
import com.thomsonreuters.ema.access.OmmReal;
import com.thomsonreuters.ema.access.OmmState;
import com.thomsonreuters.ema.access.PostMsg;
import com.thomsonreuters.ema.access.RefreshMsg;
import com.thomsonreuters.ema.access.ReqMsg;
import com.thomsonreuters.ema.access.StatusMsg;
import com.thomsonreuters.ema.access.UpdateMsg;
import com.thomsonreuters.ema.rdm.EmaRdm;

class IProviderAppclient : OmmProviderClient{

    override fun onRefreshMsg(refreshMsg: RefreshMsg, providerEvent: OmmProviderEvent) {}

    override fun onStatusMsg(statusMsg: StatusMsg, providerEvent: OmmProviderEvent) {}

    override fun onAllMsg(msg: Msg, providerEvent: OmmProviderEvent) {}

    override fun onClose(closeMsg: ReqMsg, providerEvent: OmmProviderEvent) {}

    override fun onGenericMsg(genericMsg: GenericMsg, providerEvent: OmmProviderEvent) {}

    override fun onPostMsg(postMsg: PostMsg, providerEvent: OmmProviderEvent) {}

    override fun onReissue(reissueMsg: ReqMsg, providerEvent: OmmProviderEvent) {}

    override fun onReqMsg(reqMsg: ReqMsg, providerEvent: OmmProviderEvent) {}
}