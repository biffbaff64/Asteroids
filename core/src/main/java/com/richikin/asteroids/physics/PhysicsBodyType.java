package com.richikin.asteroids.physics;

public enum PhysicsBodyType
{
    // --------------------
    NONE,
    // --------------------
    DYNAMIC,
    DYNAMIC_SENSOR,
    DYNAMIC_BOUNCY,
    DYNAMIC_CIRCLE,
    DYNAMIC_CIRCLE_SENSOR,
    DYNAMIC_PUSHABLE,
    DYNAMIC_HEAVY,
    // --------------------
    KINEMATIC,
    KINEMATIC_SENSOR,
    KINEMATIC_HEAVY,
    // --------------------
    STATIC,
    STATIC_SENSOR,
    // --------------------
}
