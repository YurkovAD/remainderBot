package com.company.telegram.commands.user;

import com.company.logic.BotTask;
import com.company.utils.BotMessageSender;
import com.company.validators.TaskListValidator;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.text.SimpleDateFormat;

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
//            BotTask botTask;
//            List<BotTask> tmpTasks = new ArrayList<>();
//            taskList.forEach(bt -> {
////                try {
//                    if(bt.getBotMessage().getMessge().equals(task)) {
//                        tmpTasks.add(bt);
//                    }
////                } catch(Exception e) {
////
////                }
//            });

            //delete
            if(taskList.size() == 0 || taskList.isEmpty()){
                message.setText("В моём списке нет такой задачи!");
                sendMess(absSender, message);
            } else {
                SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
                taskList.forEach(bt -> {
                    String s = bt.getBotMessage().getMessge() + ", " + formater.format(bt.getBotMessage().getDateTime());
                    if(s.equals(task)) {
                        BotTask botTask = bt;
                        botTask.deleteTask(botTask);
                        message.setText("Задание " + botTask.getBotMessage().getMessge() + " удалено!");
                        sendMess(absSender, message);
                    }
                });
            }
        }
    }
//    private SendMessage createDeleteKeyboard(SendMessage message, List<BotTask> tasks/*, AbsSender absSender*/){
//        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
//        tasks.forEach( task -> {
//            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//            inlineKeyboardButton.setText(task.getBotMessage().getMessge() + ", " + formater.format(task.getBotMessage().getDateTime()));
//            inlineKeyboardButton.setCallbackData(callbackData(task/*, message, absSender*/));
//            keyboardButtonsRow.add(inlineKeyboardButton);
//        });
//        rowList.add(keyboardButtonsRow);
////        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
////        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
////        inlineKeyboardButton1.setText("Тык");
////        inlineKeyboardButton1.setCallbackData("Button \"Тык\" has been pressed");
////        inlineKeyboardButton2.setText("Тык2");
////        inlineKeyboardButton2.setCallbackData("Button \"Тык2\" has been pressed");
////
////        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
//////        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Fi4a").setCallbackData("CallFi4a"));
////        keyboardButtonsRow2.add(inlineKeyboardButton2);
////        rowList.add(keyboardButtonsRow2);
//        inlineKeyboardMarkup.setKeyboard(rowList);
//        message.setText("выберите задачу, которую нужно удалить: ");
//        message.setReplyMarkup(inlineKeyboardMarkup);
//
//        return message;
//    }
//    private String callbackData (BotTask task/*,SendMessage message, AbsSender absSender*/){
//        task.deleteTask(task);
//        return "Задание " + task.getBotMessage().getMessge() + " удалено!";
////        sendMess(absSender, message);
//    }
}
