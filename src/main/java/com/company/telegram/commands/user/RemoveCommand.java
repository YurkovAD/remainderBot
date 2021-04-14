package com.company.telegram.commands.user;

import com.company.logic.BotTask;
import com.company.utils.BotMessageSender;
import com.company.validators.TaskListValidator;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

import static com.company.telegram.commands.user.RemindCommand.taskList;

/**
 * Created by yurkov.ad on 31.03.2021.
 */
public class RemoveCommand extends BotCommand implements BotMessageSender {

    public RemoveCommand(String commandIdentifier, String description) {super(commandIdentifier, description);}

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        TaskListValidator taskListValidator = new TaskListValidator();
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        String task = String.join(" ", strings);

        if(taskListValidator.validate(taskList, task, absSender, user, message, this.getCommandIdentifier())) {
            //find
            BotTask botTask = null;
            List<BotTask> tmpTasks = new ArrayList<>();
            taskList.forEach(bt -> {
//                try {
                    if(bt.getBotMessage().getMessge().equals(task)) {
                        tmpTasks.add(bt);
                    }
//                } catch(Exception e) {
//
//                }
            });
            //delete
            if(tmpTasks.size() == 0 || tmpTasks.isEmpty()){
                message.setText("В моём списке нет такой задачи!");
                sendMess(absSender, message);
            } else if(tmpTasks.size() == 1 ){
                botTask = tmpTasks.get(0);
                botTask.deleteTask(botTask);
                message.setText("Задание " + botTask.getBotMessage().getMessge() + " удалено!");
                sendMess(absSender, message);
            } else {
//                SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
//                message.setText("Введиет порядковый номер задания, которое нужно удалить:\r\n");
//                tmpTasks.forEach(tmpT -> {
//                    message.setText(message.getText() + tmpT.getBotMessage().getMessge() + ", " + formater.format(tmpT.getBotMessage().getDateTime()) + "\r\n");
//                });
//                sendMess(absSender, message);
                sendMess(absSender, createDeleteKeyboard(message));

            }
        }
    }

    private SendMessage createDeleteKeyboard(SendMessage message){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Тык");
        inlineKeyboardButton1.setCallbackData("Button \"Тык\" has been pressed");
        inlineKeyboardButton2.setText("Тык2");
        inlineKeyboardButton2.setCallbackData("Button \"Тык2\" has been pressed");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
//        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Fi4a").setCallbackData("CallFi4a"));
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        message.setText("выберите задачу, которую нужно удалить: ");
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
}
