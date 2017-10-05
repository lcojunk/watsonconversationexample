package leo.demo.watsonconversation.watson;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

/**
 * Created by odzhara-ongom on 13.09.2017.
 * Default Implementation of Watson Conversation Interface.
 * Expose the only needed "message.execute" functionality of real IBMs Watson ConversationService
 */
public class WatsonConversationImpl implements WatsonConversationIntf {

    private ConversationService conversationService;

    public WatsonConversationImpl(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @Override
    public MessageResponse sendMessage(String workspaceId, MessageRequest request) {
        return conversationService.message(workspaceId,request).execute();
    }
}
