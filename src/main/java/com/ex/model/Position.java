package com.ex.model;

/**
 * This enum is used for positions selectable to assign to a player in
 * data class - Player.  It is primarily used for data validation for
 * the position as compared to a string which could be anything.
 */
public enum Position {
    none,
    Pitcher,
    Catcher,
    FirstBase,
    SecondBase,
    ShortStop,
    ThirdBase,
    LeftField,
    RightField,
    CenterField
}
