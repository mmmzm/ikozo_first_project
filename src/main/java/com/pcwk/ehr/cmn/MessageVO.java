package com.pcwk.ehr.cmn;

public class MessageVO extends DTO {

	//메세지 id
	private String messageId;
	
	//메세지 내용
	private String msgContents;
	
	public MessageVO() {}
	
	public String getMessageId() {
		return messageId;
	}
	
	public MessageVO(String messageId, String msgContents) {
		super();
		this.messageId = messageId;
		this.msgContents = msgContents;
	}

	public String getMsgContents() {
		return msgContents;
	}

	public void setMsgContents(String msgContents) {
		this.msgContents = msgContents;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "MessageVO [messageId=" + messageId + ", msgContents=" + msgContents + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
