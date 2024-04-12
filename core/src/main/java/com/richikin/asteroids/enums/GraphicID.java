package com.richikin.asteroids.enums;

public enum GraphicID
{
    // ----------------------------
    // The Player
    G_PLAYER,
    G_PLAYER_FIGHT,
    G_PLAYER_DYING,
    G_PLAYER_SPAWNING,
    G_PLAYER_HURT,

    // ----------------------------
    // Weapons
    G_PLAYER_WEAPON,

    // ----------------------------
    // Interactive items
    G_MESSAGE_BUBBLE,
    G_MESSAGE_PANEL,

    // ----------------------------
    G_EXPLOSION12,
    G_EXPLOSION32,
    G_EXPLOSION64,
    G_EXPLOSION128,
    G_EXPLOSION256,

    // ----------------------------
    // Enemies
    G_ASTEROID,

    // #########################################################
    // Generic IDs
    // ----------------------------
    _LETHAL_OBJECT,
    _HAZARD,
    _SIGN,
    _SPEECH,
    _HUD_PANEL,
    _EXIT_BOX,

    // ----------------------------
    // Managers
    _ENEMY_MANAGER,
    _PLAYER_MANAGER,

    // ----------------------------
    // Main Character type, i.e. Player
    _MAIN,

    // ----------------------------
    // Enemy Character type, but not stationary entities
    // like rocket launchers etc.
    _ENEMY,

    // ----------------------------
    // Encapsulating type, covering any collision IDs that can be stood on.
    // This will be checked against the collision object TYPE, not the NAME.
    _OBSTACLE,

    // ----------------------------
    // As above but for objects that can't be stood on and are not entities
    _DECORATION,

    // As above, but for entities
    _ENTITY,

    // ----------------------------
    // Interactive objects
    _PICKUP,
    _WEAPON,
    _INTERACTIVE,

    // ----------------------------
    // Messages

    // ----------------------------

    G_DUMMY,
    G_UNKNOWN,
    G_NO_ID
}
