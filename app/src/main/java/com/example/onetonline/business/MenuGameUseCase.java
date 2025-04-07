package com.example.onetonline.business;

import com.example.onetonline.utils.Constants;

public class MenuGameUseCase {
    public int getExpCap(int level){
        return Constants.expCap(level);
    }
}
