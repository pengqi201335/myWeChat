package com.pq.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 保存客户端与服务端连接状态的session对象
 * @version 1.0
 * @author pengqi
 */
@Data
@NoArgsConstructor
public class Session {
    private String userName;

    private String userID;

    public Session(String userName,String userID){
        this.userID = userID;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userID+":"+userName;
    }
}
