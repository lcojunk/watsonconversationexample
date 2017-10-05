package leo.demo.watsonconversation.watson;

/**
 * Created by odzhara-ongom on 12.09.2017.
 * provides communication with single instance of IBM Watson Conversation Service
 *
 */
public interface ConversationServiceIntf {
    /**
     * Uses information provided in request Object: question, workspaceID, context to
     * communicate ("talk") with IBMs AIs
     * @param request
     * @return response Object, that contains recieved answers (ids of Documents in MongoDB)
     * and additional information that allows to continue the dialog with next request
     */
    public ChatBotConversationDTO talk(ChatBotConversationDTO request);
}
