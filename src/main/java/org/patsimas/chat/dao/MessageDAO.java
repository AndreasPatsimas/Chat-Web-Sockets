package org.patsimas.chat.dao;


public interface MessageDAO {
    Long getId();
    String getContent();
    String getSenderFirstName();
    String getSenderLastName();
    String getMessageTimestamp();
    Long getGroupId();
}
