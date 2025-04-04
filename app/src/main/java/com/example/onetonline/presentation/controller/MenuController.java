package com.example.onetonline.presentation.controller;

import com.example.onetonline.data.AvatarRepo;
import com.example.onetonline.presentation.model.MenuModel;
import com.example.onetonline.presentation.view.*;

public class MenuController {
    private MenuModel menuModel;
    private MenuGame view;
    private AvatarRepo avatarRepo;

    public MenuController(MenuModel menuModel, MenuGame view) {
        this.menuModel = menuModel;
        this.view = view;
    }

}
