package tv.flixbox.admin.handler.calls;

import tv.flixbox.admin.libs.json.variables.JsonObject;
import tv.flixbox.admin.libs.json.variables.JsonVariable;

public interface ResponseCallback {

    void onSuccessResponse(JsonVariable json);

    void onErrorResponse(JsonObject json);
}
