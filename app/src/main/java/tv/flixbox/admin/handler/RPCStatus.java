package tv.flixbox.admin.handler;

public class RPCStatus {

    public static final int TR_STATUS_STOPPED       = 0;
    public static final int TR_STATUS_CHECK_WAIT    = 1;
    public static final int TR_STATUS_CHECK         = 2;
    public static final int TR_STATUS_DOWNLOAD_WAIT = 3;
    public static final int TR_STATUS_DOWNLOAD      = 4;
    public static final int TR_STATUS_SEED_WAIT     = 5;
    public static final int TR_STATUS_SEED          = 6;

    public static final int RPC_LT_14_TR_STATUS_CHECK_WAIT = 1;
    public static final int RPC_LT_14_TR_STATUS_CHECK      = 2;
    public static final int RPC_LT_14_TR_STATUS_DOWNLOAD   = 4;
    public static final int RPC_LT_14_TR_STATUS_SEED       = 8;
    public static final int RPC_LT_14_TR_STATUS_STOPPED    = 16;

    private int rpcVersion;

    public RPCStatus(int rpcVersion){
        this.rpcVersion = rpcVersion;
    }

    public String getStatusString(int status){
        if(rpcVersion < 14){
            switch(status){
                case RPC_LT_14_TR_STATUS_CHECK_WAIT:
                    return "Waiting to verify local files";

                case RPC_LT_14_TR_STATUS_CHECK:
                    return "Verifying local files";

                case RPC_LT_14_TR_STATUS_DOWNLOAD:
                    return "Downloading";

                case RPC_LT_14_TR_STATUS_SEED:
                    return "Seeding";

                case RPC_LT_14_TR_STATUS_STOPPED:
                    return "Stopped";

                default:
                    return "Unknown";
            }

        }else{
            switch(status){
                case TR_STATUS_CHECK_WAIT:
                    return "Waiting to verify local files";

                case TR_STATUS_CHECK:
                    return "Verifying local files";

                case TR_STATUS_DOWNLOAD:
                    return "Downloading";

                case TR_STATUS_SEED:
                    return "Seeding";

                case TR_STATUS_STOPPED:
                    return "Stopped";

                case TR_STATUS_SEED_WAIT:
                    return "Queued for seeding";

                case TR_STATUS_DOWNLOAD_WAIT:
                    return "Queued for download";

                default:
                    return "Unknown";
            }
        }
    }
}
