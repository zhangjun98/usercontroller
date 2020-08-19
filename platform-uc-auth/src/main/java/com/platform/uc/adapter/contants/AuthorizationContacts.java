package com.platform.uc.adapter.contants;

/**
 * @author hao.yan
 */
public class AuthorizationContacts {

    public static final String LOGIN_TOKEN = "lt";

    public static final Long LOGIN_EXPIRE = 3600 * 1000L;

    public final static String TICKET = "ticket";

    public final static String REDIS_TICKET_KEY = "uwo:ticket:";

    public final static Long TICKET_EXPIRE = 3600 * 1000L;

    public final static String CLIENT_ID = "client_id";

    public final static String CID = "cid";

    public final static Long CID_EXPIRE = 3600 * 1000L;

}
