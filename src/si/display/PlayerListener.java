package si.display;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerListener implements KeyListener {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean fire;
    private boolean pause;
    private boolean acc;
    private boolean hyperspaceJump;
    private boolean stop;
    private boolean unbeatable = false;

    public boolean isPressingLeft() {
        return left;
    }

    public boolean isPressingRight() {
        return right;
    }

    public boolean isPressingUp() {
        return up;
    }

    public boolean isPressingAcc() {return acc;}

    public boolean hasPressedFire() {
        return fire;
    }

    public boolean hasPressedPause() {
        return pause;
    }

    public boolean hasPressedUnbeatable(){
        return unbeatable;
    }

    public boolean hasPressedHyperspaceJump() { return hyperspaceJump; }

    public boolean hasPressedStop() { return stop; }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
            pause = true;
        } else if (e.getKeyChar() == 'z' || e.getKeyChar() == 'Z') {
            stop =true;
        } else if (e.getKeyChar() == 'x' || e.getKeyChar() == 'X'){
            unbeatable = !unbeatable;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP){
            up = true;
            acc = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SHIFT){
            hyperspaceJump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP){
            up = true;
            acc = false;
        } else if (e.getKeyCode() == KeyEvent.VK_SHIFT){
            hyperspaceJump = false;
        }
    }

    public void resetPause() {
        pause = false;
    }

    public void resetHyperspaceJump() {
        hyperspaceJump = false;
    }

    public void resetStop() {
        stop = false;
    }

    public void resetFire() {
        fire = false;
    }

}
