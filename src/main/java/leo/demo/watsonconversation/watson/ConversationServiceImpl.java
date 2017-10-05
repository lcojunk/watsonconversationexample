package leo.demo.watsonconversation.watson;

import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

/**
 * Created by odzhara-ongom on 13.09.2017.
 * Default implementation of communication interface to IBM Watson Conversation Service
 */
public class ConversationServiceImpl implements ConversationServiceIntf {
    /**
     * It seems, that IBMs Id ist not happy with null questions,
     * that is why it should be replaced with empty string.
     * Then instead exception we can get for example "greeting-answer"
     * or something else that is programmed by Content Team in Watson
     */
    private static final String EMPTY_CONVERSATION_STRING="";

    /**
     * Fallback workpsace that is used, if user do not specified workspace in his request.
     */
    private String defaultWorkspaceId;
    /**
     * placeholder for Service that allows direct communication to IBMs AI using IBM API
     */
    private WatsonConversationIntf conversationService;

    public ConversationServiceImpl(WatsonConversationIntf conversationService, String defaultWorkspaceId){
        this.conversationService=conversationService;
        this.defaultWorkspaceId=defaultWorkspaceId;
    }

    /**
     * Default implementaion of communications function with IBMs Watson Conversationservice
     * Starts new conversation with default workspace, if input parameter is null.
     * Replaces null question with empty question and null workspaceid with default workspaceid
     * Maps the response from Watson Conversation to Integration Layers Transfer Object
     * @param question The Object, that contains information about workspaceId, users question,
     *                 conversation dialog (context)
     * @return Object, that contains the information about possible answers (as ids of Documents in MongoDB)
     */
    @Override
    public ChatBotConversationDTO talk(ChatBotConversationDTO question) {
        MessageRequest newMessage;
        String workspaceId;
        if (question == null) {
            newMessage = new MessageRequest.Builder().inputText(EMPTY_CONVERSATION_STRING).context(null).build();
            workspaceId = defaultWorkspaceId;
        } else {
            MessageRequest.Builder builder = new MessageRequest.Builder().context(question.getContext());
            if(question.getQuestion()==null) newMessage = builder.inputText(EMPTY_CONVERSATION_STRING).build();
            else newMessage =builder.inputText(question.getQuestion()).build();
            if (question.getWorkspaceId() == null) {
                workspaceId = defaultWorkspaceId;
            } else {
                workspaceId = question.getWorkspaceId();
            }
        }
        MessageResponse response = conversationService.sendMessage(workspaceId,newMessage);
        return ChatBotConversationDTO.fromMessageResponse(response, workspaceId);    }
}
