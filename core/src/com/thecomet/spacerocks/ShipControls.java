package com.thecomet.spacerocks;

public class ShipControls {
    private final static int TURN_LEFT  = 0x01;
    private final static int TURN_RIGHT = 0x02;
    private final static int ACCELERATE = 0x04;
    private final static int SHOOT      = 0x08;
    private final static int TELEPORT   = 0x10;

    private int currentBits = 0;
    private int lastBits = 0;
    private int positiveEdgeBits;

    public boolean getTurnLeft() {
        return (currentBits & TURN_LEFT) != 0;
    }

    public void pressLeft() {
        currentBits |= TURN_LEFT;
    }

    public void releaseLeft() {
        currentBits &= ~TURN_LEFT;
    }

    public boolean getTurnRight() {
        return (currentBits & TURN_RIGHT) != 0;
    }

    public void pressRight() {
        currentBits |= TURN_RIGHT;
    }

    public void releaseRight() {
        currentBits &= ~TURN_RIGHT;
    }

    public boolean getAccelerate() {
        return (currentBits & ACCELERATE) != 0;
    }

    public void pressAccelerate() {
        currentBits |= ACCELERATE;
    }

    public void releaseAccelerate() {
        currentBits &= ~ACCELERATE;
    }

    public boolean getShoot() {
        return (positiveEdgeBits & SHOOT) != 0;
    }

    public void pressShoot() {
        currentBits |= SHOOT;
    }

    public boolean getTeleport() {
        return (positiveEdgeBits & TELEPORT) != 0;
    }

    public void pressTeleport() {
        currentBits |= TELEPORT;
    }

    public void nextFrame() {
        positiveEdgeBits = currentBits & ~lastBits;
        lastBits = currentBits;

        // clear one-shot buttons
        currentBits &= ~SHOOT;
        currentBits &= ~TELEPORT;
    }
}
