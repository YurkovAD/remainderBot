package com.company.telegram.commands.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import com.company.utils.Utils;

/**
 * Created by yurkov.ad on 27.01.2021.
 */
public class HelpCommand extends ServiceCommand {

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        logger.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Я напомню Вам про ваши дела в необходимое время!\n\n" +
                "*Список команд*\n" +
                        "/remind - Создать задание, о котором я должен напомнить\n" +
                        "/change - Изменить задание, о котором я должен напомнить\n" +
                        "/remove - Удалить задачу из списка напоминаний\n" +
                        "/help - Помощь\n\n" +
                "remind - команда для создания задания. Введите название задания и время о котором я должен Вам напомнить в формате: \"Название задания, время.\"\n Например: Встреча, 15:00.\n\n" +
                "change - команда для изменения ранее созданного задания, о котором я должен Вам напомнить. Введите название задачи.\n Например: Встреча, 15:00.\n\n" +
                "remove - команда для удаления ранее созданного задания, о котором я должен Вам напомнить. Введите название задачи, которое хотите удалить.\n Например: Встреча, 15:00.\n\n" +
                "help - команда, которая раскажет Вам что я умею.\n\n" +
                "Желаю удачи!");
        logger.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }

    private Logger logger = LoggerFactory.getLogger(HelpCommand.class);
}
