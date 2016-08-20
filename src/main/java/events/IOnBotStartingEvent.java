package events;

public interface IOnBotStartingEvent extends IGenericEvent {
    /**
     * Event fired before the bot and API is initialized, should be used for stuff that interferes with the event queue as it is also fired before the event queue is even initialized.
     */
    void onBotStarting();
}
