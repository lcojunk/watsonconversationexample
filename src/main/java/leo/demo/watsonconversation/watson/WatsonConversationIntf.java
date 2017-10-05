package leo.demo.watsonconversation.watson;

import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

/**
 * Created by odzhara-ongom on 13.09.2017.
 * Interface that specifies wrapper for Watson Conversation service.
 * This Wrapper should expose only those function of Watson Conversation, that are needed for Integration Layer
 */
public interface WatsonConversationIntf {
    public MessageResponse sendMessage(String workspaceId, MessageRequest request);
}
